package unnamed.model.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import unnamed.controller.GameController;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.map.Map;
import unnamed.model.element.map.tile.GrassTile;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileFactory;
import unnamed.model.element.map.tile.TileType;
import unnamed.model.element.map.tile.WaterTile;

public class MapGenerator
{
	private static final int SURFACE_FOR_TERRAIN_DECORATION = 2500;
	private static final int MOUNTAIN_DIAMETER = 15;
	private static final int DEFAULT_MOUNTAIN_DENSITY = 1;
	private static final int MOUNTAIN_CHAIN_DENSITY = 99;

	private static final int HILL_DENSITY_IN_MOUNTAIN = 15;
	private static final int DEFAULT_HILL_DENSITY = 3;
	private static final int HILL_DENSITY_AROUND_MOUNTAINS = 25;

	private static final int DESERT_DIAMETER = 20;
	private static final int DESERT_SPREAD_PERCENTAGE = 18;

	private int columns;
	private int rows;

	private Map map;

	private Random rand;
	private List<Tile> corruptTiles;

	public MapGenerator(int numberOfColumns, int numberOfRows)
	{
		this.columns = numberOfColumns;
		this.rows = numberOfRows;

		this.map = new Map(numberOfColumns, numberOfRows);
		this.corruptTiles = new ArrayList<Tile>();

		this.rand = GameController.getInstance().getRandom();
	}

	public Map generateMap(ElementContainer container)
	{
		this.generateGrassBase(container);
		this.generateTerrain();
		this.generateBiomes(container);
		this.seedCorruption();
		this.createFountain();

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
		for(int i = 0; i < ((this.rows * this.columns) / MapGenerator.SURFACE_FOR_TERRAIN_DECORATION); i++)
		{
			List<Tile> seeded = this.seedMountains();
			this.expandMountains(seeded);
		}
	}

	private List<Tile> seedMountains()
	{
		List<Tile> seeded = new ArrayList<Tile>();

		Tile changedTile = this.map.get(this.map.getRandomTileIndex());

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
		this.generateWaterBiome(container);
		this.generateDesertBiome(container);
	}

	private void generateWaterBiome(ElementContainer container)
	{
		for(int i = 0; i < ((this.rows * this.columns) / MapGenerator.SURFACE_FOR_TERRAIN_DECORATION); i++)
		{
			Tile seeded = this.seedWater();
			this.expandWater(seeded);
		}

		for(int i = 0; i < (((this.rows * this.columns) / MapGenerator.SURFACE_FOR_TERRAIN_DECORATION) * 8); i++)
		{
			this.generateRiver();
		}
	}

	private Tile seedWater()
	{
		return this.seedBiome(TileFactory.WATER_BIOME);
	}

	private Tile seedBiome(String biome)
	{
		return this.setTileAt(this.map.getRandomTileIndex(GrassTile.class), biome);
	}
	
	private Tile setTileAt(int index, String biome)
	{
		Tile oldTile = this.map.get(index);
		Tile newTile = TileFactory.createFrom(biome, oldTile);
		this.map.set(index, newTile);

		return newTile;
	}

	private void expandWater(Tile seeded)
	{
		List<Tile> toExpand = new ArrayList<Tile>();
		toExpand.add(seeded);

		double turnCounter = 1.0;

		while(!toExpand.isEmpty())
		{
			List<Tile> toBuild = new ArrayList<Tile>(this.map.getAllAdjacentFor(GrassTile.class, toExpand));
			toExpand.clear();

			for(Tile tile : toBuild)
			{
				if(this.rand.nextDouble() <= ((1.0 / turnCounter) + 0.15))
				{
					this.replaceTileWithIn(tile, TileFactory.WATER_BIOME, toExpand);
				}

			}

			turnCounter++;
		}
	}

	private void replaceTileWithIn(Tile tile, String biome, List<Tile> toExpand)
	{
		if(this.map.contains(tile))
		{
			Tile newTile = TileFactory.createFrom(biome, tile);
			this.map.set(this.map.indexOf(tile), newTile);
			toExpand.add(newTile);
		}
	}

	private void generateRiver()
	{
		int selectedMountain = this.map.getRandomTileIndex(TileType.MOUNTAIN);

		List<Integer> previousIndex = this.initListWith(-1);
		List<Float> distance = this.initListWith(Float.MAX_VALUE);

		this.dijkstra(selectedMountain, previousIndex, distance);

		int closestIndex = this.findClosestWater(distance);

		this.generateRiverFrom(closestIndex, previousIndex);

	}

