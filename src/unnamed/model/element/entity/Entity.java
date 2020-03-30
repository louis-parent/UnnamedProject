package unnamed.model.element.entity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;
import unnamed.model.element.SelectableElement;
import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.TileBiome;
import unnamed.model.element.map.tile.TileDirection;
import unnamed.model.element.map.tile.TileType;

public class Entity extends Element implements SelectableElement
{
	private static final long serialVersionUID = 8553384493664910671L;

	private static Entity EMPTY;

	private static Map<TileDirection, Image> directedSprites;

	private Tile standingOn;
	private boolean isSelected;

	private boolean isCharged;

	private Stack<Tile> path;
	private Tile destination;

	private boolean hasFinishedMoving;

	public static void init() throws SlickException
	{

		Entity.directedSprites = new HashMap<TileDirection, Image>();

		Entity.directedSprites.put(TileDirection.NE, new PixelisedImage("assets/entity/up_right.png"));
		Entity.directedSprites.put(TileDirection.E, new PixelisedImage("assets/entity/right.png"));
		Entity.directedSprites.put(TileDirection.SE, new PixelisedImage("assets/entity/down_right.png"));
		Entity.directedSprites.put(TileDirection.SW, new PixelisedImage("assets/entity/down_left.png"));
		Entity.directedSprites.put(TileDirection.W, new PixelisedImage("assets/entity/left.png"));
		Entity.directedSprites.put(TileDirection.NW, new PixelisedImage("assets/entity/up_left.png"));
		Entity.directedSprites.put(TileDirection.NONE, new PixelisedImage("assets/entity/idle.png"));
	}

	public Entity(ElementContainer container) throws SlickException
	{
		this(Tile.getEmptyTile(), container);
	}

	public Entity(Tile tile, ElementContainer container) throws SlickException
	{
		super(container);

		this.standingOn = Tile.getEmptyTile();
		this.standOn(tile);

		this.isSelected = false;

		this.isCharged = false;

		this.path = new Stack<Tile>();
		this.destination = Tile.getEmptyTile();

		this.hasFinishedMoving = false;

		this.getContainer().addElementToTickUpdate(this);
	}

	public void standOn(Tile tile)
	{
		this.standingOn.informDeparture(this);

		this.standingOn = tile;

		this.standingOn.informPresence(this);
	}

	private void adjustPosition() throws SlickException
	{
		float centerXStandingOn = (this.standingOn.getX() + (this.standingOn.getWidth() / 2f)) - (this.getWidth() / 2f);
		float centerYStandingOn = (this.standingOn.getY() + (Tile.Y_ENTITY_OFFSET)) - (this.getHeight());

		float centerXDestination = (this.destination.getX() + (this.destination.getWidth() / 2f)) - (this.getWidth() / 2f);
		float centerYDestination = (this.destination.getY() + (Tile.Y_ENTITY_OFFSET)) - (this.getHeight());

		if(this.destination.isEmpty())
		{
			this.setX(centerXStandingOn);
			this.setY(centerYStandingOn);
			this.setZ(this.standingOn.getZ() + 1);
		}
		else
		{
			this.setX((centerXStandingOn + centerXDestination) / 2);
			this.setY((centerYStandingOn + centerYDestination) / 2);
			this.setZ(Math.max(this.standingOn.getZ(), this.destination.getZ()) + 1);
		}

	}

	@Override
	public Image getSprite() throws SlickException
	{
		TileDirection direction = TileDirection.NONE;

		if(!this.destination.isEmpty())
		{
			direction = this.standingOn.directionOf(this.destination);
		}
		else if(!this.path.isEmpty())
		{
			direction = this.standingOn.directionOf(this.path.peek());
		}

		return Entity.directedSprites.get(direction);
	}

	@Override
	public void tickUpdate() throws SlickException
	{
		if((this.standingOn.getBiome() == TileBiome.CORRUPT) && this.isCharged)
		{
			this.isCharged = false;
			this.standingOn.setBiome(TileBiome.GRASS);
		}
		else if(this.standingOn.getBiome() == TileBiome.FOUNTAIN)
		{
			this.isCharged = true;
		}

		this.doMovement();

		this.adjustPosition();
	}

