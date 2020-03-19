package unnamed.model.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import unnamed.controller.GameController;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.map.DesertTile;
import unnamed.model.element.map.GrassTile;
import unnamed.model.element.map.Tile;
import unnamed.model.element.map.TileType;
import unnamed.model.element.map.WaterTile;

public class MapGenerator
{

	private int rows;
	private int columns;
	
	public MapGenerator(int numberOfRows, int numberOfColumns)
	{
		this.rows = numberOfRows;
		this.columns = numberOfColumns;
	}

	public List<Tile> generateMap(ElementContainer container)
	{
		List<Tile> tiles = new ArrayList<Tile>();
		
		Random rand = GameController.getInstance().getRandom();

		for(int i = 0; i < this.columns; i++)
		{
			for(int j = 0; j < this.rows; j++)
			{
				switch(rand.nextInt(3))
				{
					case 0:
						tiles.add(new GrassTile(i, j, TileType.getRandomType(), container));
						break;

					case 1:
						tiles.add(new WaterTile(i, j, TileType.getRandomType(), container));
						break;

					case 2:
						tiles.add(new DesertTile(i, j, TileType.getRandomType(), container));
						break;
				}
			}
		}
		
		return tiles;
	}

}
