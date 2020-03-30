package unnamed.model.element.menu.button;

public enum ButtonType
{
	LOAD,
	NEW,
	QUIT,
	MENU,
	RESUME,
	SAVE,
	START;
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}
}