	private void doMovement() throws SlickException
	{
		if(!this.destination.isEmpty())
		{
			if(this.hasFinishedMoving)
			{
				this.standOn(this.destination);
				this.hasFinishedMoving = false;

				this.destination = Tile.getEmptyTile();
			}
			else
			{
				this.hasFinishedMoving = true;
			}

		}
		else if(!this.path.isEmpty())
		{
			this.destination = this.path.pop();
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
	public void clickEvent() throws SlickException
	{
	}

	public void moveTo(Tile newTile)
	{
		if(!this.path.isEmpty())
		{
			this.path.firstElement().unbook();
			this.path = new Stack<Tile>();
		}

		if(!newTile.isOccupied())
		{
			if(this.AStar(newTile))
			{
				newTile.book();
			}
		}
	}

	@Override
	public boolean isSelected()
	{
		return this.isSelected;
	}

	@Override
	public void selectEvent()
	{

	}

	@Override
	public void deselectEvent()
	{

	}

	@Override
	public void setSelected(boolean toSelect)
	{
		this.isSelected = toSelect;
	}

	private boolean AStar(Tile target)
	{
		Map<Tile, Integer> distances = new WeightedTileMap();
		Map<Tile, Integer> heuristics = new WeightedTileMap();

		Map<Tile, Tile> cameFrom = new HashMap<Tile, Tile>();
		Queue<Tile> openQueue = new PriorityQueue<Tile>(11, new HeuristicComparator(heuristics));

		openQueue.add(this.standingOn);
		distances.put(this.standingOn, 0);
		heuristics.put(this.standingOn, this.euclidianDistance(this.standingOn, target));

		while(!openQueue.isEmpty())
		{
			Tile current = openQueue.poll();

			if(current.equals(target))
			{
				this.buildPath(target, cameFrom);
				return true;
			}

			List<Tile> adjacents = current.getAdjacents();
			adjacents.removeIf(tile -> ((tile.getBiome() == TileBiome.DEEP_WATER) || (tile.getType() == TileType.MOUNTAIN)));

			for(Tile adjacent : adjacents)
			{
				int newDistance = distances.get(current) + 1;

				if(newDistance < distances.get(adjacent))
				{
					cameFrom.put(adjacent, current);
					distances.put(adjacent, newDistance);
					heuristics.put(adjacent, newDistance + this.euclidianDistance(adjacent, target));

					if(!openQueue.contains(adjacent))
					{
						openQueue.add(adjacent);
					}
				}
			}
		}

		return false;
	}

	private void buildPath(Tile target, Map<Tile, Tile> cameFrom)
	{
		this.path.add(target);

		while(!target.equals(this.standingOn))
		{
			target = cameFrom.get(target);
			this.path.add(target);
		}

		this.path.pop();
	}

	private int euclidianDistance(Tile first, Tile second)
	{
		return (int) Math.sqrt(Math.pow(second.getColumn() - first.getColumn(), 2) + Math.pow(second.getRow() - first.getRow(), 2));
	}

	public static Entity getEmptyEntity() throws SlickException
	{
		if(Entity.EMPTY == null)
		{
			Entity.EMPTY = new EmptyEntity();
		}

		return Entity.EMPTY;
	}

	private static class EmptyEntity extends Entity
	{
		private static final long serialVersionUID = -3517399285369674213L;

		public EmptyEntity() throws SlickException
		{
			super(ElementContainer.getEmptyElement());
		}

		@Override
		public boolean isEmpty()
		{
			return true;
		}
	}

	private static class WeightedTileMap extends HashMap<Tile, Integer>
	{
		private static final long serialVersionUID = 7271971041006998574L;

		@Override
		public Integer get(Object key)
		{
			if((key instanceof Tile) && !this.containsKey(key))
			{
				this.put((Tile) key, Integer.MAX_VALUE);
			}

			return super.get(key);
		}
	}

	private static class HeuristicComparator implements Comparator<Tile>
	{
		private Map<Tile, Integer> heuristics;

		public HeuristicComparator(Map<Tile, Integer> heuristics)
		{
			this.heuristics = heuristics;
		}

		@Override
		public int compare(Tile left, Tile right)
		{
			Integer leftHeuristic = this.heuristics.get(left);
			Integer rightHeuristic = this.heuristics.get(right);

			if(leftHeuristic < rightHeuristic)
			{
				return -1;
			}
			else if(leftHeuristic == rightHeuristic)
			{
				return 0;
			}
			else
			{
				return 1;
			}
		}

	}
}
