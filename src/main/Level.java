package main;

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
	private ArrayList<Tile> tiles;
	private ArrayList<Moveable> moveables;
	private ObjectHandler objectHandler;
	private Player player;
	
	private int levelWidth, levelHeight;
	
	public Level(ObjectHandler objectHandler, Player player)
	{
		this.objectHandler = objectHandler;
		this.player = player;
		
		tiles = new ArrayList<Tile>();
		moveables = new ArrayList<Moveable>();
		levelWidth = levelHeight = 0;
		
		moveables.add(this.player);
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
				if(tileTokens[y][x] == 1)
				{
					tiles.add(new Tile(x * Object.OBJECT_SIZE, y * Object.OBJECT_SIZE, Type.FLOOR));
				}
			}
		}
		
		for(Tile t: tiles)
		{
			objectHandler.addObject(t);
		}
	}

	public void collision()
	{
		for(Moveable m: moveables)
		{
			boolean colliding = false;
			for(Tile t: tiles)
			{
				if(m.getExtendedBounds().intersects(t.getBounds()))
				{
					colliding = true;
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
}
