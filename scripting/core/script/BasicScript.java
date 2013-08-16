package scripting.core.script;

import org.mozilla.javascript.Function;

import scripting.core.ScriptException;


public final class BasicScript extends JSScript {
	
	private Function main;
	private Function onExit;

	public BasicScript(String name, String source) {
		super(name, source);
	}

	/**
	 * Initialize our main+onExit function reference here.
	 * @throws ScriptException If no main function exists.
	 */
	@Override
	public void postInit() throws ScriptException {
		Object mainObj = scope.get("main", scope);
		Object exitObj = scope.get("onExit", scope);
		
		if (mainObj instanceof Function) 
			main = (Function) mainObj;
		else
			throw new ScriptException(name + " must define a main function");
		
		if (exitObj instanceof Function)
			onExit = (Function) exitObj;
	}
	
	public Function getMain() {
		return main;
	}
	
	public Function getOnExit() {
		return onExit;
	}
	
	public boolean hasOnExit() {
		return onExit != null;
	}

}
