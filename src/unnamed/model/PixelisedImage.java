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

	private PixelisedImage() throws SlickException
	{
		super("assets/empty.png");
	}
	
	public static PixelisedImage getEmpty() throws SlickException
	{
		return new PixelisedImage();
	}
}
