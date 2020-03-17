package unnamed.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;

public class ElementContainer
{

	private static final int NUMBER_OF_ROWS = 75;
	private static final int NUMBER_OF_COLUMNS = 75;
	private Map<Integer, List<Element>> elements;
	private int maxDepth;

	public ElementContainer()
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

		for(int i = 0; i < NUMBER_OF_COLUMNS; i++)
		{
			for(int j = 0; j < NUMBER_OF_ROWS; j++)
			{
				GameController.getInstance().getModel().addElement(new ConcreteTile(i, j));
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
		this.elements.get(oldZ).remove(newElement);

		this.addElement(newElement);
	}

	public void clickAt(float x, float y)
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

	public int getMapHeight()
	{
		return NUMBER_OF_ROWS * Tile.TILE_HEIGHT - (Tile.FLOATING_OFFSET * NUMBER_OF_ROWS) + Tile.TILE_HEIGHT + Tile.FLOATING_OFFSET;
	}

	public int getMapWidth()
	{
		return (ElementContainer.NUMBER_OF_COLUMNS * Tile.TILE_WIDTH) + (Tile.TILE_WIDTH / 2);
	}
}
