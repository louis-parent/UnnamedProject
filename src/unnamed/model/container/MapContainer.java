package unnamed.model.container;

import java.util.Random;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.CameraController;
import unnamed.controller.GameController;
import unnamed.model.element.map.DesertTile;
import unnamed.model.element.map.GrassTile;
import unnamed.model.element.map.Tile;
import unnamed.model.element.map.TileType;
import unnamed.model.element.map.WaterTile;

public class MapContainer extends ElementContainer
{

	private static final int NUMBER_OF_ROWS = 75;
	private static final int NUMBER_OF_COLUMNS = 75;

	@Override
	public void init() throws SlickException
	{
		GrassTile.init();
		WaterTile.init();
		DesertTile.init();

		Random rand = GameController.getInstance().getRandom();
		
		for(int i = 0; i < NUMBER_OF_COLUMNS; i++)
		{
			for(int j = 0; j < NUMBER_OF_ROWS; j++)
			{
				switch(rand.nextInt(3))
				{
					case 0:
						this.addElement(new GrassTile(i, j, TileType.getRandomType(), this));
						break;
						
					case 1:
						this.addElement(new WaterTile(i, j, TileType.getRandomType(), this));
						break;
						
					case 2:
						this.addElement(new DesertTile(i, j, TileType.getRandomType(), this));
						break;
				}
			}
		}
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
	}
}
