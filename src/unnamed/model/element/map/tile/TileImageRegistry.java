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

	private Map<String, Image> images;

	public TileImageRegistry() throws SlickException
	{
		this.images = new HashMap<String, Image>();

		for(TileBiome biome : TileBiome.values())
		{
			for(TileType type : TileType.values())
			{
				this.initImageFor(biome, type);
			}
		}
	}

	private void initImageFor(TileBiome biome, TileType type) throws SlickException
	{
		for(int i = 1; i <= type.getVariantAmount(); i++)
		{
			String imageName = this.getImageNameFor(biome, type, i);
			this.images.put(imageName, new PixelisedImage(TileImageRegistry.TILES_PATH + imageName + TileImageRegistry.IMAGE_EXTENSION));
		}
	}

	public String getImageNameFor(TileBiome biome, TileType type, int variant)
	{
		if(biome != TileBiome.FOUNTAIN)
		{
			return biome + "_" + type + "_" + variant;
		}
		else
		{
			return biome.toString();
		}
	}

	public Image get(String imageName)
	{
		return this.images.get(imageName);
	}
}
