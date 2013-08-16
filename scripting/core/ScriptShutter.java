package scripting.core;

import java.util.Arrays;
import java.util.List;

import org.mozilla.javascript.ClassShutter;

public class ScriptShutter implements ClassShutter {
	
	/**
	 * Some classes in java.lang are quite scary, so no :O
	 */
	private static List<String> ALLOWED_PACKAGES = Arrays.asList("scripting.wrapper", "java.util");
	
	private final static List<String> ALLOWED_CLASSES = Arrays.asList("java.lang.Object", "java.lang.String", "java.lang.Boolean",
			"java.lang.Float", "java.lang.Double", "java.lang.Integer", "java.lang.Byte", "java.lang.Short", "java.lang.Long", "java.lang.Class");

	@Override
	public boolean visibleToScripts(String fullClassName) {
		if(ALLOWED_CLASSES.contains(fullClassName))
			return true;
		for (String s : ALLOWED_PACKAGES)
			if (fullClassName.startsWith(s))
				return true;
		return false;
	}
	
}