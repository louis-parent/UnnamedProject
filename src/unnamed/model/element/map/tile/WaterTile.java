package unnamed.model.element.map.tile;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class WaterTile extends Tile
{
	private static final long serialVersionUID = -8248943245607482270L;
	
	private static TileImageRegistry waterRegistry;

	public static void init() throws SlickException
	{
		WaterTile.waterRegistry = new TileImageRegistry(TileBiome.SHALLOW_WATER);
	}

	protected WaterTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
	}

	@Override
	public PixelisedImage getSprite()
	{
		return WaterTile.waterRegistry.get(WaterTile.waterRegistry.getImageNameFor(this.getType(), this.getSpriteVariant()));
	}

	@Override
	public TileBiome getBiome()
	{
		return TileBiome.SHALLOW_WATER;
	}
}
