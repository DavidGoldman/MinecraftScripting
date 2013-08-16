package scripting.wrapper;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ScriptArray {
	
	public static boolean[] booleanArray(int length) {
		return new boolean[length];
	}
	public static boolean[] booleanArray(boolean... bools) {
		return bools;
	}
	public static String toString(boolean[] arr) {
		return Arrays.toString(arr);
	}
	
	public static byte[] byteArray(int length) {
		return new byte[length];
	}
	public static byte[] byteArray(byte... bytes) {
		return bytes;
	}
	public static String toString(byte[] arr) {
		return Arrays.toString(arr);
	}
	
	public static int[] intArray(int length) {
		return new int[length];
	}
	public static int[] intArray(int... ints) {
		return ints;
	}
	public static String toString(int[] arr) {
		return Arrays.toString(arr);
	}
	
	
	public static short[] shortArray(int length) {
		return new short[length];
	}
	public static short[] shortArray(short... shorts) {
		return shorts;
	}
	public static String toString(short[] arr) {
		return Arrays.toString(arr);
	}
	
	public static long[] longArray(int length) {
		return new long[length];
	}
	public static long[] longArray(long... longs) {
		return longs;
	}
	public static String toString(long[] arr) {
		return Arrays.toString(arr);
	}
	
	public static float[] floatArray(int length) {
		return new float[length];
	}
	public static float[] floatArray(float... floats) {
		return floats;
	}
	public static String toString(float[] arr) {
		return Arrays.toString(arr);
	}
	
	public static double[] doubleArray(int length) {
		return new double[length];
	}
	public static double[] doubleArray(double... doubles) {
		return doubles;
	}
	public static String toString(double[] arr) {
		return Arrays.toString(arr);
	}
	
	
	public static Object[] objectArray(int length) {
		return new Object[length];
	}
	public static Object[] objectArray(Object... objs) {
		return objs;
	}
	public static String toString(Object[] arr) {
		return Arrays.toString(arr);
	}
	
	public static Object newArray(Class<?> clazz, int length) {
		return Array.newInstance(clazz, length);
	}
	public static Object newArray(Class<?> clazz, int... dimensions) {
		return Array.newInstance(clazz, dimensions);
	}
	
	public static <T> List<T> asList(T... arr) {
		return Arrays.asList(arr);
	}
}
