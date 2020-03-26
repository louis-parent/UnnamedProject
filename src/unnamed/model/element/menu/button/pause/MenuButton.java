package unnamed.model.element.menu.button.pause;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.button.Button;

public class MenuButton extends Button
{
	private static final long serialVersionUID = 7693211564246790703L;

	private static Image released;
	private static Image pressed;

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
	protected void action() throws SlickException
	{
		GameController.getInstance().goToMainMenu();
	}

	@Override
	protected Image getPressedSprite()
	{
		return MenuButton.pressed;
	}

	@Override
	protected Image getReleasedSprite()
	{
		return MenuButton.released;
	}
}
