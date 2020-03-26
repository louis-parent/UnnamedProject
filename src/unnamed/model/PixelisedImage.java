package unnamed.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PixelisedImage extends Image
{
	private static PixelisedImage EMPTY;

	public PixelisedImage(String path) throws SlickException
	{
		super(path);
		this.setFilter(Image.FILTER_NEAREST);
	}

	private PixelisedImage() throws SlickException
	{
		super("assets/empty.png");
	}

	public static Image getEmptyPixelisedImage() throws SlickException
	{
		if(PixelisedImage.EMPTY == null)
		{
			PixelisedImage.EMPTY = new PixelisedImage();
		}

		return PixelisedImage.EMPTY;
	}
}
