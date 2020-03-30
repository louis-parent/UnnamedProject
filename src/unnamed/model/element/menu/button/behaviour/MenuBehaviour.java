package unnamed.model.element.menu.button.behaviour;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.menu.button.Button;

public class MenuBehaviour extends DefaultBehaviour
{

	public MenuBehaviour(Button button)
	{
		super(button);
	}

	@Override
	public void action() throws SlickException
	{
		GameController.getInstance().goToMainMenu();
	}
}
