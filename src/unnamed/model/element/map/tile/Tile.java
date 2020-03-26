package unnamed.model.element.map.tile;

import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;
import unnamed.model.element.SelectableElement;
import unnamed.model.element.map.tile.behaviour.TileBehaviour;
import unnamed.model.element.menu.FormattedString;

public class Tile extends Element implements SelectableElement
{
	private static final long serialVersionUID = -867365679274168458L;

	private static final Tile EMPTY = new Tile();

	public static final int Y_ENTITY_OFFSET = 29;

	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 29;

	public static final int FLOATING_OFFSET = 8;
	private static final int Z_SPACE_BETWEEN_TILES = 10;

	private static TileImageRegistry registry;

	private int column;
	private int row;

	private TileBiome biome;
	private TileBehaviour behaviour;

	private TileType type;

	private int spriteVariant;

	private boolean isSelected;

	public static void init() throws SlickException
	{
		Tile.registry = new TileImageRegistry();
	}

	private Tile()
	{
		this(ElementContainer.getEmpty());
	}

	public Tile(ElementContainer container)
	{
		this(0, 0, container);
	}

	public Tile(int column, int row, ElementContainer container)
	{
		super(Tile.getXValueFrom(column, row), Tile.getYValueFrom(row), Tile.getZValueFrom(row), container);

		this.column = column;
		this.row = row;

		this.setBiome(TileBiome.GRASS);

		this.type = TileType.FLAT;
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

	public TileType getType()
	{
		return this.type;
	}

	public void setType(TileType type)
	{
		this.type = type;
		this.setSpriteVariant(type.getRandomVariant());
	}

	public TileBiome getBiome()
	{
		return this.biome;
	}

	public void setBiome(TileBiome biome)
	{
		this.biome = biome;
		
		try
		{
			this.behaviour = this.biome.getBehaviour().getConstructor(Tile.class).newInstance(this);
		}
		catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void tickUpdate()
	{
		this.behaviour.tickUpdate();
	}

	@Override
	public void pressed()
	{
		this.behaviour.pressed();
	}

	@Override
	public void mouseLeft()
	{
		this.behaviour.mouseLeft();
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

	@Override
	public void clickEvent()
	{
		this.behaviour.clickEvent();
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
		this.behaviour.selectEvent();
	}

	@Override
	public void deselectEvent()
	{
		this.setY(this.getY() + Tile.FLOATING_OFFSET);
		this.behaviour.deselectEvent();
	}

	@Override
	public void setSelected(boolean toSelect)
	{
		this.isSelected = toSelect;
	}

	@Override
	public Image getSprite() throws SlickException
	{
		return Tile.registry.get(Tile.registry.getImageNameFor(this.biome, this.type, this.spriteVariant));
	}

	public static Tile getEmpty()
	{
		return Tile.EMPTY;
	}
}
