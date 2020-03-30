package unnamed.model.element.map.tile.behaviour;

import org.newdawn.slick.SlickException;

import unnamed.model.element.map.tile.Tile;

public class DefaultBehaviour implements TileBehaviour
{
	protected Tile tile;
	
	public DefaultBehaviour(Tile tile)
	{
		this.tile = tile;
	}

	@Override
	public void tickUpdate() throws SlickException
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
	public void clickEvent()
	{

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
	public void cleanUp()
	{

	}

	@Override
	public void informNeighbourChange() throws SlickException
	{

	}
}
