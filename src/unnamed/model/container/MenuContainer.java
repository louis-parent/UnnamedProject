package unnamed.model.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.Element;
import unnamed.model.element.menu.MenuElement;
import unnamed.model.element.menu.button.MenuFactory;
import unnamed.model.element.menu.field.TextField;

public abstract class MenuContainer extends ElementContainer
{
	private static final long serialVersionUID = 8508648978109147145L;
	private static final int PADDING = 50;
	
	private List<TextField> fields;
	private MenuElement selectedField;
	
	public MenuContainer()
	{
		super();
		this.fields = new ArrayList<TextField>();
		this.selectedField = MenuElement.EMPTY;
	}

	public MenuElement[] buildMenu(String... menuElementTypes) throws SlickException
	{
		MenuElement[] elements = new MenuElement[menuElementTypes.length];

		for(int i = 0; i < menuElementTypes.length; i++)
		{
			elements[i] = MenuFactory.create(menuElementTypes[i], this);
		}

		this.addAllElements(Arrays.asList(elements));
		this.verticalAlign(MenuContainer.PADDING, elements);
		
		return elements;
	}
	
	@Override
	public void addElement(Element element)
	{
		super.addElement(element);
		
		if(element instanceof TextField)
		{
			TextField field = (TextField) element;
			this.fields.add(field);
			
			if(MenuElement.EMPTY.equals(this.selectedField))
			{
				this.selectedField = field;
			}
		}
	}
	
	@Override
	public void mouseWheelMoved(int change)
	{
	}

	@Override
	public void keyReleased(int key, char c)
	{
	}

	@Override
	public void keyPressed(int key, char c)
	{
		this.selectedField.keyPressed(key, c);
	}
	
	@Override
	public void clickAt(float x, float y) throws SlickException
	{
		super.clickAt(x, y);
		
		for(MenuElement element : this.fields)
		{
			if(element.isInside(x, y))
			{
				this.selectedField = element;
			}
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) throws SlickException
	{
		Element oldElement = this.getTopElementAt(oldx, oldy);
		Element newElement = this.getTopElementAt(newx, newy);

		if(oldElement != newElement)
		{
			oldElement.mouseLeft();
		}
	}
	
	@Override
	public void wheelPressedAt(int x, int y)
	{
	}

	@Override
	public void wheelReleasedAt(int x, int y)
	{
	}
	
	@Override
	public int getHeight()
	{
		return GameController.GAME_HEIGHT;
	}

	@Override
	public int getWidth()
	{
		return GameController.GAME_WIDTH;
	}
}
