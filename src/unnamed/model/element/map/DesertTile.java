package unnamed.model.element.map;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class DesertTile extends Tile
{
	private static TileImageRegistry desertRegistry;

	public static void init() throws SlickException
	{
		DesertTile.desertRegistry = new TileImageRegistry(TileFactory.DESERT_BIOME);
	}

	protected DesertTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
	}

	@Override
	public PixelisedImage getSprite()
	{
		return DesertTile.desertRegistry.get(DesertTile.desertRegistry.getImageNameFor(this.getType(), this.getSpriteVariant()));
	}
}
