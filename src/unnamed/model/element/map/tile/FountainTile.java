package unnamed.model.element.map.tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class FountainTile extends Tile
{
	private static final long serialVersionUID = 9032972465859204319L;
	
	private static Image sprite;

	public static void init() throws SlickException
	{
		FountainTile.sprite = new PixelisedImage("assets/tiles/fountain.png");
	}

	protected FountainTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
	}

	@Override
	public Image getSprite()
	{
		return FountainTile.sprite;
	}
	
	@Override
	public TileBiome getBiome()
	{
		return TileBiome.FOUNTAIN;
	}
}
