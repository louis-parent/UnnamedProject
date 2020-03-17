package unnamed.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

public class ElementContainer
{
	private static ElementContainer instance;

	private Map<Integer, List<Element>> elements;
	private int maxDepth;

	public static ElementContainer getInstance()
	{
		if(ElementContainer.instance == null)
		{
			ElementContainer.instance = new ElementContainer();
		}

		return ElementContainer.instance;
	}

	private ElementContainer()
	{
		this.elements = new HashMap<Integer, List<Element>>();
		this.maxDepth = 0;
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

	public void init() throws SlickException
	{
		ConcreteTile.init();

		for(int i = 0; i < 15; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				ElementContainer.getInstance().addElement(new ConcreteTile(i, j));
			}
		}
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
		int newZ = newElement.getZ();

		this.elements.get(oldZ).remove(newElement);

		this.addElement(newElement);
	}

	public void clickAt(int x, int y)
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
		
		((Tile) elements.get(elements.size() - 1)).click();

	}
}
