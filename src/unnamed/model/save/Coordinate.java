package unnamed.model.save;

import java.io.Serializable;

import unnamed.model.element.map.tile.Tile;

public class Coordinate implements Serializable
{
	private static final long serialVersionUID = 6630198734520748509L;
	
	public int x;
	public int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coordinate(Tile tile) {
		this.x = tile.getColumn();
		this.y = tile.getRow();
	}
}
