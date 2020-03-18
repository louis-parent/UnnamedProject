package unnamed.model.element.menu;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.container.ElementContainer;

public class QuitButton extends Button
{

	private static Image released;
	private static Image pressed;

	public QuitButton(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public static void init() throws SlickException
	{
		QuitButton.released = new Image("assets/menu/quit.png");
		QuitButton.pressed = new Image("assets/menu/quit_pressed.png");
	}

	@Override
	protected void action()
	{
		GameController.stop();
	}

	@Override
	public Image getSprite()
	{
		if(this.isPressed())
		{
			return QuitButton.pressed;
		}
		else
		{
			return QuitButton.released;
		}
	}

}
