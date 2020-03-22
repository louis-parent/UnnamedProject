package unnamed.model.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.map.Map;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;
import unnamed.model.element.map.tile.TileFactory;
import unnamed.model.element.map.tile.TileType;

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

	private static final double MIN_CONVERGING_PROBABILITY_WATER_DIAMETER = 0.15;
	private static final int FLAT_WEIGHT_FOR_RIVER = 3;
	private static final int HILL_WEIGHT_FOR_RIVER = FLAT_WEIGHT_FOR_RIVER * 3;
	private static final int MOUNTAIN_WEIGHT_FOR_RIVER = 200000;
	private static final int RIVER_PER_MOUNTAIN_CHAIN = 4;

	private int columns;
	private int rows;

	private Map map;

	private Random rand;
	private List<Tile> corruptTiles;
	private Tile fountain;

	public MapGenerator(int numberOfColumns, int numberOfRows)
	{
		this.columns = numberOfColumns;
		this.rows = numberOfRows;

		this.map = new Map(numberOfColumns, numberOfRows);
		this.corruptTiles = new ArrayList<Tile>();
		this.fountain = Tile.EMPTY;

		this.rand = GameController.getInstance().getRandom();
	}

	public Map generateMap(ElementContainer container) throws SlickException
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
				this.map.add(TileFactory.create(TileBiome.GRASS, i, j, TileType.FLAT, container));
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

	private void generateBiomes(ElementContainer container) throws SlickException
	{
		this.generateWaterBiome(container);
		this.generateDesertBiome(container);
	}

	private void generateWaterBiome(ElementContainer container) throws SlickException
	{
		for(int i = 0; i < ((this.rows * this.columns) / MapGenerator.SURFACE_FOR_TERRAIN_DECORATION); i++)
		{
			Tile seeded = this.seedWater();
			this.expandWater(seeded);
		}

		for(int i = 0; i < (((this.rows * this.columns) / MapGenerator.SURFACE_FOR_TERRAIN_DECORATION) * RIVER_PER_MOUNTAIN_CHAIN); i++)
		{
			this.generateRiver();
		}
	}

	private Tile seedWater() throws SlickException
	{
		return this.seedBiome(TileBiome.DEEP_WATER);
	}

	private Tile seedBiome(TileBiome biome) throws SlickException
	{
		return this.setTileAt(this.map.getRandomTileIndex(TileBiome.GRASS), biome);
	}

	private Tile setTileAt(int index, TileBiome biome) throws SlickException
	{
		Tile oldTile = this.map.get(index);
		Tile newTile = TileFactory.createFrom(biome, oldTile);
		this.map.set(index, newTile);

		return newTile;
	}

	private void expandWater(Tile seeded) throws SlickException
	{
		List<Tile> toExpand = new ArrayList<Tile>();
		toExpand.add(seeded);

		double turnCounter = 1.0;

		while(!toExpand.isEmpty())
		{
			List<Tile> toBuild = new ArrayList<Tile>(this.map.getAllAdjacentFor(TileBiome.GRASS, toExpand));
			toExpand.clear();

			for(Tile tile : toBuild)
			{
				if(this.rand.nextDouble() <= ((1.0 / turnCounter) + MIN_CONVERGING_PROBABILITY_WATER_DIAMETER))
				{
					this.replaceTileWithIn(tile, TileBiome.DEEP_WATER, toExpand);
				}

			}

			turnCounter++;
		}
	}

	private void replaceTileWithIn(Tile tile, TileBiome biome, List<Tile> toExpand) throws SlickException
	{
		if(this.map.contains(tile))
		{
			Tile newTile = TileFactory.createFrom(biome, tile);
			this.map.set(this.map.indexOf(tile), newTile);
			toExpand.add(newTile);
		}
	}

	private void generateRiver() throws SlickException
	{
		int selectedMountain = this.map.getRandomTileIndex(TileType.MOUNTAIN);

		List<Integer> previousIndex = this.initListWith(-1);
		List<Float> distance = this.initListWith(Float.MAX_VALUE);

		this.dijkstra(selectedMountain, previousIndex, distance);

		int closestIndex = previousIndex.get(this.findClosestWater(distance));

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
			if(element.getBiome() != TileBiome.SHALLOW_WATER)
			{
				switch(element.getType())
				{
					case FLAT:
						list.add((this.rand.nextFloat() + 1) * FLAT_WEIGHT_FOR_RIVER);
						break;

					case HILL:
						list.add((this.rand.nextFloat() + 1) * HILL_WEIGHT_FOR_RIVER);
						break;

					case MOUNTAIN:
						list.add((float) MOUNTAIN_WEIGHT_FOR_RIVER);
						break;
				}
			}
			else
			{
				list.add(this.rand.nextFloat());
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
			if(this.map.get(i).getBiome() == TileBiome.DEEP_WATER && (distance.get(i) <= min))
			{
				min = distance.get(i);
				pos = i;
			}
		}

		return pos;
	}

	private void generateRiverFrom(int closestIndex, List<Integer> previousIndex) throws SlickException
	{
		int currentIndex = closestIndex;

		while(previousIndex.get(currentIndex) != currentIndex)
		{
			Tile newTile = TileFactory.createFrom(TileBiome.SHALLOW_WATER, this.map.get(currentIndex));
			this.map.set(currentIndex, newTile);

			currentIndex = previousIndex.get(currentIndex);
		}

	}

	private void generateDesertBiome(ElementContainer container) throws SlickException
	{
		for(int i = 0; i < ((this.rows * this.columns) / MapGenerator.SURFACE_FOR_TERRAIN_DECORATION); i++)
		{
			Tile seed = this.generateDesertSeed();
			this.expandDesert(seed);
		}
	}

	private Tile generateDesertSeed() throws SlickException
	{
		return this.seedBiome(TileBiome.DESERT);
	}

	private void expandDesert(Tile seed) throws SlickException
	{
		List<Tile> desert = new ArrayList<Tile>();
		desert.add(seed);

		for(int i = 0; i < MapGenerator.DESERT_DIAMETER; i++)
		{

			List<Tile> adjacents = this.map.getAllAdjacentFor(TileBiome.GRASS, desert);

			for(Tile adjacent : adjacents)
			{
				if(this.rand.nextInt(100) < MapGenerator.DESERT_SPREAD_PERCENTAGE)
				{
					this.replaceTileWithIn(adjacent, TileBiome.DESERT, desert);
				}
			}
		}
	}

	private void seedCorruption() throws SlickException
	{
		Tile corruptTile = this.setTileAt(this.map.getRandomTileIndex(), TileBiome.CORRUPT);
		this.corruptTiles.add(corruptTile);
	}

	private void createFountain() throws SlickException
	{
		this.fountain = this.setTileAt(this.map.getRandomTileIndex(TileBiome.GRASS, TileType.FLAT), TileBiome.FOUNTAIN);
	}

	public List<Tile> getCorruptTiles()
	{
		return this.corruptTiles;
	}

	public Tile getFountain()
	{
		return this.fountain;
	}
}
