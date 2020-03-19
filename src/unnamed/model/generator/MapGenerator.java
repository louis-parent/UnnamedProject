package unnamed.model.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import unnamed.controller.GameController;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.map.GrassTile;
import unnamed.model.element.map.Tile;
import unnamed.model.element.map.TileType;

public class MapGenerator
{
	private static final int DEFAULT_MOUNTAIN_DENSITY = 1;
	private static final int MOUNTAIN_CHAIN_DENSITY = 99;
	private static final int DEFAULT_HILL_DENSITY = 3;
	private static final int HILL_DENSITY_AROUND_MOUNTAINS = 25;
	
	private int rows;
	private int columns;

	private List<Tile> tiles;

	private Random rand;

	public MapGenerator(int numberOfRows, int numberOfColumns)
	{
		this.rows = numberOfRows;
		this.columns = numberOfColumns;

		this.tiles = new ArrayList<Tile>();

		this.rand = GameController.getInstance().getRandom();
	}

	public List<Tile> generateMap(ElementContainer container)
	{
		this.generateGrassBase(container);
		this.generateTerrain();
		this.generateBiomes(container);

		return this.tiles;
	}

	private int getListPosition(int x, int y)
	{
		if((x >= 0 && y >= 0) && (x < this.columns && y < this.rows))
		{
			return this.columns * x + y;
		}
		else
		{
			return -1;
		}
	}

	private void generateGrassBase(ElementContainer container)
	{
		for(int i = 0; i < this.columns; i++)
		{
			for(int j = 0; j < this.rows; j++)
			{
				this.tiles.add(new GrassTile(i, j, TileType.FLAT, container));
			}
		}
	}

	private void generateTerrain()
	{
		this.generateMountains();
		this.generateHills();
	}

	private void generateMountains()
	{
		List<Tile> seeded = this.seedMountains();
		this.expandMountains(seeded);
	}

	private List<Tile> seedMountains()
	{
		List<Tile> seeded = new ArrayList<Tile>();

		int spot = this.rand.nextInt(this.tiles.size());

		Tile changedTile = this.tiles.get(spot);

		changedTile.setType(TileType.MOUNTAIN);
		seeded.add(changedTile);

		List<Tile> adjacentTiles = this.getAdjacentTiles(changedTile);

		Tile randomAdjacentTile;

		do
		{
			randomAdjacentTile = adjacentTiles.get(this.rand.nextInt(adjacentTiles.size()));
		}while(randomAdjacentTile == null);

		randomAdjacentTile.setType(TileType.MOUNTAIN);
		seeded.add(randomAdjacentTile);

		return seeded;
	}

	private List<Tile> getAdjacentTiles(Tile changedTile)
	{
		List<Tile> adjacent = new ArrayList<Tile>();

		adjacent.addAll(this.getTopTiles(changedTile));

		int listPosition = this.getListPosition(changedTile.getColumn() - 1, changedTile.getRow());

		if(listPosition != -1)
		{
			adjacent.add(this.tiles.get(listPosition));
		}

		listPosition = this.getListPosition(changedTile.getColumn() + 1, changedTile.getRow());

		if(listPosition != -1)
		{
			adjacent.add(this.tiles.get(listPosition));
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
			row.add(this.tiles.get(listPosition));
		}

		listPosition = this.getListPosition(leftTileValue + 1, changedTile.getRow() + offset);

		if(listPosition != -1)
		{
			row.add(this.tiles.get(listPosition));
		}

		return row;
	}

	private void expandMountains(List<Tile> seeded)
	{
		this.expandMountains(seeded, 10);
	}

	private List<Tile> expandMountains(List<Tile> seeded, int iteration)
	{
		if(iteration == 0)
		{
			return seeded;
		}
		else
		{
			List<Tile> allMountains = new ArrayList<Tile>(seeded);

			List<Tile> flatAdjacents = this.getAllAdjacentFor(TileType.FLAT, seeded);

			for(Tile tile : flatAdjacents)
			{
				if(this.rand.nextInt(100) <= this.getProbabilityForMountainSpawn(this.getNumberAdjacentsOf(TileType.MOUNTAIN, tile)))
				{
					if(tile.getType() != TileType.MOUNTAIN)
					{
						tile.setType(TileType.MOUNTAIN);
						allMountains.add(tile);
					}
				}
			}

			return expandMountains(allMountains, iteration - 1);
		}
	}

	private int getProbabilityForMountainSpawn(int numberAdjacents)
	{
		if(numberAdjacents == 1)
		{
			return MOUNTAIN_CHAIN_DENSITY;
		}
		else
		{
			return DEFAULT_MOUNTAIN_DENSITY;
		}

	}

	private int getNumberAdjacentsOf(TileType type, Tile source)
	{
		List<Tile> farAdjacents = new ArrayList<Tile>();

		int i = source.getColumn();
		int j = source.getRow();

		addToWithCondition(farAdjacents, type, i - 1, j + 2);
		addToWithCondition(farAdjacents, type, i + 1, j + 2);
		addToWithCondition(farAdjacents, type, i - 2, j);
		addToWithCondition(farAdjacents, type, i + 2, j);
		addToWithCondition(farAdjacents, type, i - 1, j - 2);
		addToWithCondition(farAdjacents, type, i + 1, j - 2);

		return farAdjacents.size();
	}

	private void addToWithCondition(List<Tile> farAdjacents, TileType type, int i, int j)
	{
		int position = this.getListPosition(i, j);
		if(position != -1 && this.tiles.get(position).getType() == type)
		{
			farAdjacents.add(this.tiles.get(position));
		}
	}

	private List<Tile> getAllAdjacentFor(TileType type, List<Tile> tiles)
	{
		List<Tile> adjacents = getAllAdjacent(tiles);

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

	private List<Tile> getAllAdjacent(List<Tile> tiles)
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

	private void generateHills()
	{
		for(int i = 0; i < this.columns; i++)
		{
			for(int j = 0; j < this.rows; j++)
			{
				Tile tile = this.tiles.get(this.getListPosition(i, j));
				int prob = 0;
				
				if(this.getAllAdjacentFor(TileType.MOUNTAIN, Arrays.asList(tile)).size() > 0)
				{
					prob = HILL_DENSITY_AROUND_MOUNTAINS;
				}
				else
				{
					prob = DEFAULT_HILL_DENSITY;
				}
				
				if(this.rand.nextInt(100) <= prob)
				{
					tile.setType(TileType.HILL);
				}
			}
		}

	}

	private void generateBiomes(ElementContainer container)
	{
		// TODO 
	}

}
