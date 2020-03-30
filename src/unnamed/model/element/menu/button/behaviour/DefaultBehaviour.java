package unnamed.model.element.menu.button.behaviour;

import org.newdawn.slick.SlickException;

import unnamed.model.element.menu.button.Button;

public class DefaultBehaviour implements ButtonBehaviour
{
	private static ButtonBehaviour EMPTY;
	
	private Button button;
	
	public DefaultBehaviour(Button button)
	{
		this.button = button;
	}
	
	@Override
	public void action() throws SlickException
	{
	}

	@Override
	public void tickUpdate()
	{
	}

	@Override
	public void setButton(Button button)
	{
		this.button = button;
	}
	
	public Button getButton()
	{
		return this.button;
	}

	public static ButtonBehaviour getEmptyBehaviour()
	{
		if(DefaultBehaviour.EMPTY == null)
		{
			DefaultBehaviour.EMPTY = new EmptyBehaviour();
		}
		
		return DefaultBehaviour.EMPTY;
	}
	
	private static class EmptyBehaviour implements ButtonBehaviour
	{
		@Override
		public void action() throws SlickException
		{			
		}

		@Override
		public void tickUpdate()
		{			
		}

		@Override
		public void setButton(Button button)
		{			
		}
		
	}
}
