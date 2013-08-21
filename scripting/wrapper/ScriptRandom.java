package scripting.wrapper;

import java.util.Random;

public class ScriptRandom {
	
	private static final Random RANDOM = new Random();
	
	public static int randomInt(int min, int max) {
		return RANDOM.nextInt(max - min + 1) + min;
	}
	
	public static int randomInt(int maxExclusive) {
		return RANDOM.nextInt(maxExclusive);
	}
	
	public static int randomInt() {
		return RANDOM.nextInt();
	}
	
	public static boolean randomBoolean() {
		return RANDOM.nextBoolean();
	}
	
	public static double randomDouble() {
		return RANDOM.nextDouble();
	}
	
	public static float randomFloat() {
		return RANDOM.nextFloat();
	}
}
