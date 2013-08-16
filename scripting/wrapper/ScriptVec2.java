package scripting.wrapper;

public class ScriptVec2 {
	
	public double x;
	public double y;
	
	public ScriptVec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public ScriptVec2() {
		this(0, 0);
	}
	
	public void add(ScriptVec2 vec) {
		this.x += vec.x;
		this.y += vec.y;
	}
	
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	public void multiply(ScriptVec2 vec) {
		this.x *= vec.x;
		this.y *= vec.y;
	}

	public void multiply(double x, double y) {
		this.x *= x;
		this.y *= y;
	}
	
	public void scale(double s) {
		this.x *= s;
		this.y *= s;
	}

	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	public void normalize() {
		double l = length();
		if (l > 0) {
			this.x /= l;
			this.y /= l;
		}
	}
	
	public double distanceTo(ScriptVec2 vec) {
		double dx = this.x - vec.x;
		double dy = this.y - vec.y;
		return Math.sqrt(dx*dx + dy*dy);
	}
}
