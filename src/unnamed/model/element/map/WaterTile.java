package unnamed.model.element.map;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class WaterTile extends Tile
{
	private static final String NAME = "water_shallow";
	
	private static Map<String, PixelisedImage> waterImages;

	public static void init() throws SlickException
	{
		WaterTile.waterImages = new HashMap<String, PixelisedImage>();
		
		WaterTile.initImageFor(TileType.FLAT);
		WaterTile.initImageFor(TileType.HILL);
		WaterTile.initImageFor(TileType.MOUNTAIN);
	}

	private static void initImageFor(TileType type) throws SlickException
	{
		for(int i = 1; i <= type.getVariantAmount(); i++)
		{
			String imageName = WaterTile.getImageNameFor(type, i);
			WaterTile.waterImages.put(imageName, new PixelisedImage("assets/tiles/" + imageName + ".png"));
		}
	}

	private static String getImageNameFor(TileType type, int variant)
	{
		return NAME + "_" + type + "_" + variant;
	}

	public WaterTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, WaterTile.waterImages.get(WaterTile.getImageNameFor(type, type.getRandomVariant())), container);
	}
}
