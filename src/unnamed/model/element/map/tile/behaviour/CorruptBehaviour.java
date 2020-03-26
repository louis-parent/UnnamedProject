package unnamed.model.element.map.tile.behaviour;

import java.util.List;

import unnamed.controller.GameController;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;

public class CorruptBehaviour implements TileBehaviour
{
	private static final double CORRUPTION_SPREAD_PERCENTAGE = 1.0 / 50.0;

	private static final int RAISE_OFFSET = 13;
	private static final int FALLING_SPEED = 1;

	private Tile tile;

	private float targetY;

	public CorruptBehaviour(Tile tile)
	{
		this.tile = tile;

		if(!tile.isSelected())
		{
			this.targetY = CorruptBehaviour.RAISE_OFFSET;
			this.tile.setY(this.tile.getY() - CorruptBehaviour.RAISE_OFFSET);
		}
	}

	@Override
	public void tickUpdate()
	{
		if((this.targetY > 0) && !this.tile.isSelected())
		{
			this.tile.setY(this.tile.getY() + CorruptBehaviour.FALLING_SPEED);
			this.targetY -= CorruptBehaviour.FALLING_SPEED;
		}

		this.spreadCorruption();
	}

	private void spreadCorruption()
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
}