	private <T> List<T> initListWith(T defaultValue)
	{
		List<T> list = new ArrayList<T>();

		for(int i = 0; i < this.map.size(); i++)
		{
			list.add(defaultValue);
		}

		return list;
	}

	private void dijkstra(int selectedMountain, List<Integer> previousIndex, List<Float> distance)
	{
		List<Float> weight = this.initWeights();
		List<Boolean> seen = this.initListWith(Boolean.FALSE);

		previousIndex.set(selectedMountain, selectedMountain);
		distance.set(selectedMountain, 0f);

		while(seen.contains(Boolean.FALSE))
		{
			int minValueIndex = this.getSmallestUnseenIndex(seen, distance);
			float currentDistance = distance.get(minValueIndex);

			seen.set(minValueIndex, true);

			for(Tile tile : this.map.getAdjacentTiles(this.map.get(minValueIndex)))
			{
				int current = this.map.indexOf(tile);
				float newDistance = (currentDistance == Float.MAX_VALUE) || (weight.get(current) == Float.MAX_VALUE) ? Float.MAX_VALUE : currentDistance + weight.get(current);

				if(!seen.get(current) && (distance.get(current) > newDistance))
				{
					distance.set(current, newDistance);
					previousIndex.set(current, minValueIndex);
				}

			}
		}
	}

	private List<Float> initWeights()
	{
		List<Float> list = new ArrayList<Float>();

		for(Tile element : this.map)
		{
			switch(element.getType())
			{
				case FLAT:
					list.add((this.rand.nextFloat() + 1) * 3);
					break;

				case HILL:
					list.add((this.rand.nextFloat() + 1) * 6);
					break;

				case MOUNTAIN:
					list.add(20000f);
					break;
			}
		}

		return list;
	}

	private int getSmallestUnseenIndex(List<Boolean> seen, List<Float> distance)
	{
		float min = Float.MAX_VALUE;
		int pos = -1;

		for(int i = 0; i < distance.size(); i++)
		{
			if(!seen.get(i) && (distance.get(i) <= min))
			{
				min = distance.get(i);
				pos = i;
			}
		}

		return pos;
	}

	private int findClosestWater(List<Float> distance)
	{
		float min = Float.MAX_VALUE;
		int pos = -1;

		for(int i = 0; i < distance.size(); i++)
		{
			if((this.map.get(i) instanceof WaterTile) && (distance.get(i) <= min))
			{
				min = distance.get(i);
				pos = i;
			}
		}

		return pos;
	}

	private void generateRiverFrom(int closestIndex, List<Integer> previousIndex)
	{
		int currentIndex = closestIndex;

		while(previousIndex.get(currentIndex) != currentIndex)
		{
			Tile newTile = TileFactory.createFrom(TileFactory.WATER_BIOME, this.map.get(currentIndex));
			this.map.set(currentIndex, newTile);

			currentIndex = previousIndex.get(currentIndex);
		}

	}

	private void generateDesertBiome(ElementContainer container)
	{
		for(int i = 0; i < ((this.rows * this.columns) / MapGenerator.SURFACE_FOR_TERRAIN_DECORATION); i++)
		{
			Tile seed = this.generateDesertSeed();
			this.expandDesert(seed);
		}
	}

	private Tile generateDesertSeed()
	{
		return this.seedBiome(TileFactory.DESERT_BIOME);
	}

	private void expandDesert(Tile seed)
	{
		List<Tile> desert = new ArrayList<Tile>();
		desert.add(seed);

		for(int i = 0; i < MapGenerator.DESERT_DIAMETER; i++)
		{

			List<Tile> adjacents = this.map.getAllAdjacentFor(GrassTile.class, desert);

			for(Tile adjacent : adjacents)
			{
				if(this.rand.nextInt(100) < MapGenerator.DESERT_SPREAD_PERCENTAGE)
				{
					this.replaceTileWithIn(adjacent, TileFactory.DESERT_BIOME, desert);
				}
			}
		}
	}

	private void seedCorruption()
	{
		Tile corruptTile = this.setTileAt(this.map.getRandomTileIndex(), TileFactory.CORRUPT_BIOME);
		this.corruptTiles.add(corruptTile);
	}

	private void createFountain()
	{
		this.setTileAt(this.map.getRandomTileIndex(GrassTile.class, TileType.FLAT), TileFactory.FOUNTAIN_TILE);
	}

	public List<Tile> getCorruptTiles()
	{
		return this.corruptTiles;
	}
}
