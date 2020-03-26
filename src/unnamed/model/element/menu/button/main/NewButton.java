package unnamed.model.element.menu.button.main;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.button.Button;

public class NewButton extends Button
{
	private static final long serialVersionUID = 3205595142678747391L;

	private static Image released;
	private static Image pressed;

	public NewButton(ElementContainer container)
	{
		super(container);
	}

	public NewButton(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public static void init() throws SlickException
	{
		NewButton.released = new PixelisedImage("assets/menu/new.png");
		NewButton.pressed = new PixelisedImage("assets/menu/new_pressed.png");
	}

	@Override
	protected void action() throws SlickException
	{
		GameController.getInstance().goToSeedMenu();
	}

	@Override
	protected Image getPressedSprite()
	{
		return NewButton.pressed;
	}

	@Override
	protected Image getReleasedSprite()
	{
		return NewButton.released;
	}
}
