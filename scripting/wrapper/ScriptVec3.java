package scripting.wrapper;

public class ScriptVec3 {

	public double x;
	public double y;
	public double z;

	public ScriptVec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public ScriptVec3() {
		this(0, 0, 0);
	}

	public void add(ScriptVec3 vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
	}

	public void add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void multiply(ScriptVec3 vec) {
		this.x *= vec.x;
		this.y *= vec.y;
		this.z *= vec.z;
	}

	public void multiply(double x, double y, double z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
	}

	public void scale(double s) {
		this.x *= s;
		this.y *= s;
		this.z *= s;
	}

	public double length() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public void normalize() {
		double l = length();
		if (l > 0) {
			this.x /= l;
			this.y /= l;
			this.z /= l;
		}
	}

	public double distanceTo(ScriptVec3 vec) {
		double dx = this.x - vec.x;
		double dy = this.y - vec.y;
		double dz = this.z - vec.z;
		return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}
}
