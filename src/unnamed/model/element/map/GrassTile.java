package unnamed.model.element.map;

import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;

public class GrassTile extends Tile
{
	private static TileImageRegistry grassRegistry;

	public static void init() throws SlickException
	{
		GrassTile.grassRegistry = new TileImageRegistry("grass");
	}

	public GrassTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, GrassTile.grassRegistry.get(GrassTile.grassRegistry.getImageNameFor(type, type.getRandomVariant())), container);
	}
}
