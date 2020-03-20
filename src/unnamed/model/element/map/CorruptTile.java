package unnamed.model.element.map;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class CorruptTile extends Tile
{
	private static final int RAISE_OFFSET = 13;
	private static final int FALLING_SPEED = 1;
	
	private static TileImageRegistry corruptRegistry;

	public static void init() throws SlickException
	{
		CorruptTile.corruptRegistry = new TileImageRegistry(TileFactory.CORRUPT_BIOME);
	}

	private float targetY;

	protected CorruptTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);
		
		this.targetY = RAISE_OFFSET;
		this.setY(this.getY() - RAISE_OFFSET);
	}

	@Override
	public void tickUpdate()
	{
		if(this.targetY > 0 && !this.isSelected())
		{
			this.setY(this.getY() + FALLING_SPEED);
			this.targetY -= FALLING_SPEED;
		}
	}
	
	@Override
	protected void select()
	{
		this.setY(this.getY() + this.targetY);
		this.targetY = 0;
		super.select();
	}
	
	@Override
	public PixelisedImage getSprite()
	{
		return CorruptTile.corruptRegistry.get(CorruptTile.corruptRegistry.getImageNameFor(this.getType(), this.getSpriteVariant()));
	}
}
