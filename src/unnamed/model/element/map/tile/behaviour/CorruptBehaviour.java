package unnamed.model.element.map.tile.behaviour;

import java.util.List;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;

public class CorruptBehaviour extends DefaultBehaviour
{
	private static final double CORRUPTION_SPREAD_PERCENTAGE = 1.0 / 1000.0;

	private static final int RAISE_OFFSET = 13;
	private static final int FALLING_SPEED = 1;

	private float targetY;

	private boolean isActivated;

	public CorruptBehaviour(Tile tile)
	{
		super(tile);
		
		Tile ownTile = this.getTile();
		
		if(!tile.isSelected())
		{
			this.targetY = CorruptBehaviour.RAISE_OFFSET;
			ownTile.setY(ownTile.getY() - CorruptBehaviour.RAISE_OFFSET);
		}

		ownTile.getContainer().addElementToTickUpdate(ownTile);

		this.isActivated = true;
	}

	@Override
	public void tickUpdate() throws SlickException
	{
		Tile ownTile = this.getTile();
		
		if((this.targetY > 0) && !ownTile.isSelected())
		{
			ownTile.setY(ownTile.getY() + CorruptBehaviour.FALLING_SPEED);
			this.targetY -= CorruptBehaviour.FALLING_SPEED;
		}

		this.spreadCorruption();
	}

	private void spreadCorruption() throws SlickException
	{
		List<Tile> adjacents = this.getTile().getAdjacents();
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
	public void selectEvent()
	{
		this.resetFalling();
	}

	public void resetFalling()
	{
		Tile ownTile = this.getTile();
		ownTile.setY(ownTile.getY() + this.targetY);
		this.targetY = 0;
	}

	@Override
	public void cleanUp()
	{
		this.resetFalling();
	}

	@Override
	public void informNeighbourChange()
	{
		Tile ownTile = this.getTile();
		
		boolean isAdjacentToGrass = ownTile.getAdjacents().stream().anyMatch(tile -> (tile.getBiome() == TileBiome.GRASS) && !tile.isEmpty());

		if(this.isActivated && !isAdjacentToGrass && (this.targetY == 0))
		{
			ownTile.getContainer().removeElementToTickUpdate(ownTile);
			this.isActivated = false;
		}
		else if(!this.isActivated && isAdjacentToGrass)
		{
			ownTile.getContainer().addElementToTickUpdate(ownTile);
			this.isActivated = true;
		}
	}
}
