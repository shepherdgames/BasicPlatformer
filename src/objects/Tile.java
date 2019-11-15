package objects;

import java.awt.Color;
import java.awt.Graphics;

public class Tile extends Object
{
	public enum Type { FLOOR, FALLABLE };
	
	private Type type;
	private boolean fallable = false;
	private boolean objectTouching = false;
	
	public Tile(float x, float y, Type type)
	{
		super(x, y, Object.OBJECT_SIZE, Object.OBJECT_SIZE);
		this.type = type;
	}
	
	public void init()
	{
		falling = false;
		if(type == Type.FALLABLE) fallable = true;
	}
	
	public void tick()
	{
		if(objectTouching && fallable)
		{
			System.out.println("TOUCHING");
			falling = true;
		}
		
		handleVerticalMovement();
		y += velY;
	}
	
	public void render(Graphics g)
	{
		if(type == Type.FLOOR)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
	}
	
	public boolean isFallable() { return fallable; }
	public boolean isObjectTouching() { return objectTouching; }
	
	public void setFallable(boolean fallable) { this.fallable = fallable; }
	public void setObjectTouching(boolean objectTouching) { this.objectTouching = objectTouching; }
}
