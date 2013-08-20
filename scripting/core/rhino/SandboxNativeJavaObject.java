package scripting.core.rhino;

import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

/**
 * Simple sandbox for native java objects. 
 * This will prevent JS scripts from using methods
 * such as {@link Object#wait()}.
 * See {@link org.mozilla.javascript.ContextFactory}
 * @see http://codeutopia.net/blog/2009/01/02/sandboxing-rhino-in-java/
 *
 */
public class SandboxNativeJavaObject extends NativeJavaObject {
	
	public SandboxNativeJavaObject() { }
	
	public SandboxNativeJavaObject(Scriptable scope, Object javaObject, Class<?> staticType) {
		super(scope, javaObject, staticType);
	}
	
	@Override
	public Object get(String name, Scriptable start) {
		if (name.equals("wait") || name.equals("notify") || name.equals("notifyAll")) 
			return NOT_FOUND;
		return super.get(name, start);
	}
}
