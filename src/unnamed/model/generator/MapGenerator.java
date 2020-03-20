package unnamed.model.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import unnamed.controller.GameController;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.map.Map;
import unnamed.model.element.map.Tile;
import unnamed.model.element.map.TileFactory;
import unnamed.model.element.map.TileType;

public class MapGenerator
{
	private static final int SURFACE_FOR_MOUNTAIN_CHAIN = 2500;
	private static final int MOUNTAIN_DIAMETER = 15;
	private static final int DEFAULT_MOUNTAIN_DENSITY = 1;
	private static final int MOUNTAIN_CHAIN_DENSITY = 99;

	private static final int HILL_DENSITY_IN_MOUNTAIN = 15;
	private static final int DEFAULT_HILL_DENSITY = 3;
	private static final int HILL_DENSITY_AROUND_MOUNTAINS = 25;

	private int columns;
	private int rows;

	private Map map;

	private Random rand;

	public MapGenerator(int numberOfColumns, int numberOfRows)
	{
		this.columns = numberOfColumns;
		this.rows = numberOfRows;

		this.map = new Map(numberOfColumns, numberOfRows);

		this.rand = GameController.getInstance().getRandom();
	}

	public Map generateMap(ElementContainer container)
	{
		this.generateGrassBase(container);
		this.generateTerrain();
		this.generateBiomes(container);

		return this.map;
	}

	private void generateGrassBase(ElementContainer container)
	{
		for(int i = 0; i < this.columns; i++)
		{
			for(int j = 0; j < this.rows; j++)
			{
				this.map.add(TileFactory.create(TileFactory.GRASS_BIOME, i, j, TileType.FLAT, container));
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
		for(int i = 0; i < ((this.rows * this.columns) / MapGenerator.SURFACE_FOR_MOUNTAIN_CHAIN); i++)
		{
			List<Tile> seeded = this.seedMountains();
			this.expandMountains(seeded);
		}
	}

	private List<Tile> seedMountains()
	{
		List<Tile> seeded = new ArrayList<Tile>();

		int spot = this.rand.nextInt(this.map.size());

		Tile changedTile = this.map.get(spot);

		changedTile.setType(TileType.MOUNTAIN);
		seeded.add(changedTile);

		List<Tile> adjacentTiles = this.map.getAdjacentTiles(changedTile);

		Tile randomAdjacentTile;

		do
		{
			randomAdjacentTile = adjacentTiles.get(this.rand.nextInt(adjacentTiles.size()));
		}while(randomAdjacentTile == null);

		randomAdjacentTile.setType(TileType.MOUNTAIN);
		seeded.add(randomAdjacentTile);

		return seeded;
	}

	private void expandMountains(List<Tile> seeded)
	{
		this.expandMountains(seeded, MapGenerator.MOUNTAIN_DIAMETER);
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

			List<Tile> flatAdjacents = this.map.getAllAdjacentFor(TileType.FLAT, seeded);

			for(Tile tile : flatAdjacents)
			{
				if(this.rand.nextInt(100) <= this.getProbabilityForMountainSpawn(this.map.getNumberAdjacentsOf(TileType.MOUNTAIN, tile)))
				{
					if(tile.getType() != TileType.MOUNTAIN)
					{
						tile.setType(TileType.MOUNTAIN);
						allMountains.add(tile);
					}
				}
			}

			return this.expandMountains(allMountains, iteration - 1);
		}
	}

	private int getProbabilityForMountainSpawn(int numberAdjacents)
	{
		if(numberAdjacents == 1)
		{
			return MapGenerator.MOUNTAIN_CHAIN_DENSITY;
		}
		else
		{
			return MapGenerator.DEFAULT_MOUNTAIN_DENSITY;
		}

	}

	private void generateHills()
	{
		for(int i = 0; i < this.columns; i++)
		{
			for(int j = 0; j < this.rows; j++)
			{
				Tile tile = this.map.get(this.map.getListPosition(i, j));
				int prob = 0;

				if(tile.getType() == TileType.MOUNTAIN)
				{
					prob = MapGenerator.HILL_DENSITY_IN_MOUNTAIN;
				}
				else if(this.map.getAllAdjacentFor(TileType.MOUNTAIN, Arrays.asList(tile)).size() > 0)
				{
					prob = MapGenerator.HILL_DENSITY_AROUND_MOUNTAINS;
				}
				else
				{
					prob = MapGenerator.DEFAULT_HILL_DENSITY;
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
