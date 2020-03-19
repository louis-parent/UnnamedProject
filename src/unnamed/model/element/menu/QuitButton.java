package unnamed.model.element.menu;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class QuitButton extends Button
{

	private static PixelisedImage released;
	private static PixelisedImage pressed;

	public QuitButton(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public static void init() throws SlickException
	{
		QuitButton.released = new PixelisedImage("assets/menu/quit.png");
		QuitButton.pressed = new PixelisedImage("assets/menu/quit_pressed.png");
	}

	@Override
	protected void action()
	{
		GameController.stop();
	}

	@Override
	protected PixelisedImage getPressedSprite()
	{
		return QuitButton.pressed;
	}

	@Override
	protected PixelisedImage getReleasedSprite()
	{
		return QuitButton.released;
	}
}
