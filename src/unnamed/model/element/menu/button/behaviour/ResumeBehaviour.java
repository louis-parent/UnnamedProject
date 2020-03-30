package unnamed.model.element.menu.button.behaviour;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.menu.button.Button;

public class ResumeBehaviour extends DefaultBehaviour
{

	public ResumeBehaviour(Button button)
	{
		super(button);
	}
	
	@Override
	public void action() throws SlickException
	{
		GameController.getInstance().playGame();
	}
}
