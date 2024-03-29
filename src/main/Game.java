package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import state.MenuState;
import state.State;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 608;
	public static final String TITLE = "BasicPlatformer";
	
	private Thread thread;
	private boolean running = false;
	
	private State startState;
	private Font font;
	
	public Game()
	{
		Dimension d = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
	}
	
	public void init()
	{	
		startState = new MenuState(this);
		State.changeState(startState);
		
		try
		{
			font = Font.createFont(Font.TRUETYPE_FONT, new File("res/GameFont.otf"));
			font = font.deriveFont(32.0f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		} catch (FontFormatException | IOException e)
		{
			System.err.println("Could not load custom font file");
			e.printStackTrace();
			System.exit(-1);
		}

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
		g.setFont(font);
		
		State.currentState.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public void run()
	{
		init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
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
		
		game.requestFocus();
		game.start();
	}
}
