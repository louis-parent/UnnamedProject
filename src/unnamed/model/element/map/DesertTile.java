package unnamed.model.element.map;

import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;

public class DesertTile extends Tile
{
	private static TileImageRegistry desertRegistry;

	public static void init() throws SlickException
	{
		DesertTile.desertRegistry = new TileImageRegistry("desert");
	}

	public DesertTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, DesertTile.desertRegistry.get(DesertTile.desertRegistry.getImageNameFor(type, type.getRandomVariant())), container);
	}
}
