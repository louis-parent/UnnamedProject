package unnamed.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ConcreteTile extends Tile
{

	private static Image flat;
	private static Image mountain;
	
	private Image sprite;

	public static void init() throws SlickException
	{
		ConcreteTile.flat = new Image("assets/tiles/grass_flat_1.png");
		ConcreteTile.mountain = new Image("assets/tiles/grass_mountain_1.png");
	}

	public ConcreteTile(int column, int row)
	{
		super(column, row);

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
	public Image getSprite()
	{
		return this.sprite;
	}

}
