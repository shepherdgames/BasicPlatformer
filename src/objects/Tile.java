package objects;

import java.awt.Color;
import java.awt.Graphics;

public class Tile extends Object
{
	public enum Type { FLOOR };
	
	private Type type;
	
	public Tile(float x, float y, Type type)
	{
		super(x, y, Object.OBJECT_SIZE, Object.OBJECT_SIZE);
		this.type = type;
	}
	
	public void init()
	{
		
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y, width, height);
	}
}
