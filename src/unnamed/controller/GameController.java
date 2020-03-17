package unnamed.controller;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import unnamed.model.ElementContainer;
import unnamed.view.Camera;
import unnamed.view.GameWindow;

public class GameController
{

	public static final int GAME_HEIGHT = 1080;

	public static final int GAME_WIDTH = 1920;

	private static final String GAME_NAME = "Unnamed";

	private static GameController instance;

	private GameContainer container;
	private GameWindow view;
	private ElementContainer model;

	public static GameController getInstance()
	{
		if(GameController.instance == null)
		{
			GameController.instance = new GameController();
		}

		return GameController.instance;
	}

	private GameController()
	{
		this.view = new GameWindow(GAME_NAME, this);
		this.model = new ElementContainer();
	}

	public static void start() throws SlickException
	{
		AppGameContainer game = new AppGameContainer(GameController.getInstance().view, GAME_WIDTH, GAME_HEIGHT, true);
		game.start();
	}
	
	public static void stop()
	{
		GameController.getInstance().container.exit();
	}

	public void init(GameContainer container) throws SlickException
	{
		this.container = container;
		this.model.init();
		container.getInput().addMouseListener(new MouseController());
		container.getInput().addKeyListener(new KeyController());
	}

	public void update(GameContainer container, int delta) throws SlickException
	{
		this.view.updateWindow();
	}

	public void render(GameContainer container, Graphics g) throws SlickException
	{
		this.view.renderWindow(g, this.model.getElementsToDraw());
	}

	public GameWindow getView()
	{
		return this.view;
	}

	public ElementContainer getModel()
	{
		return this.model;
	}

	public void clickAt(int x, int y)
	{
		Camera camera = this.view.getCamera();
		float zoomMultiplicator = camera.getZoomMultiplicator();
		this.model.clickAt((x / zoomMultiplicator) - camera.getOffsetX(), (y / zoomMultiplicator) - camera.getOffsetY());
	}

	public int getMapHeight()
	{
		return this.model.getMapHeight();
	}

	public int getMapWidth()
	{
		return this.model.getMapWidth();
	}
}
