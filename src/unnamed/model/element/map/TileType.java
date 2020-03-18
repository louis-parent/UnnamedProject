package unnamed.model.element.map;

import java.util.Random;

import unnamed.controller.GameController;

public enum TileType
{
	FLAT(6), HILL(3), MOUNTAIN(3);

	private final int variant;

	private TileType(int variant)
	{
		this.variant = variant;
	}

	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}

	public int getVariantAmount()
	{
		return this.variant;
	}

	public int getRandomVariant()
	{
		return GameController.getInstance().getRandom().nextInt(this.variant) + 1;
	}

	public static TileType getRandomType()
	{
		Random random = GameController.getInstance().getRandom();

		TileType[] values = TileType.values();
		int randomTypeIndex = random.nextInt(values.length);

		return values[randomTypeIndex];
	}
}
