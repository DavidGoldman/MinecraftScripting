package scripting.wrapper;

import net.minecraft.client.Minecraft;
import scripting.utils.BlockCoord;

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
