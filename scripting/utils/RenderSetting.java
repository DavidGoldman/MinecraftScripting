package scripting.utils;

public class RenderSetting {
	
	public final float r, g, b, a;
	public final int depthFunc;
	
	public RenderSetting(float r, float g, float b, float a, int depthFunc) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.depthFunc = depthFunc;
	}
}
