package scripting.wrapper.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeIterator;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;

/**
 * This class represents an integer range (inclusive).
 * Note the the JS annotations are used rather than the 
 * specialized naming conventions (for convenience). 
 * 
 * Usage: "for (var coord in new Range(sel.minX, sel.maxX)) ..."
 * 
 * Based off of the Range class by Tim Schaub.
 * @See https://gist.github.com/tschaub/3291399
 *
 */
public class Range extends ScriptableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int current, low, high;

	public Range() { }

	public Range(int low, int high) {
		this.low = low;
		this.high = high;
		this.current = low - 1;
	}

	public Range(int high) {
		this(0, high);
	}

	/**
	 * {@link org.mozilla.javascript.FunctionObject.FunctionObject}
	 */
	@JSConstructor
	public static Object constructor(Context cx, Object[] args, Function ctorObj, boolean inNewExpr) {
		if (!inNewExpr)
			throw ScriptRuntime.constructError("Range Error", "Call constructor using the \"new\" keyword.");
		if (args.length == 1)
			return new Range( (int)Context.toNumber(args[0]));
		if (args.length == 2) 
			return new Range( (int)Context.toNumber(args[0]), (int)Context.toNumber(args[1]));
		throw ScriptRuntime.constructError("Range Error", "Call constructor with one or two numbers.");
	}

	@Override
	public Object getDefaultValue(Class<?> typeHint) {
		if (typeHint == String.class)
			return toString();
		return this;
	}

	@JSFunction
	public int next() {
		//Increment current and throw stop iteration if done
		if ( (++current) > high) 
			throw new JavaScriptException(NativeIterator.getStopIterationObject(getParentScope()), null, 0);
		return current;
	}

	@JSFunction
	public Object __iterator__(boolean b) {
		return this;
	}

	@Override
	public String getClassName() {
		return "Range";
	}

	@JSGetter
	public int getHigh() {
		return high;
	}

	@JSGetter
	public int getLow() {
		return low;
	}
}
