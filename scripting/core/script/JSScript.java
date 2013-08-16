package scripting.core.script;

import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import scripting.core.ScriptException;

public abstract class JSScript {
	
	public final String name;
	public final String source;
	
	protected Script script;
	protected Scriptable scope;
	
	public JSScript(String name, String source) {
		this.name = name;
		this.source = source;
	}
	
	/**
	 * Called after being executed for the first time to set up functions.
	 * 
	 * @throws ScriptException If an error occurs (such as function not found).
	 */
	public abstract void postInit() throws ScriptException;
	
	/**
	 * Called after being compiled.
	 */
	public void init(Script script, Scriptable scope) {
		this.script = script;
		this.scope = scope;
	}
	
	public void release() {
		script = null;
		scope = null;
	}

	public Script getScript() {
		return script;
	}
	
	public Scriptable getScope() {
		return scope;
	}
	
	/* Not needed
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() == obj.getClass()) {
			JSScript js = (JSScript)obj;
			return this.name.equals(js.name) && this.source.equals(js.source) && this.scope == js.scope;
		}
		return false;
	}*/

}
