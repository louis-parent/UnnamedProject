package unnamed.model.element.map.tile.behaviour;

import unnamed.model.element.map.tile.Tile;

public class CorruptBehaviour implements TileBehaviour
{
	private static final int RAISE_OFFSET = 13;
	private static final int FALLING_SPEED = 1;

	private Tile tile;

	private float targetY;

	public CorruptBehaviour(Tile tile)
	{
		this.tile = tile;

		if (!tile.isSelected()) {
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
		this.tile.setY(this.tile.getY() + this.targetY);
		this.targetY = 0;
	}

	@Override
	public void deselectEvent()
	{

	}
}
