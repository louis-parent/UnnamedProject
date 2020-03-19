package unnamed.model.element.map;

import unnamed.model.container.ElementContainer;

public class TileFactory
{
	public static final String GRASS_BIOME = "grass";
	public static final String CORRUPT_BIOME = "corrupt";
	public static final String WATER_BIOME = "water_shallow";
	public static final String DESERT_BIOME = "desert";

	public static Tile create(String biome, int column, int row, TileType type, ElementContainer container)
	{
		switch(biome)
		{
			case GRASS_BIOME:
				return new GrassTile(column, row, type, container);

			case CORRUPT_BIOME:
				return new CorruptTile(column, row, type, container);

			case WATER_BIOME:
				return new WaterTile(column, row, type, container);

			case DESERT_BIOME:
				return new DesertTile(column, row, type, container);

			default:
				return Tile.getEmptyTile();
		}
	}
}
