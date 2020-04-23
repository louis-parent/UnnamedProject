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

		Tile ownTile = this.getTile();
		
		ownTile.getContainer().addElementToTickUpdate(ownTile);
	}

	@Override
	public void tickUpdate() throws SlickException
	{
		Tile ownTile = this.getTile();
		
		if(!ownTile.isOccupied())
		{
			if(this.spawnCooldown == 0)
			{
				ownTile.getContainer().addElement(new Entity(ownTile, ownTile.getContainer()));
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
		List<Tile> adjacents = this.getTile().getAdjacents();
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

	public int getCooldown()
	{
		return this.spawnCooldown;
	}

	public void setCooldwn(int cooldown)
	{
		this.spawnCooldown = cooldown;
	}
}
