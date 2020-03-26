package unnamed.model.element.map.tile.behaviour;

import java.util.List;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;

public class FountainBehaviour implements TileBehaviour
{

	private Tile tile;

	public FountainBehaviour(Tile tile)
	{
		this.tile = tile;
	}

	@Override
	public void tickUpdate() throws SlickException
	{
		this.checkDefeat();
	}

	private void checkDefeat() throws SlickException
	{
		List<Tile> adjacents = this.tile.getAdjacents();
		adjacents.removeIf(tile -> tile.getBiome() != TileBiome.CORRUPT);
		boolean isDefeat = !adjacents.isEmpty();

		if(isDefeat)
		{
			GameController.getInstance().loseGame();
		}
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
}
