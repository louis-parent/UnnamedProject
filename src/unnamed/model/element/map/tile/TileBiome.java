package unnamed.model.element.map.tile;

public enum TileBiome
{
	GRASS,
	SHALLOW_WATER,
	DEEP_WATER,
	DESERT,
	CORRUPT,
	FOUNTAIN;
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}
}
