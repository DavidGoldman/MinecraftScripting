package scripting.core.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;

/**
 * Simple sandbox helper to return our sandboxed java objects.
 * @see http://codeutopia.net/blog/2009/01/02/sandboxing-rhino-in-java/
 *
 */
public class SandboxWrapFactory extends WrapFactory {
	@SuppressWarnings("rawtypes")
	@Override
	public Scriptable wrapAsJavaObject(Context cx, Scriptable scope, Object javaObject, Class staticType) {
		return new SandboxNativeJavaObject(scope, javaObject, staticType);
	}
}
