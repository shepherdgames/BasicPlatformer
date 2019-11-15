package objects;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Moveable
{
	public static final float PLAYER_SPEED = 5.0f;
	
	public Player(float x, float y)
	{
		super(x, y, Object.OBJECT_SIZE, Object.OBJECT_SIZE * 2);
	}
	
	public void init()
	{
		
	}
	
	public void tick()
	{
		x += velX;
		y += velY;
		this.handleVerticalMovement();
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
	}
}
