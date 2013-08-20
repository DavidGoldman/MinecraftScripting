package scripting.core.rhino;

import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

/**
 * The core of our sandbox. This creates our special context,
 * sets the wrap factory, and observes the time each script
 * takes to execute, eventually killing it if necessary. 
 * 
 * See {@link org.mozilla.javascript.ContextFactory}.
 */
public class SandboxContextFactory extends ContextFactory {

	public static final int STACK_DEPTH = 16383;
	public static final int INSTRUCTION_THRESHOLD = 10000;
	public static final int MAX_TIME = 30*1000; //30 seconds

	private static class TimedContext extends Context {

		long startTime;

		public TimedContext(ContextFactory factory) {
			super(factory);
		}
	}

	/**
	 * @return A context that calls observeInstructionCount every 10,000 bytecode instructions
	 */ 
	protected Context makeContext() {
		TimedContext cx = new TimedContext(this);
		cx.setLanguageVersion(Context.VERSION_1_7);
		cx.setOptimizationLevel(-1);
		cx.setMaximumInterpreterStackDepth(STACK_DEPTH);
		cx.setInstructionObserverThreshold(INSTRUCTION_THRESHOLD);
		cx.setClassShutter(new SandboxClassShutter());
		cx.setWrapFactory(new SandboxWrapFactory());
		return cx;
	}

	protected void observeInstructionCount(Context cx, int instructionCount) throws Error {
		TimedContext mcx = (TimedContext)cx;
		long currentTime = System.currentTimeMillis();
		if (currentTime - mcx.startTime > MAX_TIME) 
			throw new Error("Script exceeded maximum runtime length");
	}

	//TODO Possibly lookup which script is being executed and set the max time based off of its type
	protected Object doTopCall(Callable callable, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		TimedContext mcx = (TimedContext)cx;
		mcx.startTime = System.currentTimeMillis();
		return super.doTopCall(callable, cx, scope, thisObj, args);
	}


}
