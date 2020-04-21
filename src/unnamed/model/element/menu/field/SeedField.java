package unnamed.model.element.menu.field;

import unnamed.model.container.ElementContainer;

public class SeedField extends Field
{
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
