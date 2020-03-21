package unnamed.model.element.menu.field;

import unnamed.model.container.ElementContainer;

public class SeedField extends Field
{
	private static final long serialVersionUID = 7791309804781391953L;

	public SeedField(ElementContainer container)
	{
		super(container);
	}

	@Override
	public boolean isValid(char c)
	{
		return true;
	}
}
