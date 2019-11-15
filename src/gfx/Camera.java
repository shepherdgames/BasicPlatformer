package gfx;

public class Camera
{
	private float x, y;

	public Camera(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void moveCam(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
}
