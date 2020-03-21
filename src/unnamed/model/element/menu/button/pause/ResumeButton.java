package unnamed.model.element.menu.button.pause;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.button.Button;

public class ResumeButton extends Button
{
	private static final long serialVersionUID = -1805146767265116468L;
	
	private static PixelisedImage released;
	private static PixelisedImage pressed;
	
	public ResumeButton(ElementContainer container)
	{
		super(container);
	}

	public ResumeButton(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public static void init() throws SlickException
	{
		ResumeButton.released = new PixelisedImage("assets/menu/resume.png");
		ResumeButton.pressed = new PixelisedImage("assets/menu/resume_pressed.png");
	}

	@Override
	protected void action()
	{
		GameController.getInstance().playGame();
	}

	@Override
	protected PixelisedImage getPressedSprite()
	{
		return ResumeButton.pressed;
	}

	@Override
	protected PixelisedImage getReleasedSprite()
	{
		return ResumeButton.released;
	}
}
