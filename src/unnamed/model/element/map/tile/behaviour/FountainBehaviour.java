package unnamed.model.element.map.tile.behaviour;

import java.util.List;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.entity.Entity;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;

public class FountainBehaviour extends DefaultBehaviour
{
	private static final int TICK_PER_SPAWN = 40;

	private int spawnCooldown;

	public FountainBehaviour(Tile tile)
	{
		super(tile);
		
		this.spawnCooldown = FountainBehaviour.TICK_PER_SPAWN;

		this.tile.getContainer().addElementToTickUpdate(this.tile);
	}

	@Override
	public void tickUpdate() throws SlickException
	{
		if(!this.tile.isOccupied())
		{
			if(this.spawnCooldown == 0)
			{
				this.tile.getContainer().addElement(new Entity(this.tile, this.tile.getContainer()));
				this.spawnCooldown = FountainBehaviour.TICK_PER_SPAWN;
			}
			else
			{
				this.spawnCooldown--;
			}
		}
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
	public void informNeighbourChange() throws SlickException
	{
		this.checkDefeat();
	}
}
