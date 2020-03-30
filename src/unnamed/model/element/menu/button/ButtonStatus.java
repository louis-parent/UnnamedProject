package unnamed.model.element.menu.button;

public enum ButtonStatus
{
	RELEASED,
	PRESSED,
	DISABLED;
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}
}
