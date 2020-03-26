package unnamed.model.container;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.menu.button.MenuFactory;

public class GameOverMenuContainer extends MenuContainer
{
	private static final long serialVersionUID = 3759703099376065619L;

	@Override
	public void init() throws SlickException
	{
		this.buildMenu(MenuFactory.START_BUTTON, MenuFactory.MENU_BUTTON, MenuFactory.QUIT_BUTTON);
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
