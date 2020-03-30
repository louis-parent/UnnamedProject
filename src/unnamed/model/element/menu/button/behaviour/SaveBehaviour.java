package unnamed.model.element.menu.button.behaviour;

import unnamed.controller.GameController;
import unnamed.model.element.menu.button.Button;

public class SaveBehaviour extends DefaultBehaviour
{

	public SaveBehaviour(Button button)
	{
		super(button);
	}

	@Override
	public void action()
	{
		GameController.getInstance().saveGame();
	}
}
