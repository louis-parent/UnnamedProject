package unnamed.model.element.menu;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;

public abstract class MenuElement extends Element
{
	private static final long serialVersionUID = 183177966778498672L;
	
	private static final MenuElement EMPTY = new EmptyMenuElement();

	public MenuElement(float x, float y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public MenuElement(ElementContainer container)
	{
		super(container);
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
	public FormattedString getFormattedText()
	{
		return FormattedString.getEmpty();
	}

	public static MenuElement getEmpty()
	{
		return MenuElement.EMPTY;
	}
	
	private static class EmptyMenuElement extends MenuElement
	{
		private static final long serialVersionUID = -5224604040164389541L;

		public EmptyMenuElement()
		{
			super(ElementContainer.getEmpty());
		}

		@Override
		public Image getSprite() throws SlickException
		{
			return PixelisedImage.getEmpty();
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

		@Override
		public boolean isEmpty()
		{
			return true;
		}

		@Override
		public void clickEvent()
		{

		}
	}
}
