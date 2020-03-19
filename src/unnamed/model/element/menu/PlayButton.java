package unnamed.model.element.menu;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public class PlayButton extends Button
{

	private static PixelisedImage released;
	private static PixelisedImage pressed;

	public PlayButton(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public static void init() throws SlickException
	{
		PlayButton.released = new PixelisedImage("assets/menu/play.png");
		PlayButton.pressed = new PixelisedImage("assets/menu/play_pressed.png");
	}

	@Override
	protected void action()
	{
		GameController.getInstance().playGame();
	}

	@Override
	protected PixelisedImage getPressedSprite()
	{
		return PlayButton.pressed;
	}

	@Override
	protected PixelisedImage getReleasedSprite()
	{
		return PlayButton.released;
	}
}
