package unnamed.model.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.Element;
import unnamed.model.element.SelectableElement;
import unnamed.util.Nullable;

public abstract class ElementContainer implements Serializable
{
	private static final long serialVersionUID = 3624575560912630840L;

	public static final ElementContainer EMPTY = new EmptyContainer();

	private Map<Integer, List<Element>> elements;
	private int maxDepth;

	private SelectableElement lastSelected;

	public ElementContainer()
	{
		this.elements = new HashMap<Integer, List<Element>>();
		this.maxDepth = 0;

		this.lastSelected = SelectableElement.EMPTY;
	}

	public void addAllElements(List<Element> elements) throws SlickException
	{
		for(Element element : elements)
		{
			this.addElement(element);
		}
	}

	public void addElement(Element element) throws SlickException
	{
		int z = element.getZ();

		if(!this.elements.containsKey(z))
		{
			this.elements.put(z, new ArrayList<Element>());

			if(z > this.maxDepth)
			{
				this.maxDepth = z;
			}
		}

		this.elements.get(z).add(element);
	}

	public void removeElement(Element element)
	{
		this.elements.get(element.getZ()).remove(element);
	}

	public void replaceElement(Element oldElement, Element newElement) throws SlickException
	{
		this.removeElement(oldElement);
		this.addElement(newElement);
	}

	public List<Element> getElementsToDraw()
	{
		List<Element> finalList = new ArrayList<Element>();

		for(int i = 0; i <= this.maxDepth; i++)
		{
			if(this.elements.containsKey(i))
			{
				finalList.addAll(this.elements.get(i));
			}
		}

		return finalList;
	}

	public void changeZOfElement(Element newElement, int oldZ) throws SlickException
	{
		this.elements.get(oldZ).remove(newElement);

		this.addElement(newElement);
	}

	protected Element getTopElementAt(float x, float y) throws SlickException
	{
		Element topElement = this.getTopElementAtIn(x, y, this.getElementsToDraw());
		return topElement == null ? Element.EMPTY : topElement;
	}

	@Nullable
	protected <T extends Element> T getTopElementAtIn(float x, float y, List<T> elements) throws SlickException
	{
		for(int i = 0; i < elements.size(); i++)
		{
			Element element = elements.get(i);

			if(!element.isInside(x, y))
			{
				elements.remove(element);
				i--;
			}
		}

		if(elements.size() > 0)
		{
			return elements.get(elements.size() - 1);
		}
		else
		{
			return null;
		}
	}

	protected void verticalAlign(int padding, Element... elements) throws SlickException
	{
		int midX = GameController.GAME_WIDTH / 2;
		int midY = GameController.GAME_HEIGHT / 2;

		int summedHeight = 0;

		for(Element element : elements)
		{
			summedHeight += element.getHeight();
			summedHeight += padding;
		}

		summedHeight -= padding;

		int sumOfDone = 0;

		for(Element element : elements)
		{
			element.centerElementAt(midX, (midY - (summedHeight / 2)) + sumOfDone);
			sumOfDone += element.getHeight() + padding;
		}
	}

	public void clickAt(float x, float y) throws SlickException
	{
		Element clicked = this.getTopElementAt(x, y);

		if(clicked instanceof SelectableElement)
		{
			this.setSelected((SelectableElement) clicked);
		}
		else
		{
			clicked.click();
		}
	}

	public SelectableElement getSelected()
	{
		return this.lastSelected;
	}

	public void setSelected(SelectableElement selected) throws SlickException
	{
		((Element) selected).click();

		if(!selected.equals(this.lastSelected))
		{
			((Element) this.lastSelected).click();
			this.lastSelected = selected;
		}
		else
		{
			this.lastSelected = SelectableElement.EMPTY;
		}
	}

	public void pressedAt(float f, float g) throws SlickException
	{
		this.getTopElementAt(f, g).pressed();
	}

	public void enter() throws SlickException
	{

	}

	public void leave() throws SlickException
	{

	}

	public abstract void init() throws SlickException;

	public abstract void tickUpdate() throws SlickException;

	public abstract void mouseWheelMoved(int change);

	public abstract void keyReleased(int key, char c);

	public abstract void keyPressed(int key, char c) throws SlickException;

	public abstract void mouseDragged(float f, float g, float h, float i) throws SlickException;

	public abstract void wheelPressedAt(float f, float g);

	public abstract void wheelReleasedAt(float x, float y);

	public abstract void rightClickAt(float x, float y) throws SlickException;

	public abstract int getHeight();

	public abstract int getWidth();

	private static class EmptyContainer extends ElementContainer
	{
		private static final long serialVersionUID = 4766154415901796682L;

		@Override
		public void wheelReleasedAt(float x, float y)
		{
		}

		@Override
		public void wheelPressedAt(float x, float y)
		{
		}

		@Override
		public void tickUpdate()
		{
		}

		@Override
		public void mouseWheelMoved(int change)
		{
		}

		@Override
		public void mouseDragged(float oldx, float oldy, float newx, float newy)
		{
		}

		@Override
		public void keyReleased(int key, char c)
		{
		}

		@Override
		public void keyPressed(int key, char c)
		{
		}

		@Override
		public void rightClickAt(float x, float y)
		{

		}

		@Override
		public void init() throws SlickException
		{
		}

		@Override
		public int getHeight()
		{
			return 0;
		}

		@Override
		public int getWidth()
		{
			return 0;
		}
	}

}
