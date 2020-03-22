package unnamed.model.element.map.tile;

import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;

public class TileFactory
{	
	public static void init() throws SlickException
	{
		GrassTile.init();
		CorruptTile.init();
		ShallowWaterTile.init();
		DeepWaterTile.init();
		DesertTile.init();
		FountainTile.init();
	}

	public static Tile create(TileBiome biome, int column, int row, TileType type, ElementContainer container)
	{
		switch(biome)
		{
			case GRASS:
				return new GrassTile(column, row, type, container);

			case CORRUPT:
				return new CorruptTile(column, row, type, container);

			case SHALLOW_WATER:
				return new ShallowWaterTile(column, row, type, container);

			case DEEP_WATER:
				return new DeepWaterTile(column, row, type, container);

			case DESERT:
				return new DesertTile(column, row, type, container);

			case FOUNTAIN:
				return new FountainTile(column, row, type, container);
				
			default:
				return Tile.EMPTY;
		}
	}

	public static Tile createFrom(TileBiome biome, Tile tile)
	{
		Tile created = TileFactory.create(biome, tile.getColumn(), tile.getRow(), tile.getType(), tile.getContainer());

		if(tile.isSelected())
		{
			created.select();
		}

		return created;
	}
}
