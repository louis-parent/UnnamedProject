package unnamed.model.element.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;
import unnamed.model.element.SelectableElement;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.menu.FormattedString;

public class Entity extends Element implements SelectableElement
{
	private static final long serialVersionUID = 8553384493664910671L;

	private static Image idle;
//	private static Image down_left;
//	private static Image down_right;
//	private static Image up_left;
//	private static Image up_right;
//	private static Image left;
//	private static Image right;

	private Tile standingOn;
	private boolean isSelected;

	public static void init() throws SlickException
	{
		Entity.idle = new PixelisedImage("assets/entity/idle.png");
//		Entity.down_left = new PixelisedImage("assets/entity/down_left.png");
//		Entity.down_right = new PixelisedImage("assets/entity/down_right.png");
//		Entity.up_left = new PixelisedImage("assets/entity/up_left.png");
//		Entity.up_right = new PixelisedImage("assets/entity/up_right.png");
//		Entity.left = new PixelisedImage("assets/entity/left.png");
//		Entity.right = new PixelisedImage("assets/entity/right.png");
	}

	public Entity(ElementContainer container) throws SlickException
	{
		this(Tile.EMPTY, container);
	}

	public Entity(Tile tile, ElementContainer container) throws SlickException
	{
		super(container);
		this.standOn(tile);
		this.isSelected = false;
	}

	public void standOn(Tile tile)
	{
		this.standingOn = tile;
	}

	private void centerPosToTile() throws SlickException
	{
		this.setX((this.standingOn.getX() + (this.standingOn.getWidth() / 2f)) - (this.getWidth() / 2f));
		this.setY((this.standingOn.getY() + (Tile.Y_ENTITY_OFFSET)) - (this.getHeight()));
		this.setZ(this.standingOn.getZ() + 1);
	}

	@Override
	public Image getSprite() throws SlickException
	{
		return Entity.idle;
	}

	@Override
	public FormattedString getFormattedText()
	{
		return FormattedString.EMPTY;
	}

	@Override
	public void tickUpdate() throws SlickException
	{
		this.centerPosToTile();
	}

	@Override
	public void pressed()
	{
	}

	@Override
	public void mouseLeft()
	{
	}

	@Override
	public void clickEvent() throws SlickException
	{
	}

	public void moveTo(Tile newTile)
	{
		this.standOn(newTile);
	}
	
	@Override
	public boolean isSelected()
	{
		return this.isSelected;
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
		this.isSelected = toSelect;
	}

	public static Entity getEmptyEntity() throws SlickException
	{
		return new EmptyEntity();
	}

	private static class EmptyEntity extends Entity
	{
		private static final long serialVersionUID = -3517399285369674213L;

		public EmptyEntity() throws SlickException
		{
			super(ElementContainer.EMPTY);
		}

		@Override
		public boolean isEmpty()
		{
			return true;
		}
	}
}
