package unnamed.model.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.Element;

public abstract class ElementContainer implements Serializable
{
	private static final long serialVersionUID = 3624575560912630840L;

	public static final ElementContainer EMPTY = new EmptyContainer();

	private Map<Integer, List<Element>> elements;
	private int maxDepth;

	public ElementContainer()
	{
		this.elements = new HashMap<Integer, List<Element>>();
		this.maxDepth = 0;
	}
	
	public void addAllElements(List<Element> elements)
	{
		for(Element element : elements)
		{
			this.addElement(element);
		}
	}

	public void addElement(Element element)
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

	public void replaceElement(Element oldElement, Element newElement)
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

	public void changeZOfElement(Element newElement, int oldZ)
	{
		this.elements.get(oldZ).remove(newElement);

		this.addElement(newElement);
	}

	protected Element getTopElementAt(float x, float y)
	{
		List<Element> elements = this.getElementsToDraw();

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
			return Element.EMPTY;
		}
	}

	protected void verticalAlign(int padding, Element... elements)
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
		this.getTopElementAt(x, y).click();
	}

	public void pressedAt(int x, int y)
	{
		this.getTopElementAt(x, y).pressed();
	}

	public abstract void init() throws SlickException;
	public abstract void tickUpdate();
	public abstract void mouseWheelMoved(int change);
	public abstract void keyReleased(int key, char c);
	public abstract void keyPressed(int key, char c);
	public abstract void mouseDragged(int oldx, int oldy, int newx, int newy);
	public abstract void wheelPressedAt(int x, int y);
	public abstract void wheelReleasedAt(int x, int y);
	public abstract int getHeight();
	public abstract int getWidth();

	private static class EmptyContainer extends ElementContainer
	{
		private static final long serialVersionUID = 4766154415901796682L;

		@Override
		public void wheelReleasedAt(int x, int y)
		{
		}

		@Override
		public void wheelPressedAt(int x, int y)
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
		public void mouseDragged(int oldx, int oldy, int newx, int newy)
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
