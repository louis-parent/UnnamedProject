package unnamed.model.element.map.tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;
import unnamed.model.element.SelectableElement;
import unnamed.model.element.entity.Entity;
import unnamed.model.element.menu.FormattedString;

public abstract class Tile extends Element implements SelectableElement
{
	private static final int Y_ENTITY_OFFSET = 29;

	private static final long serialVersionUID = -867365679274168458L;

	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 29;

	public static final int FLOATING_OFFSET = 8;
	private static final int Z_SPACE_BETWEEN_TILES = 10;

	public static final Tile EMPTY = new EmptyTile();

	private int column;
	private int row;

	private TileType type;

	private int spriteVariant;

	private Entity entity;

	private boolean isSelected;

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

		this.entity = Entity.EMPTY;

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
	public void pressed()
	{
	}

	@Override
	public void mouseLeft()
	{
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

	public void setEntity(Entity entity) throws SlickException
	{
		this.entity = entity;

		entity.setX((this.getX() + (this.getWidth() / 2f)) - (entity.getWidth() / 2f));
		entity.setY((this.getY() + Tile.Y_ENTITY_OFFSET) - entity.getHeight());
		entity.setZ(this.getZ());
	}

	@Override
	public void clickEvent()
	{

	}

	@Override
	public boolean isSelected()
	{
		return this.isSelected;
	}

	@Override
	public void selectEvent()
	{
		this.setY(this.getY() - Tile.FLOATING_OFFSET);
		this.entity.setY(this.entity.getY() - Tile.FLOATING_OFFSET);
	}

	@Override
	public void deselectEvent()
	{
		this.setY(this.getY() + Tile.FLOATING_OFFSET);
		this.entity.setY(this.entity.getY() + Tile.FLOATING_OFFSET);
	}

	@Override
	public void setSelected(boolean toSelect)
	{
		this.isSelected = toSelect;
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

		@Override
		public boolean isEmpty()
		{
			return true;
		}

		@Override
		public boolean isSelected()
		{
			return false;
		}

		@Override
		public void selectEvent()
		{

		}

		@Override
		public void deselectEvent()
		{

		}

		@Override
		public void setSelected(boolean toSelect)
		{

		}

		@Override
		public void clickEvent()
		{

		}
	}
}
