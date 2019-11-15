package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import main.Game;

public class MenuState extends State
{
	public static final int NUM_MENU_ITEMS = 4;
	
	private String titleString = "Basic Platformer";
	private String playString = "Play";
	private String settingsString = "Settings";
	private String helpString = "Help";
	private String exitString = "Exit";
	
	private int menuPosition = 0;
	
	public MenuState(Game game)
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
		
		g.setColor(Color.WHITE);
		g.drawString(titleString, 100, 100);
		
		drawString(g, 0, playString, 175);
		drawString(g, 1, settingsString, 250);
		drawString(g, 2, helpString, 325);
		drawString(g, 3, exitString, 400);
	}
	
	private void drawString(Graphics g, int position, String myString, int y)
	{
		if(menuPosition == position)
		{
			g.setColor(Color.GREEN);
			g.setFont(g.getFont().deriveFont(35.0f));
		}else
		{
			g.setColor(Color.WHITE);
			g.setFont(g.getFont().deriveFont(32.0f));
		}
		g.drawString(myString, 100, y);
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_DOWN && menuPosition != NUM_MENU_ITEMS - 1) menuPosition++;
		else if(key == KeyEvent.VK_UP && menuPosition != 0) menuPosition--;
		
		if(key == KeyEvent.VK_ENTER)
		{
			switch(menuPosition)
			{
			case 0:
				State.changeState(new GameState(game));
				break;
			case 1:
				System.out.println("SETTINGS STATE WILL GO HERE");
				break;
			case 2:
				System.out.println("HELP STATE WILL GO HERE");
				break;
			case 3:
				game.stop();
				System.exit(0);
				break;
			default:
				System.err.println("This code should never be run");
			}
		}
	}
}
