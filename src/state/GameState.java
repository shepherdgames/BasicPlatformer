package state;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class GameState extends State
{
	public GameState(Game game)
	{
		super(game);
	}
	
	public void init()
	{
		
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
}
