package unnamed.model.element.map.tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;
import unnamed.model.element.menu.FormattedString;

public abstract class Tile extends Element
{
	private static final long serialVersionUID = -867365679274168458L;
	
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 29;

	public static final int FLOATING_OFFSET = 8;
	private static final int Z_SPACE_BETWEEN_TILES = 10;

	public static final Tile EMPTY = new EmptyTile();

	private static Tile lastSelected;

	private int column;
	private int row;

	private TileType type;

	private boolean isSelected;

	private int spriteVariant;

	public Tile(TileType type, ElementContainer container)
	{
		this(0, 0, type, container);
	}

	public Tile(int column, int row, TileType type, ElementContainer container)
	{
		super(Tile.getXValueFrom(column, row), Tile.getYValueFrom(row), Tile.getZValueFrom(row), container);
		this.column = column;
		this.row = row;

		this.type = type;
		this.spriteVariant = type.getRandomVariant();

		this.isSelected = false;
	}

	public int getColumn()
	{
		return this.column;
	}

	public int getRow()
	{
		return this.row;
	}

	public boolean isSelected()
	{
		return this.isSelected;
	}

	public TileType getType()
	{
		return this.type;
	}
	

	public void setType(TileType type)
	{
		this.type = type;
		this.setSpriteVariant(type.getRandomVariant());
	}

	public abstract TileBiome getBiome();

	@Override
	public FormattedString getFormattedText()
	{
		return FormattedString.EMPTY;
	}
	
	@Override
	public void tickUpdate()
	{
	}

	@Override
	public void click()
	{
		if(this.isSelected())
		{
			this.deselect();
		}
		else
		{
			this.select();
		}
	}

	@Override
	public void pressed()
	{
	}

	@Override
	public void mouseLeft()
	{
	}

	protected void select()
	{
		this.isSelected = true;
		this.setY(this.getY() - Tile.FLOATING_OFFSET);

		if(Tile.lastSelected != null)
		{
			Tile.lastSelected.deselect();
		}

		Tile.lastSelected = this;
	}

	protected void deselect()
	{
		this.isSelected = false;
		this.setY(this.getY() + Tile.FLOATING_OFFSET);

		if(Tile.lastSelected == this)
		{
			Tile.lastSelected = null;
		}
	}

	private static float getXValueFrom(int column, int row)
	{
		if((row % 2) == 0)
		{
			return column * Tile.TILE_WIDTH;
		}
		else
		{
			return (column * Tile.TILE_WIDTH) + (Tile.TILE_WIDTH / 2);
		}
	}

	private static float getYValueFrom(int row)
	{
		if(row != 0)
		{
			return ((row * Tile.TILE_HEIGHT) - (Tile.FLOATING_OFFSET * row)) + Tile.FLOATING_OFFSET;
		}
		else
		{
			return Tile.FLOATING_OFFSET;
		}
	}

	private static int getZValueFrom(int row)
	{
		return row * Tile.Z_SPACE_BETWEEN_TILES;
	}

	protected int getSpriteVariant()
	{
		return this.spriteVariant;
	}

	protected void setSpriteVariant(int spriteVariant)
	{
		this.spriteVariant = spriteVariant;
	}

	private static class EmptyTile extends Tile
	{
		private static final long serialVersionUID = 8715601332795760931L;

		public EmptyTile()
		{
			super(TileType.FLAT, ElementContainer.EMPTY);
		}

		@Override
		public Image getSprite() throws SlickException
		{
			return PixelisedImage.getEmpty();
		}

		@Override
		public TileBiome getBiome()
		{
			return TileBiome.GRASS;
		}
	}
}
