package unnamed.model.container;

import java.util.Arrays;

import unnamed.model.element.button.Button;
import unnamed.model.element.button.ButtonFactory;

public abstract class MenuContainer extends ElementContainer
{
	private static final long serialVersionUID = 8508648978109147145L;
	
	public void buildMenu(String... buttonTypes)
	{
		Button[] buttons = new Button[buttonTypes.length];
		
		for(int i = 0; i < buttonTypes.length; i++)
		{
			buttons[i] = ButtonFactory.create(buttonTypes[i], this);
		}
		
		this.addAllElements(Arrays.asList(buttons));
		this.verticalAlign(50, buttons);
	}
}
