package unnamed.model.element.map.tile;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class ShallowWaterTile extends Tile
{
	private static final long serialVersionUID = -8248943245607482270L;
	
	private static TileImageRegistry waterRegistry;

	public static void init() throws SlickException
	{
		ShallowWaterTile.waterRegistry = new TileImageRegistry(TileBiome.SHALLOW_WATER);
	}

	protected ShallowWaterTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
	}

	@Override
	public PixelisedImage getSprite()
	{
		return ShallowWaterTile.waterRegistry.get(ShallowWaterTile.waterRegistry.getImageNameFor(this.getType(), this.getSpriteVariant()));
	}

	@Override
	public TileBiome getBiome()
	{
		return TileBiome.SHALLOW_WATER;
	}
}
