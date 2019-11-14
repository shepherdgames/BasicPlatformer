package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import state.GameState;
import state.State;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "BasicPlatformer";
	
	private Thread thread;
	private boolean running = false;
	
	private State startState;
	
	public Game()
	{
		Dimension d = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
	}
	
	public void init()
	{
		startState = new GameState(this);
		State.changeState(startState);
	}
	
	public void tick()
	{
		State.currentState.tick();
	}
	
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		State.currentState.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public void run()
	{
		init();
		while(running)
		{
			tick();
			render();
		}
	}
	
	public synchronized void start()
	{
		if(running) return;
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!running) return;
		running = false;
		
		try 
		{
			thread.join();
		} catch (InterruptedException e) 
		{
			System.err.println("Could not correctly shut down game");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame(Game.TITLE);
		Game game = new Game();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		game.start();
	}
}
