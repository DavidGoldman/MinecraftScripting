package scripting.wrapper;


public class ScriptIO {
	
	public static void print(String s) {
		System.out.print(s);
	}
	public static void println(String s) {
		System.out.println(s);
	}
	
	
	public static void errPrint(String s) {
		System.err.print(s);
	}
	public static void  errPrintln(String s) {
		System.err.println(s);
	}
	
}
