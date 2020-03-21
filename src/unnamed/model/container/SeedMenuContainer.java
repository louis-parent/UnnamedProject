package unnamed.model.container;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.menu.MenuElement;
import unnamed.model.element.menu.button.FieldValidator;
import unnamed.model.element.menu.button.MenuFactory;
import unnamed.model.element.menu.field.TextField;

public class SeedMenuContainer extends MenuContainer
{
	private static final long serialVersionUID = 4282892685264066141L;

	private TextField seedField;

	@Override
	public void init() throws SlickException
	{
		MenuElement[] elements = this.buildMenu(MenuFactory.TEXT_FIELD, MenuFactory.START_BUTTON);

		this.seedField = (TextField) elements[0];
		((FieldValidator) elements[1]).setField(this.seedField);
	}

	@Override
	public void tickUpdate()
	{
	}

	@Override
	public void keyPressed(int key, char c)
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
