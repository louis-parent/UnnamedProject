package unnamed.model.save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

import unnamed.model.container.MapContainer;
import unnamed.model.element.map.TileMap;
import unnamed.model.element.map.tile.Tile;

public class SaveableMap implements Serializable
{
	private static final long serialVersionUID = -1886305566779109127L;
	
	private Map<Coordinate, TileProperties> saveable;
	
	private Coordinate fountain;
	
	private int columns;
	private int rows;
	
	public SaveableMap(TileMap tiles, Tile fountain)
	{
		this.saveable = new HashMap<Coordinate, TileProperties>();
		
		this.fountain = new Coordinate(fountain);
		
		this.columns = tiles.getColumns();
		this.rows = tiles.getRows();
		
		for(Tile tile : tiles)
		{
			this.saveable.put(new Coordinate(tile), new TileProperties(tile));
		}
	}

	public MapContainer getContainer() throws SlickException
	{
		MapContainer container = new MapContainer();
		
		TileMap map = new TileMap(this.columns, this.rows);
		
		map.addAll(getListOfTiles(container));
		
		container.addTileMap(map);
		
		container.setFountain(map.get(map.getListPosition(this.fountain.x, this.fountain.y)));
		
		return container;
	}

	private List<Tile> getListOfTiles(MapContainer container) throws SlickException
	{
		List<Tile> tiles = new ArrayList<Tile>();
		
		for(Map.Entry<Coordinate, TileProperties> entry : this.saveable.entrySet())
		{
			tiles.add(entry.getValue().getTile(entry.getKey(), container));
		}
		
		return tiles;
	}
}
