package scripting.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import scripting.core.script.BasicScript;
import scripting.core.script.JSScript;
import scripting.wrapper.js.Range;
import scripting.wrapper.js.TagItr;

public abstract class ScriptCore {
	
	public static final class State implements Comparable<State> {
		public final String script;
		public final boolean running;
		
		public State(String script, boolean running) {
			this.script = script;
			this.running = running;
		}

		@Override
		public int compareTo(State obj) {
			return this.script.compareTo(obj.script);
		}
	}

	protected static final class Executing {
		public final JSScript script;
		public final boolean canSleep;

		public Executing(JSScript script, boolean canSleep) {
			this.script = script;
			this.canSleep = canSleep;
		}
	}

	protected static final class Sleeping {
		public final BasicScript script;
		public final Object continuation;
		private int ticksLeft;

		public Sleeping(BasicScript script, Object continuation, int ticksLeft) {
			this.script = script;
			this.continuation = continuation;
			this.ticksLeft = ticksLeft;
		}

		public boolean tick() {
			return (--ticksLeft) == 0;
		}
	}
	

	/**
	 * Note that {@link Context#exit} is never called.
	 * The thread that calls {@link Context.enter} will
	 * eventually die along with the reference to this ScriptCore
	 * object. 
	 */
	protected final Context context;
	protected final Scriptable globalScope;

	protected final Map<String, JSScript> scripts;
	protected final List<Sleeping> sleepingScripts;

	protected Executing curScript;

	public ScriptCore(Map<String, JSScript> scripts, Map<String, Object> props, Map<String,Class<?>> abbreviations) {
		this.scripts = scripts;
		this.context = Context.enter();
		this.sleepingScripts = new ArrayList<Sleeping>();
		this.globalScope = new ImporterTopLevel(context); /*context.initStandardObjects();*/
		
		try {
			ScriptableObject.defineClass(globalScope, Range.class);
			ScriptableObject.defineClass(globalScope, TagItr.class);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}

		for (Entry<String, Class<?>> entry : abbreviations.entrySet()) 
			abbreviate(entry.getValue(), entry.getKey());

		for (Entry<String, Object> entry : props.entrySet())
			setProperty(entry.getKey(), entry.getValue());

		for (Iterator<JSScript> it = scripts.values().iterator(); it.hasNext(); ) {
			JSScript js = it.next();
			try {
				initializeScript(js);
			}
			catch(Throwable e) {
				System.err.println("[SCRIPTS] Error initializing script " + js.name);
				e.printStackTrace();
				it.remove();
			}
		}
	}

	protected abstract void notifyCrash(JSScript script, Exception e);

	protected final void scriptCrash(JSScript script, Exception e) {
		//Remove it for good! Well until it is reloaded...
		scripts.remove(script.name);

		System.err.println("[SCRIPTS] Error running script " + script.name);
		e.printStackTrace();
		notifyCrash(script, e);
	}

	public void tick() {
		/*
		 * The copy prevents a ConcurrentModificationException.
		 * The script may trigger a block of code that adds
		 * an element to our sleepingScripts list.
		 */
		List<Sleeping> copy = new ArrayList<Sleeping>(sleepingScripts);
		for (Sleeping s : copy) {
			if (s.tick()) {
				sleepingScripts.remove(s);
				continueExecuting(s);
			}
		}
	}
	
	/**
	 * Starts or stops the specified  BasicScript.
	 * @param name Name of the script.
	 */
	public void toggleScript(String name) {
		JSScript script = scripts.get(name);
		if (script instanceof BasicScript) {
			BasicScript bsc = (BasicScript) script;

			if (!isSleeping(script))
				startExecuting(bsc);
			else {
				if (bsc.hasOnExit())
					bsc.getOnExit().call(context, bsc.getScope(), bsc.getScope(), new Object[0]);
				removeSleeping(script);
			}
		}
	}
	
	/**
	 * @return If this core has any BasicScripts
	 */
	public boolean hasScripts() {
		return !scripts.isEmpty();
	}
	
	public boolean isScriptRunning(String name) {
		return isSleeping(scripts.get(name));
	}
	
	protected boolean isSleeping(JSScript script) {
		if (script instanceof BasicScript) {
			for (Sleeping s : sleepingScripts)
				if (s.script == script)
					return true;
		}
		return false;
	}

	protected void removeSleeping(JSScript script) {
		for (Iterator<Sleeping> it = sleepingScripts.iterator(); it.hasNext();) {
			if (it.next().script == script) {
				it.remove();
				return;
			}
		}
	}
	
	public final List<State> getBasicScripts() {
		List<State> list = new ArrayList<State>();
		for (JSScript script : scripts.values()) {
			if (script instanceof BasicScript)
				list.add(new State(script.name, isSleeping(script)));
		}
		Collections.sort(list);
		return list;
	}

	public final void scriptSleep(int ticks) throws ContinuationPending, IllegalAccessException, IllegalArgumentException {
		if (ticks < 1)
			throw new IllegalArgumentException("Must sleep for at least 1 tick");
		if (curScript == null)
			throw new IllegalAccessException("No script currently running");
		if (!curScript.canSleep)
			throw new IllegalAccessException("Illegal state for sleeping");
		ContinuationPending cp = context.captureContinuation();
		sleepingScripts.add(new Sleeping((BasicScript)curScript.script, cp.getContinuation(), ticks));
		throw cp;
	}

	public final void setProperty(String name, Object obj) {
		ScriptableObject.putProperty(globalScope, name, Context.javaToJS(obj, globalScope));
	}

	protected final void abbreviate(Class<?> clazz, String abrev) throws RuntimeException {
		try {
			context.evaluateString(globalScope, abrev + " = " + "Packages." + clazz.getName(), "<abrev>", 1, null);
		}
		catch(RuntimeException e) {
			throw e;
		}
		catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}

	private Script compileScript(String script, String sourceName) throws Exception {
		return context.compileString(script, sourceName, 1, null);
	}

	private void initializeScript(JSScript js) throws Throwable {
		curScript = new Executing(js, false);
		js.init(compileScript(js.source, js.name), context.newObject(globalScope));
		js.getScript().exec(context, js.getScope());
		js.postInit();
		curScript = null;
	}

	private void continueExecuting(Sleeping script) {
		try {
			curScript = new Executing(script.script, true);
			context.resumeContinuation(script.continuation, script.script.getScope(), null);
			curScript = new Executing(script.script, false);
			BasicScript bsc = script.script;
			if (bsc.hasOnExit())
				bsc.getOnExit().call(context, bsc.getScope(), bsc.getScope(), new Object[0]);
		}
		catch(ContinuationPending p) { }
		catch(Exception e) {
			scriptCrash(script.script, e);
		}
		finally {
			curScript = null;
		}
	}

	private void startExecuting(BasicScript script) {
		try {
			curScript = new Executing(script, true);
			context.callFunctionWithContinuations(script.getMain(), script.getScope(), new Object[0]);
			curScript = new Executing(script, false);
			if (script.hasOnExit())
				script.getOnExit().call(context, script.getScope(), script.getScope(), new Object[0]);
		}
		catch(ContinuationPending p) { }
		catch(Exception e) {
			scriptCrash(script, e);
		}
		finally {
			curScript = null; 
		}
	}
}
