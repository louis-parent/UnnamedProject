package unnamed.model.container;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.CameraController;
import unnamed.controller.GameController;
import unnamed.model.element.map.Map;
import unnamed.model.element.map.tile.CorruptTile;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileFactory;
import unnamed.model.generator.MapGenerator;

public class MapContainer extends ElementContainer
{
	private static final long serialVersionUID = -3760401809828849717L;

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

		this.map = new Map(MapContainer.NUMBER_OF_COLUMNS, MapContainer.NUMBER_OF_ROWS);
		this.corruptTiles = new ArrayList<Tile>();
	}

	@Override
	public void init() throws SlickException
	{
		MapGenerator generator = new MapGenerator(MapContainer.NUMBER_OF_COLUMNS, MapContainer.NUMBER_OF_ROWS);
		this.map.addAll(generator.generateMap(this));
		this.corruptTiles.addAll(generator.getCorruptTiles());

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
			if((GameController.getInstance().getRandom().nextInt(MapContainer.CORRUPTION_SPEED) == 0) && !Tile.EMPTY.equals(tile) && this.map.contains(tile))
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

	public int getHeight()
	{
		return ((MapContainer.NUMBER_OF_ROWS * Tile.TILE_HEIGHT) - (Tile.FLOATING_OFFSET * MapContainer.NUMBER_OF_ROWS)) + Tile.TILE_HEIGHT + Tile.FLOATING_OFFSET;
	}

	public int getWidth()
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
				GameController.getInstance().goToPauseMenu();
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
