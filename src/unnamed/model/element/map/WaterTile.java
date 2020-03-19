package unnamed.model.element.map;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class WaterTile extends Tile
{
	private static TileImageRegistry waterRegistry;

	public static void init() throws SlickException
	{
		WaterTile.waterRegistry = new TileImageRegistry("water_shallow");
	}

	public WaterTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
	}

	@Override
	public PixelisedImage getSprite()
	{
		return WaterTile.waterRegistry.get(WaterTile.waterRegistry.getImageNameFor(this.getType(), this.getSpriteVariant()));
	}
}
