package unnamed.model.element.button.pause;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.button.Button;

public class MenuButton extends Button
{
	private static final long serialVersionUID = 7693211564246790703L;
	
	private static PixelisedImage released;
	private static PixelisedImage pressed;

	public MenuButton(ElementContainer container)
	{
		super(container);
	}
	
	public MenuButton(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public static void init() throws SlickException
	{
		MenuButton.released = new PixelisedImage("assets/menu/menu.png");
		MenuButton.pressed = new PixelisedImage("assets/menu/menu_pressed.png");
	}

	@Override
	protected void action()
	{
		GameController.getInstance().goToMainMenu();
	}

	@Override
	protected PixelisedImage getPressedSprite()
	{
		return MenuButton.pressed;
	}

	@Override
	protected PixelisedImage getReleasedSprite()
	{
		return MenuButton.released;
	}
}
