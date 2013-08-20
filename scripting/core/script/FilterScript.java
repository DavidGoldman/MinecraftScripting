package scripting.core.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;

import scripting.core.ScriptException;

public final class FilterScript extends JSScript {
	
	/* getOptions() */
	private Function options;
	/* run(player, world, selection, options) */
	private Function run;

	public FilterScript(String name, String source) {
		super(name, source);
	}

	/**
	 * We initialize our run and options functions here.
	 * @throws ScriptException If no run function exists.
	 */
	@Override
	public void postInit() throws ScriptException {
		Object runObj = scope.get("run", scope);
		Object optObj = scope.get("getOptions", scope);
		
		if (runObj instanceof Function) 
			run = (Function) runObj;
		else
			throw new ScriptException(name + " must define a run function");
		
		if (optObj instanceof Function)
			options = (Function) optObj;
	}
	
	public Function getRun() {
		return run;
	}
	
	public boolean hasOptions() {
		return options != null;
	}
	
	public Function getOptions() {
		return options;
	}

}
