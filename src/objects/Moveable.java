package objects;

public abstract class Moveable extends Object
{
	protected float velX, velY;
	
	public Moveable(float x, float y, int width, int height)
	{
		super(x, y, width, height);
	}
	
	public float getVelX() { return velX; }
	public float getVelY() { return velY; }
	
	public void setVelX(float velX) { this.velX = velX; }
	public void setVelY(float velY) { this.velY = velY; }
}
