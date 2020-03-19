package unnamed.model.element.map;

import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;

public abstract class Tile extends Element
{
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 29;

	public static final int FLOATING_OFFSET = 8;
	private static final int Z_SPACE_BETWEEN_TILES = 10;

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
		super(getXValueFrom(column, row), getYValueFrom(row), getZValueFrom(row), container);
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

	private void select()
	{
		this.isSelected = true;
		this.setY(this.getY() - FLOATING_OFFSET);

		if(Tile.lastSelected != null)
		{
			Tile.lastSelected.deselect();
		}

		Tile.lastSelected = this;
	}

	private void deselect()
	{
		this.isSelected = false;
		this.setY(this.getY() + FLOATING_OFFSET);

		if(Tile.lastSelected == this)
		{
			Tile.lastSelected = null;
		}
	}

	private static float getXValueFrom(int column, int row)
	{
		if(row % 2 == 0)
		{
			return column * TILE_WIDTH;
		}
		else
		{
			return (column * TILE_WIDTH) + (TILE_WIDTH / 2);
		}
	}

	private static float getYValueFrom(int row)
	{
		if(row != 0)
		{
			return row * TILE_HEIGHT - (FLOATING_OFFSET * row) + FLOATING_OFFSET;
		}
		else
		{
			return FLOATING_OFFSET;
		}
	}

	private static int getZValueFrom(int row)
	{
		return row * Z_SPACE_BETWEEN_TILES;
	}

	protected int getSpriteVariant()
	{
		return this.spriteVariant;
	}

	protected void setSpriteVariant(int spriteVariant)
	{
		this.spriteVariant = spriteVariant;
	}
}
