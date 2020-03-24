package unnamed.model.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.Element;
import unnamed.model.element.SelectableElement;
import unnamed.model.element.menu.MenuElement;
import unnamed.model.element.menu.button.MenuFactory;
import unnamed.model.element.menu.field.Field;

public abstract class MenuContainer extends ElementContainer
{
	private static final long serialVersionUID = 8508648978109147145L;
	private static final int PADDING = 50;

	private List<Field> fields;

	public MenuContainer()
	{
		super();
		this.fields = new ArrayList<Field>();
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
	public void addElement(Element element) throws SlickException
	{
		super.addElement(element);

		if(element instanceof Field)
		{
			Field field = (Field) element;
			this.fields.add(field);
		}
	}

	@Override
	public void enter() throws SlickException
	{
		if(!this.fields.isEmpty())
		{
			this.setSelected(this.fields.get(0));
		}
	}
	
	@Override
	public void leave() throws SlickException
	{
		this.setSelected(SelectableElement.EMPTY);
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
	public void keyPressed(int key, char c) throws SlickException
	{
		((Element) this.getSelected()).keyPressed(key, c);
	}

	@Override
	public void mouseDragged(float oldx, float oldy, float newx, float newy) throws SlickException
	{
		Element oldElement = this.getTopElementAt(oldx, oldy);
		Element newElement = this.getTopElementAt(newx, newy);

		if(oldElement != newElement)
		{
			oldElement.mouseLeft();
		}
	}

	@Override
	public void wheelPressedAt(float x, float y)
	{
	}

	@Override
	public void wheelReleasedAt(float x, float y)
	{
	}
	
	@Override
	public void rightClickAt(float x, float y)
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
