package main;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import objects.Moveable;
import objects.Object;
import objects.ObjectHandler;
import objects.Player;
import objects.Tile;
import objects.Tile.Type;

public class Level
{
	public static final int CUTOFF_AREA = Object.OBJECT_SIZE * 2;
	public static final int START_LEVEL_TIME = 60 * 3;
	
	private ArrayList<Tile> tiles;
	private ArrayList<Moveable> moveables;
	private ObjectHandler objectHandler;
	private Player player;
	private String levelName;
	private String startingString;
	private int levelNumber;
	private boolean displayingLevelName = true;
	private int displayCounter = 0;
	
	private int levelWidth, levelHeight;
	
	public Level(ObjectHandler objectHandler, Player player, int levelNumber)
	{
		this.objectHandler = objectHandler;
		this.player = player;
		this.levelNumber = levelNumber;
		
		tiles = new ArrayList<Tile>();
		moveables = new ArrayList<Moveable>();
		levelWidth = levelHeight = 0;
		displayCounter = 0;
		startingString = "Level starting in: " + (((START_LEVEL_TIME - displayCounter) / 60) + 1);
		loadLevelName();
		
		moveables.add(this.player);
	}
	
	private void loadLevelName()
	{
		levelName = "Level " + levelNumber + ": ";
		switch(levelNumber)
		{
		case 1:
			levelName += "Getting down to Basics";
			break;
		default:
			levelName += "Placeholder Level Name";
		}
	}
	
	public void loadLevel(String fileName)
	{
		String path = "res/" + fileName;
		int[][] tileTokens = null;
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(path));
			levelWidth = Integer.parseInt(br.readLine());
			levelHeight = Integer.parseInt(br.readLine());
			tileTokens = new int[levelHeight][levelWidth];
			
			String line;
			int y = 0;
			while((line = br.readLine()) != null)
			{
				String[] tokensStrings = line.split(",");
				for(int x = 0; x < levelWidth; x++)
				{
					tileTokens[y][x] = Integer.parseInt(tokensStrings[x]);
				}
				y++;
			}
			br.close();
			
		} catch (IOException e) 
		{
			System.err.println("Cannot load level called " + fileName);
			e.printStackTrace();
			System.exit(-1);
		}
		
		for(int y = 0; y < levelHeight; y++)
		{
			for(int x = 0; x < levelWidth; x++)
			{
				if(tileTokens[y][x] == -1)
				{
					player.setX(x * Object.OBJECT_SIZE);
					player.setY(y * Object.OBJECT_SIZE);
				}else if(tileTokens[y][x] == 1)
				{
					tiles.add(new Tile(x * Object.OBJECT_SIZE, y * Object.OBJECT_SIZE, Type.FLOOR));
				}else if(tileTokens[y][x] == 2)
				{
					tiles.add(new Tile(x * Object.OBJECT_SIZE, y * Object.OBJECT_SIZE, Type.FALLABLE));
				}
			}
		}
		
		for(Tile t: tiles)
		{
			objectHandler.addObject(t);
		}
	}
	
	public void tick()
	{
		if(displayingLevelName)
		{
			displayCounter++;
			startingString = "Level starting in: " + (((START_LEVEL_TIME - displayCounter) / 60) + 1);
			if(displayCounter >= 60 * 3)
			{
				displayCounter = 0;
				displayingLevelName = false;
			}
		}else
		{
			collision();
		}
	}
	
	public void render(Graphics g)
	{
		if(this.displayingLevelName)
		{
			FontMetrics metrics = g.getFontMetrics(g.getFont());
			int nameCentreX = (Game.WIDTH - metrics.stringWidth(levelName)) / 2;
			int nameCentreY = ((Game.HEIGHT - metrics.stringWidth(levelName)) / 2) + metrics.getAscent();
			int startCentreX = ((Game.WIDTH - metrics.stringWidth(startingString)) / 2);
			
			g.setColor(new Color(0, 0, 128));
			g.drawString(levelName, nameCentreX, nameCentreY);
			g.drawString(startingString, startCentreX, nameCentreY + 100);
		}
	}

	public void collision()
	{
		for(Tile t: tiles)
		{
			t.setObjectTouching(false);
		}
		
		for(Moveable m: moveables)
		{
			boolean colliding = false;
			for(Tile t: tiles)
			{
				if(m.getExtendedBounds().intersects(t.getBounds()))
				{
					colliding = true;
					t.setObjectTouching(true);
					if(m.getBounds().intersects(t.getBounds()))
					{
						if(m.getBottom().intersects(t.getTop()))
						{
							m.setFalling(false);
							m.setVelY(0);
							m.setY(t.getY() - m.getHeight());
						}else if(m.getLeft().intersects(t.getRight()))
						{
							m.setVelX(0);
							m.setX(t.getX() + t.getWidth());
						}else if(m.getRight().intersects(t.getLeft()))
						{
							m.setVelX(0);
							m.setX(t.getX() - m.getWidth());
						}else if(m.getTop().intersects(t.getBottom()))
						{
							System.out.println("NEED TO WRITE CODE FOR ROOF COLLISION");
						}
					}
				}
			}
			
			if(!colliding) m.setFalling(true);
		}
	}

	public void checkOnScreen()
	{
		float leftBound = player.getX() + (player.getWidth() / 2) - (Game.WIDTH / 2) - CUTOFF_AREA;
		float rightBound = player.getX() + (player.getWidth() / 2) + (Game.WIDTH / 2) + CUTOFF_AREA;
		
		for(Tile t: tiles)
		{
			if(t.getX() < leftBound || t.getX() > rightBound) t.setOnScreen(false);
		}
		
		for(Moveable m: moveables)
		{
			if(m.getX() < leftBound || m.getX() > rightBound) m.setOnScreen(false);
		}
	}
	
	public boolean isDisplayingLevelName() { return displayingLevelName; }
	
	public void setDisplayingLevelName(boolean displayingLevelName) { this.displayingLevelName = displayingLevelName; }
}
