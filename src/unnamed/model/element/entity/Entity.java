package unnamed.model.element.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;
import unnamed.model.element.menu.FormattedString;

public class Entity extends Element
{
	private static final long serialVersionUID = 8553384493664910671L;

	public static final Entity EMPTY = new EmptyEntity();;

	private static Image idle;
	private static Image down_left;
	private static Image down_right;
	private static Image up_left;
	private static Image up_right;
	private static Image left;
	private static Image right;

	public static void init() throws SlickException
	{
		Entity.idle = new PixelisedImage("assets/entity/idle.png");
		Entity.down_left = new PixelisedImage("assets/entity/down_left.png");
		Entity.down_right = new PixelisedImage("assets/entity/down_right.png");
		Entity.up_left = new PixelisedImage("assets/entity/up_left.png");
		Entity.up_right = new PixelisedImage("assets/entity/up_right.png");
		Entity.left = new PixelisedImage("assets/entity/left.png");
		Entity.right = new PixelisedImage("assets/entity/right.png");
	}

	public Entity(ElementContainer container)
	{
		super(container);
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

	private static class EmptyEntity extends Entity
	{
		private static final long serialVersionUID = -3517399285369674213L;

		public EmptyEntity()
		{
			super(ElementContainer.EMPTY);
		}
		
		@Override
		public boolean isEmpty()
		{
			return true;
		}
	}

	@Override
	public void clickEvent() throws SlickException
	{
	}
}
