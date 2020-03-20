package unnamed.model.container;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.CameraController;
import unnamed.controller.GameController;
import unnamed.model.element.map.CorruptTile;
import unnamed.model.element.map.DesertTile;
import unnamed.model.element.map.GrassTile;
import unnamed.model.element.map.Map;
import unnamed.model.element.map.Tile;
import unnamed.model.element.map.TileFactory;
import unnamed.model.element.map.WaterTile;
import unnamed.model.generator.MapGenerator;

public class MapContainer extends ElementContainer
{

	private static final int CORRUPTION_SPEED = 1000;
	private static final int NUMBER_OF_COLUMNS = 75;
	private static final int NUMBER_OF_ROWS = 75;

	private boolean isMouseWheelActivated;

	private Map map;

	private List<Tile> corruptTiles;

	public MapContainer()
	{
		super();

		this.isMouseWheelActivated = false;

		this.map = new Map(NUMBER_OF_COLUMNS, NUMBER_OF_ROWS);
		this.corruptTiles = new ArrayList<Tile>();
	}

	@Override
	public void init() throws SlickException
	{
		GrassTile.init();
		WaterTile.init();
		DesertTile.init();
		CorruptTile.init();

		MapGenerator gen = new MapGenerator(NUMBER_OF_COLUMNS, NUMBER_OF_ROWS);
		this.map.addAll(gen.generateMap(this));

		int corruptStart = GameController.getInstance().getRandom().nextInt(this.map.size());
		Tile oldTile = this.map.get(corruptStart);
		Tile corruptTile = TileFactory.createFrom(TileFactory.CORRUPT_BIOME, oldTile);
		this.map.set(corruptStart, corruptTile);
		this.corruptTiles.add(corruptTile);

		for(Tile tile : this.map)
		{
			this.addElement(tile);
		}
	}

	@Override
	public void tickUpdate()
	{
		this.spreadCorruption();
		this.tickUpdateAllTiles();
	}
	
	private void tickUpdateAllTiles()
	{
		for(Tile tile : this.map)
		{
			tile.tickUpdate();
		}
	}

	private void spreadCorruption()
	{
		List<Tile> adjacent = this.map.getAllAdjacent(this.corruptTiles);

		adjacent.removeIf(t -> t instanceof CorruptTile);

		for(Tile tile : adjacent)
		{
			if(GameController.getInstance().getRandom().nextInt(CORRUPTION_SPEED) == 0 && !Tile.EMPTY.equals(tile) && this.map.contains(tile))
			{
				Tile corrupt = TileFactory.createFrom(TileFactory.CORRUPT_BIOME, tile);

				this.replaceTile(tile, corrupt);

				this.corruptTiles.add(corrupt);
			}
		}
	}

	private void replaceTile(Tile oldElement, Tile newElement)
	{
		this.replaceElement(oldElement, newElement);

		this.map.set(this.map.indexOf(oldElement), newElement);
	}

	public int getMapHeight()
	{
		return (NUMBER_OF_ROWS * Tile.TILE_HEIGHT) - (Tile.FLOATING_OFFSET * NUMBER_OF_ROWS) + Tile.TILE_HEIGHT + Tile.FLOATING_OFFSET;
	}

	public int getMapWidth()
	{
		return (MapContainer.NUMBER_OF_COLUMNS * Tile.TILE_WIDTH) + (Tile.TILE_WIDTH / 2);
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		if(change < 0)
		{
			GameController.getInstance().getCameraController().unzoom();
		}
		else
		{
			GameController.getInstance().getCameraController().zoom();
		}
	}

	@Override
	public void keyReleased(int key, char c)
	{
		CameraController cam = GameController.getInstance().getCameraController();

		switch(key)
		{
			case Input.KEY_UP:
				cam.stopMovingUp();
				break;
			case Input.KEY_DOWN:
				cam.stopMovingDown();
				break;
			case Input.KEY_LEFT:
				cam.stopMovingLeft();
				break;
			case Input.KEY_RIGHT:
				cam.stopMovingRight();
				break;
		}
	}

	@Override
	public void keyPressed(int key, char c)
	{
		CameraController cam = GameController.getInstance().getCameraController();

		switch(key)
		{
			case Input.KEY_UP:
				cam.movingUp();
				break;
			case Input.KEY_DOWN:
				cam.movingDown();
				break;
			case Input.KEY_LEFT:
				cam.movingLeft();
				break;
			case Input.KEY_RIGHT:
				cam.movingRight();
				break;
			case Input.KEY_ESCAPE:
				GameController.getInstance().goToMainMenu();
				break;
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy)
	{
		if(this.isMouseWheelActivated)
		{
			GameController.getInstance().getCameraController().mouseWheelDragged(oldx, oldy, newx, newy);
		}
	}

	@Override
	public void wheelPressedAt(int x, int y)
	{
		this.isMouseWheelActivated = true;
	}

	@Override
	public void wheelReleasedAt(int x, int y)
	{
		this.isMouseWheelActivated = false;
	}
}
