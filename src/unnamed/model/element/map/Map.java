package unnamed.model.element.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import unnamed.controller.GameController;
import unnamed.model.element.map.tile.Tile;
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
		List<Tile> adjacents = this.getAllAdjacent(tiles);

		for(int i = 0; i < adjacents.size(); i++)
		{
			if(adjacents.get(i).getType() != type)
			{
				adjacents.remove(i);
				i--;
			}
		}

		return adjacents;
	}

	public List<Tile> getAllAdjacentFor(Class<? extends Tile> tileClass, List<Tile> tiles)
	{
		List<Tile> adjacents = this.getAllAdjacent(tiles);

		for(int i = 0; i < adjacents.size(); i++)
		{
			if(!adjacents.get(i).getClass().equals(tileClass))
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

	public int getRandomTileIndex()
	{
		Random rand = GameController.getInstance().getRandom();
		return rand.nextInt(this.size());
	}

	public int getRandomTileIndex(Class<? extends Tile> tileClass)
	{
		return this.getRandomTileIndex(tile -> tile.getClass().equals(tileClass));
	}

	public int getRandomTileIndex(TileType type)
	{
		return this.getRandomTileIndex(tile -> tile.getType() == type);
	}
	
	public int getRandomTileIndex(Class<? extends Tile> tileClass, TileType type)
	{
		return this.getRandomTileIndex(tile -> tile.getClass().equals(tileClass) && tile.getType() == type);
	}
	
	private int getRandomTileIndex(Predicate<Tile> predicate)
	{
		int selected = -1;

		boolean found = false;

		while(!found)
		{
			selected = getRandomTileIndex();

			Tile tile = this.get(selected);
			if(predicate.test(tile))
			{
				found = true;
			}
		}

		return selected;
	}
}
