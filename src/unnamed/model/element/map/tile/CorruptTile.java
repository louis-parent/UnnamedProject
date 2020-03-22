package unnamed.model.element.map.tile;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class CorruptTile extends Tile
{
	private static final long serialVersionUID = -2223056741288411428L;

	private static final int RAISE_OFFSET = 13;
	private static final int FALLING_SPEED = 1;

	private static TileImageRegistry corruptRegistry;

	public static void init() throws SlickException
	{
		CorruptTile.corruptRegistry = new TileImageRegistry(TileBiome.CORRUPT);
	}

	private float targetY;

	protected CorruptTile(int column, int row, TileType type, ElementContainer container)
	{
		super(column, row, type, container);

		this.targetY = CorruptTile.RAISE_OFFSET;
		this.setY(this.getY() - CorruptTile.RAISE_OFFSET);
	}

	@Override
	public void tickUpdate()
	{
		if((this.targetY > 0) && !this.isSelected())
		{
			this.setY(this.getY() + CorruptTile.FALLING_SPEED);
			this.targetY -= CorruptTile.FALLING_SPEED;
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

	@Override
	public TileBiome getBiome()
	{
		return TileBiome.CORRUPT;
	}
}
