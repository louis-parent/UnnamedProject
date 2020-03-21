package unnamed.model.element.menu.button;

import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.field.Field;

public abstract class FieldValidator extends Button
{
	private static final long serialVersionUID = 2079967442580121662L;

	private Field field;
	
	public FieldValidator(Field field, ElementContainer container)
	{
		super(container);
		this.field = field;
	}

	@Override
	protected final void action() throws SlickException
	{
		this.action(this.field.getFormattedText().getText());
	}
	
	public void setField(Field field)
	{
		this.field = field;
	}
	
	protected abstract void action(String value) throws SlickException;
}
