package unnamed.model.element.map.tile.behaviour;

import java.util.List;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;

public class CorruptBehaviour implements TileBehaviour
{
	private static final double CORRUPTION_SPREAD_PERCENTAGE = 1.0 / 1000.0;

	private static final int RAISE_OFFSET = 13;
	private static final int FALLING_SPEED = 1;

	private Tile tile;

	private float targetY;

	private boolean isActivated;

	public CorruptBehaviour(Tile tile)
	{
		this.tile = tile;

		if(!tile.isSelected())
		{
			this.targetY = CorruptBehaviour.RAISE_OFFSET;
			this.tile.setY(this.tile.getY() - CorruptBehaviour.RAISE_OFFSET);
		}

		this.tile.getContainer().addElementToTickUpdate(this.tile);

		this.isActivated = true;
	}

	@Override
	public void tickUpdate() throws SlickException
	{
		if((this.targetY > 0) && !this.tile.isSelected())
		{
			this.tile.setY(this.tile.getY() + CorruptBehaviour.FALLING_SPEED);
			this.targetY -= CorruptBehaviour.FALLING_SPEED;
		}

		this.spreadCorruption();
	}

	private void spreadCorruption() throws SlickException
	{
		List<Tile> adjacents = this.tile.getAdjacents();
		adjacents.removeIf(tile -> tile.getBiome() != TileBiome.GRASS);

		for(Tile toSpread : adjacents)
		{
			if(GameController.getInstance().getRandom().nextDouble() < CorruptBehaviour.CORRUPTION_SPREAD_PERCENTAGE)
			{
				toSpread.setBiome(TileBiome.CORRUPT);
			}
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
		this.resetFalling();
	}

	private void resetFalling()
	{
		this.tile.setY(this.tile.getY() + this.targetY);
		this.targetY = 0;
	}

	@Override
	public void deselectEvent()
	{

	}

	@Override
	public void cleanUp()
	{
		this.resetFalling();
	}

	@Override
	public void informNeighbourChange()
	{
		boolean isAdjacentToGrass = this.tile.getAdjacents().stream().anyMatch(tile -> tile.getBiome() == TileBiome.GRASS && !tile.isEmpty());

		if(this.isActivated && !isAdjacentToGrass && this.targetY == 0)
		{
			this.tile.getContainer().removeElementToTickUpdate(this.tile);
			this.isActivated = false;
		}
		else if(!this.isActivated && isAdjacentToGrass)
		{
			this.tile.getContainer().addElementToTickUpdate(this.tile);
			this.isActivated = true;
		}
	}
}
