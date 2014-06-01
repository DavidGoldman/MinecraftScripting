package scripting.core;

public class ScriptException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScriptException(String message) {
		super(message);
	}
	
	public ScriptException(Throwable cause) {
		super(cause);
	}
}
