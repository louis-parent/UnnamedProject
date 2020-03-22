package unnamed.model.element.map.tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;

public class DesertTile extends Tile
{
	private static final long serialVersionUID = 5315208958463114529L;
	
	private static TileImageRegistry desertRegistry;

	public static void init() throws SlickException
	{
		DesertTile.desertRegistry = new TileImageRegistry(TileBiome.DESERT);
	}

	protected DesertTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
	}

	@Override
	public Image getSprite()
	{
		return DesertTile.desertRegistry.get(DesertTile.desertRegistry.getImageNameFor(this.getType(), this.getSpriteVariant()));
	}

	@Override
	public TileBiome getBiome()
	{
		return TileBiome.DESERT;
	}
}
