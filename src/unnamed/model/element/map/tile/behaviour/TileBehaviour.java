package unnamed.model.element.map.tile.behaviour;

public interface TileBehaviour
{

	public abstract void tickUpdate();
	public abstract void pressed();
	public abstract void mouseLeft();
	public abstract void clickEvent();
	public abstract void selectEvent();
	public abstract void deselectEvent();

}
