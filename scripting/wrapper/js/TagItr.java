package scripting.wrapper.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeIterator;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;

import scripting.wrapper.nbt.TAG_Base;
import scripting.wrapper.nbt.TAG_List;

/**
 * Javascript TAG_List iterator.
 * 
 * Usage: "for (var tag in new TagItr(tagList)) ..."
 * 
 * See {@link scripting.wrapper.js.Range} for more information.
 *
 */
public class TagItr extends ScriptableObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TAG_List list;
	int index;
	
	public TagItr() { }
	
	public TagItr(TAG_List list) {
		this.list = list;
	}
	

	@JSConstructor
	public static Object constructor(Context cx, Object[] args, Function ctorObj, boolean inNewExpr) {
		if (!inNewExpr)
			throw ScriptRuntime.constructError("TagItr Error", "Call constructor using the \"new\" keyword.");
		if (args.length == 1)
			return new TagItr((TAG_List)Context.jsToJava(args[0], TAG_List.class));
		throw ScriptRuntime.constructError("TagItr Error", "Call constructor with a single TAG_List.");
	}
	
	@JSFunction
	public TAG_Base next() {
		if (index >= list.tagCount()) 
			throw new JavaScriptException(NativeIterator.getStopIterationObject(getParentScope()), null, 0);
		return list.tagAt(index++);
	}
	
	@Override
	public Object getDefaultValue(Class<?> typeHint) {
		if (typeHint == String.class)
			return toString();
		return this;
	}
	
	@JSFunction
	public Object __iterator__(boolean b) {
		return this;
	}

	@Override
	public String getClassName() {
		return "TagItr";
	}

}
