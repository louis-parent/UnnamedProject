package unnamed.model.element.button.main;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.button.Button;

public class PlayButton extends Button
{
	private static final long serialVersionUID = 3205595142678747391L;
	
	private static PixelisedImage released;
	private static PixelisedImage pressed;

	public PlayButton(ElementContainer container)
	{
		super(container);
	}
	
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
