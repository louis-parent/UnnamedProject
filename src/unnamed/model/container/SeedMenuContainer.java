package unnamed.model.container;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.menu.MenuElement;
import unnamed.model.element.menu.button.Button;
import unnamed.model.element.menu.button.MenuFactory;
import unnamed.model.element.menu.button.behaviour.FieldValidatorBehaviour;
import unnamed.model.element.menu.field.Field;

public class SeedMenuContainer extends MenuContainer
{
	private Field seedField;

	@Override
	public void init() throws SlickException
	{
		MenuElement[] elements = this.buildMenu(MenuFactory.SEED_FIELD, MenuFactory.START_BUTTON);

		this.seedField = (Field) elements[0];
		((FieldValidatorBehaviour) ((Button) elements[1]).getBehaviour()).setField(this.seedField);
	}

	@Override
	public void enter() throws SlickException
	{
		super.enter();
		this.seedField.setText("");
	}

	@Override
	public void keyPressed(int key, char c) throws SlickException
	{
		super.keyPressed(key, c);
		switch(key)
		{
			case Input.KEY_ESCAPE:
				GameController.getInstance().goToMainMenu();
				break;
		}
	}
}
