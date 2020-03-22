package unnamed.model.element.map.tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;

public class GrassTile extends Tile
{
	private static final long serialVersionUID = -7245963920095485122L;
	
	private static TileImageRegistry grassRegistry;

	public static void init() throws SlickException
	{
		GrassTile.grassRegistry = new TileImageRegistry(TileBiome.GRASS);
	}

	protected GrassTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
	}

	@Override
	public Image getSprite()
	{
		return GrassTile.grassRegistry.get(GrassTile.grassRegistry.getImageNameFor(this.getType(), this.getSpriteVariant()));
	}

	@Override
	public TileBiome getBiome()
	{
		return TileBiome.GRASS;
	}
}
