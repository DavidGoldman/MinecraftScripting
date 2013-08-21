package scripting.core.script;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaArray;

import scripting.core.ScriptException;
import scripting.wrapper.settings.Setting;

public final class FilterScript extends JSScript {
	
	/* function run(player, world, sel, options) */
	private Function run;
	/* var inputs = Setting.toArray(...); */
	private Setting[] inputs;

	public FilterScript(String name, String source) {
		super(name, source);
	}

	/**
	 * We initialize our run and options functions here.
	 * @throws ScriptException If no run function exists.
	 */
	@Override
	public void postInit() throws ScriptException {
		Object run = scope.get("run", scope);
		Object inputs = scope.get("inputs", scope);
		
		if (run instanceof Function) 
			this.run = (Function)run;
		else
			throw new ScriptException(name + " must define a run function");
		
		if (inputs instanceof NativeJavaArray) {
			try {
				this.inputs = (Setting[]) ((NativeJavaArray)inputs).unwrap();
			}
			catch(ClassCastException e) {
				throw new ScriptException(e);
			}
		}
	}
	
	public Function getRun() {
		return run;
	}
	
	public boolean hasOptions() {
		return inputs != null && inputs.length > 0;
	}
	
	public Setting[] getOptions() {
		return inputs;
	}

}
