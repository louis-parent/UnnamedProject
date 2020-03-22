package unnamed.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;
import unnamed.model.container.GameOverMenuContainer;
import unnamed.model.container.MainMenuContainer;
import unnamed.model.container.MapContainer;
import unnamed.model.container.PauseMenuContainer;
import unnamed.model.container.SeedMenuContainer;
import unnamed.model.element.map.tile.TileFactory;
import unnamed.model.element.menu.button.MenuFactory;
import unnamed.view.Camera;
import unnamed.view.GameWindow;

public class GameController
{
	public static final int GAME_HEIGHT = 1080;
	public static final int GAME_WIDTH = 1920;

	private static final String GAME_NAME = "Unnamed";
	private static final String SAVE_FILE = "saves/save.unp";
	private static final int TICK_LENGTH_IN_MILLIS = 50;

	private static GameController instance;

	private CameraController cameraController;

	private GameContainer container;
	private GameWindow view;

	private ElementContainer currentContainer;
	private MainMenuContainer mainMenuContainer;
	private PauseMenuContainer pauseMenuContainer;
	private SeedMenuContainer seedMenuContainer;
	private MapContainer mapContainer;
	private GameOverMenuContainer gameOverContainer;

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
		this.view = new GameWindow(GameController.GAME_NAME, this);

		this.cameraController = new CameraController(this.view.getCamera());

		this.currentContainer = ElementContainer.EMPTY;
		this.mainMenuContainer = new MainMenuContainer();
		this.pauseMenuContainer = new PauseMenuContainer();
		this.seedMenuContainer = new SeedMenuContainer();
		this.gameOverContainer = new GameOverMenuContainer();
		
		this.random = new Random();

		this.leftOverMillis = 0;
	}

	public static void start() throws SlickException
	{
		AppGameContainer game = new AppGameContainer(GameController.getInstance().view, GameController.GAME_WIDTH, GameController.GAME_HEIGHT, true);
		game.start();
	}

	public static void stop()
	{
		GameController.getInstance().container.exit();
	}

	public void init(GameContainer container) throws SlickException
	{
		MenuFactory.init();
		TileFactory.init();

		this.container = container;

		this.mainMenuContainer.init();
		this.pauseMenuContainer.init();
		this.seedMenuContainer.init();
		this.gameOverContainer.init();

		this.setCurrentContainer(this.mainMenuContainer);

		container.getInput().addMouseListener(new MouseController());
		container.getInput().addKeyListener(new KeyController());
	}

	public void update(GameContainer container, int delta) throws SlickException
	{
		this.leftOverMillis += delta;
		int ticksPassed = this.leftOverMillis / GameController.TICK_LENGTH_IN_MILLIS;
		this.leftOverMillis %= GameController.TICK_LENGTH_IN_MILLIS;

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

		try
		{
			this.currentContainer.clickAt((x / zoomMultiplicator) - camera.getOffsetX(), (y / zoomMultiplicator) - camera.getOffsetY());
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}

	public void pressedAt(int x, int y) throws SlickException
	{
		this.currentContainer.pressedAt(x, y);
	}

	public int getHeight()
	{
		return this.currentContainer.getHeight();
	}

	public int getWidth()
	{
		return this.currentContainer.getWidth();
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
		this.currentContainer.leave();
		this.currentContainer = container;
		
		this.cameraController.reset();
		this.currentContainer.enter();
	}

	public void playGame()
	{
		this.setCurrentContainer(this.mapContainer);
	}

	public void goToMainMenu()
	{
		this.setCurrentContainer(this.mainMenuContainer);
	}

	public void goToPauseMenu()
	{
		this.setCurrentContainer(this.pauseMenuContainer);
	}
	
	public void goToSeedMenu()
	{
		this.setCurrentContainer(this.seedMenuContainer);
	}
	
	public void loseGame()
	{
		this.setCurrentContainer(this.gameOverContainer);
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) throws SlickException
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

	public void saveGame()
	{
		try
		{
			File save = new File(GameController.SAVE_FILE);
			
			File saveFolder = save.getParentFile();
			if(!saveFolder.exists())
			{
				saveFolder.mkdir();
			}
			
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(save));
			stream.writeObject(this.mapContainer);
			stream.close();
		}
		catch(IOException e)
		{
		}
	}

	public void loadGame()
	{
		try
		{
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(GameController.SAVE_FILE));
			this.mapContainer = (MapContainer) stream.readObject();
			stream.close();
		}
		catch(IOException | ClassNotFoundException e)
		{
		}

		this.playGame();
	}

	public void createGame(long seed) throws SlickException
	{
		this.random.setSeed(seed);
		
		this.mapContainer = new MapContainer();
		this.mapContainer.init();

		this.playGame();
	}

	public static boolean saveExists()
	{
		return new File(SAVE_FILE).exists();
	}
}
