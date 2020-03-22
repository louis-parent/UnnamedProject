package unnamed.model.element.map.tile;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class DeepWaterTile extends Tile
{
	private static final long serialVersionUID = 5853996703796333931L;
	
	private static TileImageRegistry waterRegistry;

	public static void init() throws SlickException
	{
		DeepWaterTile.waterRegistry = new TileImageRegistry(TileBiome.DEEP_WATER);
	}

	protected DeepWaterTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
	}

	@Override
	public PixelisedImage getSprite()
	{
		return DeepWaterTile.waterRegistry.get(DeepWaterTile.waterRegistry.getImageNameFor(this.getType(), this.getSpriteVariant()));
	}

	@Override
	public TileBiome getBiome()
	{
		return TileBiome.DEEP_WATER;
	}
}
