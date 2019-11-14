package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import main.Game;
import main.Level;
import objects.Moveable;
import objects.Object;
import objects.ObjectHandler;
import objects.Player;

public class GameState extends State
{
	private ObjectHandler objectHandler;
	private Player player;
	
	private Level level1;
	
	public GameState(Game game)
	{
		super(game);
	}
	
	public void init()
	{
		objectHandler = new ObjectHandler();
		player = new Player((Game.WIDTH / 2) - (Object.OBJECT_SIZE / 2), (Game.HEIGHT / 2) - (Object.OBJECT_SIZE / 2));
		level1 = new Level(objectHandler, player);
		
		objectHandler.addObject(player);
		level1.loadLevel("Level1.txt");
		
		objectHandler.init();
	}
	
	public void tick()
	{
		objectHandler.tick();
		level1.collision();
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		objectHandler.render(g);
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
			player.setVelX(-Player.PLAYER_SPEED);
		else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
			player.setVelX(Player.PLAYER_SPEED);
		
		if(key == KeyEvent.VK_SPACE)
		{
			if(!player.isFalling() || player.isJumping())
			{
				player.setJumping(true);
			}
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
			player.setVelX(0);
		else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
			player.setVelX(0);
	}
}
