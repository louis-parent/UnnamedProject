package unnamed.model.element.button.main;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.button.Button;

public class NewButton extends Button
{
	private static final long serialVersionUID = 3205595142678747391L;
	
	private static PixelisedImage released;
	private static PixelisedImage pressed;

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
		GameController.getInstance().createGame();
	}

	@Override
	protected PixelisedImage getPressedSprite()
	{
		return NewButton.pressed;
	}

	@Override
	protected PixelisedImage getReleasedSprite()
	{
		return NewButton.released;
	}
}
