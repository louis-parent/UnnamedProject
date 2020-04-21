package unnamed.model.element.map.tile.behaviour;

import java.io.Serializable;

import org.newdawn.slick.SlickException;

public interface TileBehaviour extends Serializable
{

	public abstract void tickUpdate() throws SlickException;

	public abstract void pressed();

	public abstract void mouseLeft();

	public abstract void clickEvent();

	public abstract void selectEvent();

	public abstract void deselectEvent();

	public abstract void cleanUp();

	public abstract void informNeighbourChange() throws SlickException;

}
