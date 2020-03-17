package unnamed.controller;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import unnamed.model.ElementContainer;
import unnamed.model.Tile;
import unnamed.view.GameWindow;

public class GameController
{

	private static final String GAME_NAME = "Unnamed";
	private GameWindow view;

	public GameController()
	{
		this.view = new GameWindow(GAME_NAME, this);
	}

	public void start() throws SlickException
	{
		new AppGameContainer(this.view, 640, 480, false).start();
	}

	public void init(GameContainer container) throws SlickException
	{
		ElementContainer.getInstance().init();
		container.getInput().addMouseListener(new MouseController());
	}

	public void update(GameContainer container, int delta) throws SlickException
	{

	}

	public void render(GameContainer container, Graphics g) throws SlickException
	{
		this.view.renderWindow(g, ElementContainer.getInstance().getElementsToDraw());
	}
}
