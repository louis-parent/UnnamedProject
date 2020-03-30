package unnamed.model.element.menu.button.behaviour;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.menu.button.Button;
import unnamed.model.element.menu.button.ButtonStatus;

public class LoadBehaviour extends DefaultBehaviour
{	
	public LoadBehaviour(Button button)
	{
		super(button);
				
		button.getContainer().addElementToTickUpdate(button);
	}
	
	@Override
	public void setButton(Button button)
	{
		Button oldButton = this.getButton();
		oldButton.getContainer().removeElementToTickUpdate(oldButton);
		
		super.setButton(button);
		
		button.getContainer().addElementToTickUpdate(button);
	}

	@Override
	public void action() throws SlickException
	{
		if(this.getButton().getStatus() != ButtonStatus.DISABLED)
		{
			GameController.getInstance().loadGame();
		}
	}
	
	@Override
	public void tickUpdate()
	{
		if(this.isDisabled())
		{
			this.getButton().setStatus(ButtonStatus.DISABLED);
		}
		else if(this.getButton().getStatus() == ButtonStatus.DISABLED)
		{
			this.getButton().setStatus(ButtonStatus.RELEASED);
		}
	}

	public boolean isDisabled()
	{
		return !GameController.saveExists();
	}
}
