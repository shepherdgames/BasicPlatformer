package state;

import java.awt.Graphics;

import main.Game;

public abstract class State 
{
	protected Game game;
	
	protected State(Game game)
	{
		this.game = game;
	}
	
	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void cleanUp() {}
	
	public static State currentState;
	
	public static void changeState(State newState)
	{
		if(currentState != null) currentState.cleanUp();
		newState.init();
		currentState = newState;
	}
}
