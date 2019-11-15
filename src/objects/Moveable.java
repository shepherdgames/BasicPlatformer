package objects;

public abstract class Moveable extends Object
{
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
	
	public boolean isJumping() { return jumping; }
	
	public void setJumping(boolean jumping) { this.jumping = jumping; }
}
