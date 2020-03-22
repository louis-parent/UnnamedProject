package unnamed.model.element.menu.button.main;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.button.Button;

public class QuitButton extends Button
{
	private static final long serialVersionUID = -904106979096792682L;
	
	private static Image released;
	private static Image pressed;

	public QuitButton(ElementContainer container)
	{
		super(container);
	}
	
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
	protected Image getPressedSprite()
	{
		return QuitButton.pressed;
	}

	@Override
	protected Image getReleasedSprite()
	{
		return QuitButton.released;
	}
}
