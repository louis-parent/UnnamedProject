package unnamed.model.element.map.tile;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;

public class TileImageRegistry
{
	private static final String TILES_PATH = "assets/tiles/";
	private static final String IMAGE_EXTENSION = ".png";

	private final String NAME;

	private Map<String, Image> images;

	public TileImageRegistry(TileBiome biome) throws SlickException
	{
		this.NAME = biome.toString();
		this.images = new HashMap<String, Image>();

		this.initImageFor(TileType.FLAT);
		this.initImageFor(TileType.HILL);
		this.initImageFor(TileType.MOUNTAIN);
	}

	private void initImageFor(TileType type) throws SlickException
	{
		for(int i = 1; i <= type.getVariantAmount(); i++)
		{
			String imageName = this.getImageNameFor(type, i);
			this.images.put(imageName, new PixelisedImage(TileImageRegistry.TILES_PATH + imageName + TileImageRegistry.IMAGE_EXTENSION));
		}
	}

	public String getImageNameFor(TileType type, int variant)
	{
		return this.NAME + "_" + type + "_" + variant;
	}

	public Image get(String imageName)
	{
		return this.images.get(imageName);
	}
}