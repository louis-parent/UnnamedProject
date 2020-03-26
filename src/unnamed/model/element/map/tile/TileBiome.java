package unnamed.model.element.map.tile;

import unnamed.model.element.map.tile.behaviour.CorruptBehaviour;
import unnamed.model.element.map.tile.behaviour.DefaultBehaviour;
import unnamed.model.element.map.tile.behaviour.FountainBehaviour;
import unnamed.model.element.map.tile.behaviour.TileBehaviour;

public enum TileBiome
{
	GRASS(DefaultBehaviour.class),
	SHALLOW_WATER(DefaultBehaviour.class),
	DEEP_WATER(DefaultBehaviour.class),
	DESERT(DefaultBehaviour.class),
	CORRUPT(CorruptBehaviour.class),
	FOUNTAIN(FountainBehaviour.class);

	private final Class<? extends TileBehaviour> behaviourClass;

	private TileBiome(Class<? extends TileBehaviour> behaviourClass)
	{
		this.behaviourClass = behaviourClass;
	}

	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}

	public Class<? extends TileBehaviour> getBehaviour()
	{
		return this.behaviourClass;
	}
}
