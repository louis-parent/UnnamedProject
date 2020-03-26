package unnamed.model.element.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import unnamed.controller.GameController;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;
import unnamed.model.element.map.tile.TileDirection;
import unnamed.model.element.map.tile.TileType;

public class TileMap extends ArrayList<Tile>
{
	private static final long serialVersionUID = 1L;

	private int columns;
	private int rows;

	public TileMap(int columns, int rows)
	{
		super();

		this.columns = columns;
		this.rows = rows;
	}

	public int getListPosition(float x, float y)
	{
		if(((x >= 0) && (y >= 0)) && ((x < this.columns) && (y < this.rows)))
		{
			return (int) ((this.columns * x) + y);
		}
		else
		{
			return -1;
		}
	}

	public Map<TileDirection, Tile> getAdjacentTilesRaw(Tile tile)
	{
		Map<TileDirection, Tile> adjacents = new HashMap<TileDirection, Tile>();

		adjacents.put(TileDirection.NE, getTileAt((tile.getColumn() + 1) - ((tile.getRow() - 1)  % 2), tile.getRow() - 1));
		adjacents.put(TileDirection.E, getTileAt(tile.getColumn() + 1, tile.getRow()));
		adjacents.put(TileDirection.SE, getTileAt((tile.getColumn() + 1) - ((tile.getRow() + 1) % 2), tile.getRow() + 1));
		adjacents.put(TileDirection.SW, getTileAt(tile.getColumn() - ((tile.getRow() + 1) % 2), tile.getRow() + 1));
		adjacents.put(TileDirection.W, getTileAt(tile.getColumn() - 1, tile.getRow()));
		adjacents.put(TileDirection.NW, getTileAt(tile.getColumn() - ((tile.getRow() - 1) % 2), tile.getRow() - 1));

		return adjacents;
	}

	private Tile getTileAt(int x, int y)
	{
		int listPosition = this.getListPosition(x, y);
		
		if(listPosition != -1)
		{
			return this.get(listPosition);
		}
		else
		{
			return Tile.getEmpty();
		}
	}

	public int getNumberAdjacentsOf(TileType type, Tile source)
	{
		List<Tile> adjacents = source.getAdjacents();

		adjacents.removeIf(tile -> tile.getType() != type);
		
		return adjacents.size();
	}

	public List<Tile> getAllAdjacentFor(TileType type, List<Tile> tiles)
	{
		return this.getAllAdjacentFor(tile -> tile.getType() == type, tiles);
	}

	public List<Tile> getAllAdjacentFor(TileBiome biome, List<Tile> tiles)
	{
		return this.getAllAdjacentFor(tile -> tile.getBiome() == biome, tiles);
	}

	private List<Tile> getAllAdjacentFor(Predicate<Tile> predicate, List<Tile> tiles)
	{
		List<Tile> adjacents = this.getAllAdjacent(tiles);

		for(int i = 0; i < adjacents.size(); i++)
		{
			Tile tile = adjacents.get(i);
			if(!predicate.test(tile))
			{
				adjacents.remove(i);
				i--;
			}
		}

		return adjacents;
	}

	public List<Tile> getAllAdjacent(List<Tile> tiles)
	{
		List<Tile> typedAdjacent = new ArrayList<Tile>();

		for(Tile tile : tiles)
		{
			typedAdjacent.addAll(tile.getAdjacents());
		}

		return typedAdjacent;
	}

	public Tile getRandomTile()
	{
		Random rand = GameController.getInstance().getRandom();
		return this.get(rand.nextInt(this.size()));
	}

	public Tile getRandomTile(TileBiome biome)
	{
		return this.getRandomTile(tile -> tile.getBiome() == biome);
	}

	public Tile getRandomTile(TileType type)
	{
		return this.getRandomTile(tile -> tile.getType() == type);
	}

	public Tile getRandomTile(TileBiome biome, TileType type)
	{
		return this.getRandomTile(tile -> (tile.getBiome() == biome) && (tile.getType() == type));
	}

	private Tile getRandomTile(Predicate<Tile> predicate)
	{
		Tile selected = Tile.getEmpty();

		boolean found = false;

		while(!found)
		{
			selected = this.getRandomTile();

			if(predicate.test(selected))
			{
				found = true;
			}
		}

		return selected;
	}
}
