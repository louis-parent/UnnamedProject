package unnamed.controller;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;
import unnamed.model.container.MainMenuContainer;
import unnamed.model.container.MapContainer;
import unnamed.view.Camera;
import unnamed.view.GameWindow;

public class GameController
{
	public static final int GAME_HEIGHT = 1080;
	public static final int GAME_WIDTH = 1920;

	private static final String GAME_NAME = "Unnamed";
	private static final int TICK_LENGTH_IN_MILLIS = 50;
	
	private static GameController instance;

	private CameraController cameraController;

	private GameContainer container;
	private GameWindow view;

	private ElementContainer currentContainer;
	private MainMenuContainer menuContainer;
	private MapContainer mapContainer;

	private Random random;
	
	private int leftOverMillis;

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

		this.cameraController = new CameraController(this.view.getCamera());

		this.menuContainer = new MainMenuContainer();
		this.mapContainer = new MapContainer();

		this.random = new Random();
		
		this.leftOverMillis = 0;
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

		this.menuContainer.init();
		this.mapContainer.init();

		this.setCurrentContainer(this.menuContainer);

		container.getInput().addMouseListener(new MouseController());
		container.getInput().addKeyListener(new KeyController());
	}

	public void update(GameContainer container, int delta) throws SlickException
	{
		this.leftOverMillis += delta;
		int ticksPassed = this.leftOverMillis / TICK_LENGTH_IN_MILLIS;
		this.leftOverMillis %= TICK_LENGTH_IN_MILLIS;
		
		for(int i = 0; i < ticksPassed; i++)
		{
			this.currentContainer.tickUpdate();
		}
		
		this.view.updateWindow(delta);
	}

	public void render(GameContainer container, Graphics g) throws SlickException
	{
		this.view.renderWindow(g, this.currentContainer.getElementsToDraw());
	}

	public GameWindow getView()
	{
		return this.view;
	}

	public CameraController getCameraController()
	{
		return this.cameraController;
	}

	public Random getRandom()
	{
		return this.random;
	}

	public void clickAt(int x, int y)
	{
		Camera camera = this.view.getCamera();
		float zoomMultiplicator = camera.getZoomMultiplicator();
		this.currentContainer.clickAt((x / zoomMultiplicator) - camera.getOffsetX(), (y / zoomMultiplicator) - camera.getOffsetY());
	}

	public void pressedAt(int x, int y)
	{
		this.currentContainer.pressedAt(x, y);
	}

	public int getMapHeight()
	{
		return this.mapContainer.getMapHeight();
	}

	public int getMapWidth()
	{
		return this.mapContainer.getMapWidth();
	}

	public void mouseWheelMoved(int change)
	{
		this.currentContainer.mouseWheelMoved(change);
	}

	public void keyReleased(int key, char c)
	{
		this.currentContainer.keyReleased(key, c);
	}

	public void keyPressed(int key, char c)
	{
		this.currentContainer.keyPressed(key, c);
	}

	public void setCurrentContainer(ElementContainer container)
	{
		this.currentContainer = container;
		this.cameraController.reset();
	}

	public void playGame()
	{
		this.setCurrentContainer(this.mapContainer);
	}

	public void goToMainMenu()
	{
		this.setCurrentContainer(this.menuContainer);
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy)
	{
		this.currentContainer.mouseDragged(oldx, oldy, newx, newy);
	}

	public void wheelPressedAt(int x, int y)
	{
		this.currentContainer.wheelPressedAt(x, y);
	}

	public void wheelReleasedAt(int x, int y)
	{
		this.currentContainer.wheelReleasedAt(x, y);
	}
}
