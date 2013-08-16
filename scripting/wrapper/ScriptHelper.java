package scripting.wrapper;

import org.mozilla.javascript.ContinuationPending;

import scripting.ScriptingMod;

public class ScriptHelper {
	
	public static void sleep(int ticks) throws ContinuationPending, IllegalAccessException, IllegalArgumentException {
		ScriptingMod.instance.scriptSleep(ticks);
	}
	
}
