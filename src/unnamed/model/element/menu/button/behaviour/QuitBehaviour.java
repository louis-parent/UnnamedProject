package unnamed.model.element.menu.button.behaviour;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.menu.button.Button;

public class QuitBehaviour extends DefaultBehaviour
{

	public QuitBehaviour(Button button)
	{
		super(button);
	}

	@Override
	public void action() throws SlickException
	{
		GameController.stop();
	}
}
