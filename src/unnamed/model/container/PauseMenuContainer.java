package unnamed.model.container;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.menu.button.MenuFactory;

public class PauseMenuContainer extends MenuContainer
{
	@Override
	public void init() throws SlickException
	{
		this.buildMenu(MenuFactory.RESUME_BUTTON, MenuFactory.SAVE_BUTTON, MenuFactory.MENU_BUTTON);
	}

	@Override
	public void keyPressed(int key, char c) throws SlickException
	{
		super.keyPressed(key, c);

		switch(key)
		{
			case Input.KEY_ESCAPE:
				GameController.getInstance().playGame();
				break;
		}
	}
}
