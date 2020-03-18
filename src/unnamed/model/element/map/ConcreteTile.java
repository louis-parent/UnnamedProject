package unnamed.model.element.map;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class ConcreteTile extends Tile
{

	private static PixelisedImage flat;
	private static PixelisedImage mountain;

	private PixelisedImage sprite;

	public static void init() throws SlickException
	{
		ConcreteTile.flat = new PixelisedImage("assets/tiles/grass_flat_1.png");
		ConcreteTile.mountain = new PixelisedImage("assets/tiles/grass_mountain_1.png");
	}

	public ConcreteTile(int column, int row, ElementContainer container)
	{
		super(column, row, container);

		if((column + row) % 7 == 0)
		{
			this.sprite = ConcreteTile.mountain;
		}
		else
		{
			this.sprite = ConcreteTile.flat;
		}
	}

	@Override
	public PixelisedImage getSprite()
	{
		return this.sprite;
	}

	@Override
	public void pressed()
	{
	}

	@Override
	public void mouseLeft()
	{
	}

}
