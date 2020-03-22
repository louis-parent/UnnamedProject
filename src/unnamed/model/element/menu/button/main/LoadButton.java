package unnamed.model.element.menu.button.main;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.button.Button;

public class LoadButton extends Button
{
	private static final long serialVersionUID = -8409899989368706116L;
	
	private static Image released;
	private static Image pressed;
	private static Image disabled;

	public LoadButton(ElementContainer container)
	{
		super(container);
	}
	
	public LoadButton(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
	}

	public static void init() throws SlickException
	{
		LoadButton.released = new PixelisedImage("assets/menu/load.png");
		LoadButton.pressed = new PixelisedImage("assets/menu/load_pressed.png");
		LoadButton.disabled = new PixelisedImage("assets/menu/load_disabled.png");
	}

	@Override
	protected void action()
	{
		if(!this.isDisabled())
		{
			GameController.getInstance().loadGame();
		}
	}
	
	public boolean isDisabled()
	{
		return !GameController.saveExists();
	}

	@Override
	public Image getSprite()
	{
		if(this.isDisabled())
		{
			return LoadButton.disabled;
		}
		else
		{
			return super.getSprite();
		}
	}
	
	@Override
	protected Image getPressedSprite()
	{
		return LoadButton.pressed;
	}

	@Override
	protected Image getReleasedSprite()
	{
		return LoadButton.released;
	}
}
