package unnamed.model;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PixelisedImage extends Image
{
	private static final Color HIGHLIGHT_COLOR = new Color(255, 255, 0);

	private static PixelisedImage EMPTY;

	private PixelisedImage highlighted;
	private PixelisedImage transparent;

	public PixelisedImage(Image image)
	{
		super(image);
		this.setFilter(Image.FILTER_NEAREST);
	}

	public PixelisedImage(String path) throws SlickException
	{
		super(path);
		this.setFilter(Image.FILTER_NEAREST);
	}

	private PixelisedImage() throws SlickException
	{
		super("assets/empty.png");
		this.setFilter(Image.FILTER_NEAREST);
	}
	
	public boolean isTransparency(int x, int y)
	{
		return this.getColor(x, y).getAlpha() == 0;
	}

	public Image highlighted() throws SlickException
	{
		if(this.highlighted == null)
		{
			this.highlighted = new PixelisedImage(this);

			Graphics graphics = this.highlighted.getGraphics();
			graphics.setColor(HIGHLIGHT_COLOR);

			for(int x = 0; x < this.highlighted.getWidth(); x++)
			{
				for(int y = 0; y < this.highlighted.getHeight(); y++)
				{
					if(this.highlighted.isTransparency(x, y) && this.hasOpaqueNeighbour(x, y))
					{
						graphics.drawRect(x, y, 0.1f, 0.1f);
					}
				}
			}

			graphics.flush();

		}

		return this.highlighted;
	}
	
	private boolean hasOpaqueNeighbour(int x, int y)
	{
		boolean allTransparent = true;
		
		for(int i = x-1; i <= x+1; i++)
		{
			for(int j = y-1; j <= y+1; j++)
			{
				if(i > 0 && i < this.getWidth() && j > 0 && j < this.getHeight())
				{
					allTransparent &= this.isTransparency(i, j);
				}
			}
		}
		return !allTransparent;
	}

	public Image madeTransparent() throws SlickException {
		
		if(this.transparent == null)
		{
			this.transparent = new PixelisedImage(this);
			this.transparent.setAlpha(0);
		}

		return this.transparent;
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
