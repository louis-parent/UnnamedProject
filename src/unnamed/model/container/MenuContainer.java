package unnamed.model.container;

import java.util.Arrays;

import unnamed.controller.GameController;
import unnamed.model.element.button.Button;
import unnamed.model.element.button.ButtonFactory;

public abstract class MenuContainer extends ElementContainer
{
	private static final long serialVersionUID = 8508648978109147145L;

	private static final int PADDING = 50;
	
	public void buildMenu(String... buttonTypes)
	{
		Button[] buttons = new Button[buttonTypes.length];
		
		for(int i = 0; i < buttonTypes.length; i++)
		{
			buttons[i] = ButtonFactory.create(buttonTypes[i], this);
		}
		
		this.addAllElements(Arrays.asList(buttons));
		this.verticalAlign(PADDING, buttons);
	}
	
	@Override
	public int getHeight()
	{
		return GameController.GAME_HEIGHT;
	}
	
	@Override
	public int getWidth()
	{
		return GameController.GAME_WIDTH;
	}
}
