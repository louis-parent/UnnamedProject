package unnamed.model.element.map;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class DesertTile extends Tile
{
	private static final String NAME = "desert";
	
	private static Map<String, PixelisedImage> desertImages;

	public static void init() throws SlickException
	{
		DesertTile.desertImages = new HashMap<String, PixelisedImage>();
		
		DesertTile.initImageFor(TileType.FLAT);
		DesertTile.initImageFor(TileType.HILL);
		DesertTile.initImageFor(TileType.MOUNTAIN);
	}

	private static void initImageFor(TileType type) throws SlickException
	{
		for(int i = 1; i <= type.getVariantAmount(); i++)
		{
			String imageName = DesertTile.getImageNameFor(type, i);
			DesertTile.desertImages.put(imageName, new PixelisedImage("assets/tiles/" + imageName + ".png"));
		}
	}

	private static String getImageNameFor(TileType type, int variant)
	{
		return NAME + "_" + type + "_" + variant;
	}

	public DesertTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, DesertTile.desertImages.get(DesertTile.getImageNameFor(type, type.getRandomVariant())), container);
	}
}
