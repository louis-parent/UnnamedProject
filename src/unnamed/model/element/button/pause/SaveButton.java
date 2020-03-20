package unnamed.model.element.button.pause;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.button.Button;

public class SaveButton extends Button
{
	private static final long serialVersionUID = -9138057225816165313L;
	
	private static PixelisedImage released;
	private static PixelisedImage pressed;
	
	public SaveButton(ElementContainer container)
	{
		super(container);
	}
	
	public SaveButton(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public static void init() throws SlickException
	{
		SaveButton.released = new PixelisedImage("assets/menu/save.png");
		SaveButton.pressed = new PixelisedImage("assets/menu/save_pressed.png");
	}

	@Override
	protected void action()
	{
		GameController.getInstance().saveGame();
	}

	@Override
	protected PixelisedImage getPressedSprite()
	{
		return SaveButton.pressed;
	}

	@Override
	protected PixelisedImage getReleasedSprite()
	{
		return SaveButton.released;
	}
}
