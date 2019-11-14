package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Object 
{
	public static final int OBJECT_SIZE = 32;
	public static final int BOUND_SIZE = 5;
	
	protected float x, y;
	protected int width, height;
	protected BufferedImage texture;
	protected boolean onScreen = true;
	
	protected Object(float x, float y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width; 
		this.height = height;
		this.texture = null;
	}
	
	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public Rectangle getBounds() { return new Rectangle((int)x, (int)y, width, height); }
	public Rectangle getExtendedBounds() { return new Rectangle((int)x, (int)y, width, height + BOUND_SIZE); }
	public Rectangle getLeft() { return new Rectangle((int)x, (int)y+BOUND_SIZE, BOUND_SIZE, height-(BOUND_SIZE * 2)); }
	public Rectangle getTop() { return new Rectangle((int)x, (int)y, width, BOUND_SIZE); }
	public Rectangle getRight()  { return new Rectangle((int)x+width-BOUND_SIZE, (int)y+BOUND_SIZE, BOUND_SIZE, height-(BOUND_SIZE*2)); }
	public Rectangle getBottom() { return new Rectangle((int)x, (int)y+height-BOUND_SIZE, width, BOUND_SIZE); }
	
	public float getX() { return x; }
	public float getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public BufferedImage getTexture() { return texture; }
	public boolean isOnScreen() { return onScreen; }
	
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public void setTexture(BufferedImage texture) { this.texture = texture; }
	public void setOnScreen(boolean onScreen) { this.onScreen = onScreen; }
}
