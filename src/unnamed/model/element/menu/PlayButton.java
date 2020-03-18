package unnamed.model.element.menu;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.container.ElementContainer;

public class PlayButton extends Button
{

	private static Image released;
	private static Image pressed;

	public PlayButton(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public static void init() throws SlickException
	{
		PlayButton.released = new Image("assets/menu/play.png");
		PlayButton.pressed = new Image("assets/menu/play_pressed.png");
	}

	@Override
	protected void action()
	{
		GameController.getInstance().playGame();
	}

	@Override
	public Image getSprite()
	{
		if(this.isPressed())
		{
			return PlayButton.pressed;
		}
		else
		{
			return PlayButton.released;
		}
	}

}
