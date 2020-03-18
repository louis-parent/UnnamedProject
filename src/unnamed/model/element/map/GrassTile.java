package unnamed.model.element.map;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class GrassTile extends Tile
{
	private static final String NAME = "grass";
	
	private static Map<String, PixelisedImage> grassImages;

	public static void init() throws SlickException
	{
		GrassTile.grassImages = new HashMap<String, PixelisedImage>();
		
		GrassTile.initImageFor(TileType.FLAT);
		GrassTile.initImageFor(TileType.HILL);
		GrassTile.initImageFor(TileType.MOUNTAIN);
	}

	private static void initImageFor(TileType type) throws SlickException
	{
		for(int i = 1; i <= type.getVariantAmount(); i++)
		{
			String imageName = GrassTile.getImageNameFor(type, i);
			GrassTile.grassImages.put(imageName, new PixelisedImage("assets/tiles/" + imageName + ".png"));
		}
	}

	private static String getImageNameFor(TileType type, int variant)
	{
		return NAME + "_" + type + "_" + variant;
	}

	public GrassTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, GrassTile.grassImages.get(GrassTile.getImageNameFor(type, type.getRandomVariant())), container);
	}
}
