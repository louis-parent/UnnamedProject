package unnamed.model.element.menu;

import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;

public abstract class MenuElement extends Element
{	
	private static final long serialVersionUID = 183177966778498672L;
	public static final MenuElement EMPTY = new EmptyMenuElement();
	
	public MenuElement(float x, float y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public MenuElement(ElementContainer container)
	{
		super(container);
	}
	
	@Override
	public void click() throws SlickException
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
	
	public void keyPressed(int key, char c)
	{
	}
	
	@Override
	public FormattedString getFormattedText()
	{
		return FormattedString.EMPTY;
	}
	
	private static class EmptyMenuElement extends MenuElement
	{
		private static final long serialVersionUID = -5224604040164389541L;

		public EmptyMenuElement()
		{
			super(ElementContainer.EMPTY);
		}

		@Override
		public PixelisedImage getSprite() throws SlickException
		{
			return PixelisedImage.getEmpty();
		}

		@Override
		public void tickUpdate()
		{			
		}

		@Override
		public void click() throws SlickException
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
		
	}
}
