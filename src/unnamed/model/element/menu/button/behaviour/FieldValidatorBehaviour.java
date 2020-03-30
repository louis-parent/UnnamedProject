package unnamed.model.element.menu.button.behaviour;

import org.newdawn.slick.SlickException;

import unnamed.model.element.menu.button.Button;
import unnamed.model.element.menu.field.Field;

public abstract class FieldValidatorBehaviour extends DefaultBehaviour
{
	private Field field;
	
	public FieldValidatorBehaviour(Field field, Button button)
	{
		super(button);
		this.field = field;
	}

	@Override
	public final void action() throws SlickException
	{
		this.action(this.field.getFormattedText().getText());
	}

	public void setField(Field field)
	{
		this.field = field;
	}

	protected abstract void action(String value) throws SlickException;
}
