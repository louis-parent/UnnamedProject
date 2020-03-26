package unnamed.model.element.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import unnamed.controller.GameController;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;
import unnamed.model.element.map.tile.TileType;

public class Map extends ArrayList<Tile>
{
	private static final long serialVersionUID = 1L;

	private int columns;
	private int rows;

	public Map(int columns, int rows)
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

	public List<Tile> getAdjacentTiles(Tile changedTile)
	{
		List<Tile> adjacent = new ArrayList<Tile>();

		adjacent.addAll(this.getTopTiles(changedTile));

		int listPosition = this.getListPosition(changedTile.getColumn() - 1, changedTile.getRow());

		if(listPosition != -1)
		{
			adjacent.add(this.get(listPosition));
		}

		listPosition = this.getListPosition(changedTile.getColumn() + 1, changedTile.getRow());

		if(listPosition != -1)
		{
			adjacent.add(this.get(listPosition));
		}

		adjacent.addAll(this.getBottomTiles(changedTile));

		return adjacent;
	}

	private List<Tile> getTopTiles(Tile changedTile)
	{
		return this.getAdjacentTilesForRow(changedTile, -1);
	}

	private List<Tile> getBottomTiles(Tile changedTile)
	{
		return this.getAdjacentTilesForRow(changedTile, 1);
	}

	private List<Tile> getAdjacentTilesForRow(Tile changedTile, int offset)
	{
		List<Tile> row = new ArrayList<Tile>();

		int leftTileValue = changedTile.getColumn() - ((changedTile.getRow() + 1) % 2);

		int listPosition = this.getListPosition(leftTileValue, changedTile.getRow() + offset);

		if(listPosition != -1)
		{
			row.add(this.get(listPosition));
		}

		listPosition = this.getListPosition(leftTileValue + 1, changedTile.getRow() + offset);

		if(listPosition != -1)
		{
			row.add(this.get(listPosition));
		}

		return row;
	}

	public int getNumberAdjacentsOf(TileType type, Tile source)
	{
		List<Tile> farAdjacents = new ArrayList<Tile>();

		int i = source.getColumn();
		int j = source.getRow();

		this.addToWithCondition(farAdjacents, type, i - 1, j + 2);
		this.addToWithCondition(farAdjacents, type, i + 1, j + 2);
		this.addToWithCondition(farAdjacents, type, i - 2, j);
		this.addToWithCondition(farAdjacents, type, i + 2, j);
		this.addToWithCondition(farAdjacents, type, i - 1, j - 2);
		this.addToWithCondition(farAdjacents, type, i + 1, j - 2);

		return farAdjacents.size();
	}

	private void addToWithCondition(List<Tile> farAdjacents, TileType type, int i, int j)
	{
		int position = this.getListPosition(i, j);
		if((position != -1) && (this.get(position).getType() == type))
		{
			farAdjacents.add(this.get(position));
		}
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
			for(Tile adjacent : this.getAdjacentTiles(tile))
			{
				typedAdjacent.add(adjacent);
			}
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
