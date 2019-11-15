package objects;

public abstract class Moveable extends Object
{
	public static float GRAV_INCREASE = 0.3f;
	public static float MAX_GRAV = 7.0f;
	
	protected float velX, velY;
	protected boolean falling = true;
	protected boolean jumping = false;
	
	public Moveable(float x, float y, int width, int height)
	{
		super(x, y, width, height);
	}
	
	public void handleVerticalMovement()
	{
		if(jumping)
		{
			velY = -MAX_GRAV;
			jumping = false;
			falling = true;
		}
		else if(falling)
		{
			velY += GRAV_INCREASE;
			if(velY >= MAX_GRAV) velY = MAX_GRAV;
		}
	}
	
	public float getVelX() { return velX; }
	public float getVelY() { return velY; }
	public boolean isFalling() { return falling; }
	public boolean isJumping() { return jumping; }
	
	public void setVelX(float velX) { this.velX = velX; }
	public void setVelY(float velY) { this.velY = velY; }
	public void setFalling(boolean falling) { this.falling = falling; }
	public void setJumping(boolean jumping) { this.jumping = jumping; }
}
