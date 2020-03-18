package unnamed.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PixelisedImage extends Image
{
	public PixelisedImage(String path) throws SlickException
	{
		super(path);
		this.setFilter(Image.FILTER_NEAREST);
	}

	private PixelisedImage()
	{

	}

	public static PixelisedImage getEmptyImage()
	{
		return new PixelisedImage();
	}
}
