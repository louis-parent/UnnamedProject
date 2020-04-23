package unnamed.model.save;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import unnamed.model.container.MapContainer;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;
import unnamed.model.element.map.tile.TileDirection;
import unnamed.model.element.map.tile.TileType;

public class TileProperties implements Serializable
{
	private static final long serialVersionUID = 6679348274571471046L;
	
	private Map<TileDirection, Coordinate> adjacents;
	
	private TileBiome biome;
	
	private TileType type;
	
	private BehaviourProperties behaviour;
	
	private int variant;
	
	public TileProperties(Tile tile)
	{
		buildAdjacents(tile);
		
		this.biome = tile.getBiome();
		
		this.type = tile.getType();
		
		this.behaviour = new BehaviourProperties(tile.getBehaviour());
		
		this.variant = tile.getSpriteVariant();
	}

	private void buildAdjacents(Tile tile)
	{
		this.adjacents = new HashMap<TileDirection, Coordinate>();

		for (TileDirection dir : TileDirection.values()) {
			Tile adj = tile.getAdjacentFrom(dir);
			
			if (adj != null && !adj.isEmpty()) {
				this.adjacents.put(dir, new Coordinate(adj));
			}
		}
	}

	public Tile getTile(Coordinate coordinate, MapContainer container) throws SlickException
	{
		Tile tile = new Tile(coordinate.x, coordinate.y, container);
		
		tile.setBiome(this.biome);
		tile.setType(this.type);
		
		tile.setBehaviour(this.behaviour.getBehaviour(tile));
		
		tile.setSpriteVariant(this.variant);
		
		return tile;
	}
}
