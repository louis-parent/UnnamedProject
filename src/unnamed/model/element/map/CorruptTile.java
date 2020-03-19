package unnamed.model.element.map;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class CorruptTile extends Tile
{
	private static TileImageRegistry corruptRegistry;

	public static void init() throws SlickException
	{
		CorruptTile.corruptRegistry = new TileImageRegistry(TileFactory.CORRUPT_BIOME);
	}

	protected CorruptTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
	}

	@Override
	public PixelisedImage getSprite()
	{
		return CorruptTile.corruptRegistry.get(CorruptTile.corruptRegistry.getImageNameFor(this.getType(), this.getSpriteVariant()));
	}
}
