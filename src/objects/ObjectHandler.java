package objects;

import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectHandler 
{
	private ArrayList<Object> objects;
	
	public ObjectHandler()
	{
		objects = new ArrayList<Object>();
	}
	
	public void init()
	{
		for(Object ob: objects)
		{
			ob.init();
		}
	}
	
	public void tick()
	{
		for(Object ob: objects)
		{
			ob.tick();
		}
	}
	
	public void render(Graphics g)
	{
		for(Object ob: objects)
		{
			ob.render(g);
		}
	}
	
	public void addObject(Object ob)
	{
		objects.add(ob);
	}
	
	public void removeObject(Object ob)
	{
		objects.remove(ob);
	}
}
