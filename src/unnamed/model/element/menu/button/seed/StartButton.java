package unnamed.model.element.menu.button.seed;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.button.FieldValidator;
import unnamed.model.element.menu.field.Field;

public class StartButton extends FieldValidator
{
	private static final long serialVersionUID = -3868409855098130358L;
	
	private static PixelisedImage released;
	private static PixelisedImage pressed;
	
	public StartButton(Field field, ElementContainer container)
	{
		super(field, container);
	}

	public static void init() throws SlickException
	{
		StartButton.released = new PixelisedImage("assets/menu/start.png");
		StartButton.pressed = new PixelisedImage("assets/menu/start_pressed.png");
	}

	@Override
	protected void action(String value) throws SlickException
	{
		long seed;
		
		if(value.isEmpty())
		{
			seed = GameController.getInstance().getRandom().nextLong();
		}
		else
		{
			seed = this.seedToLong(value);
		}
		
		GameController.getInstance().createGame(seed);
	}

	private long seedToLong(String value)
	{
		long result = 0;
		
		for(int i = 0; i < value.length(); i++)
		{
			int currentByte = 8 - (i % 8);
			long c = value.charAt(i);
			c = c << (currentByte - 1) * 8;
			
			if((i / 8) % 2 == 0)
			{
				result |= c;
			}
			else
			{
				result &= c;
			}
		}
		
		return result;
	}

	@Override
	protected PixelisedImage getPressedSprite()
	{
		return StartButton.pressed;
	}

	@Override
	protected PixelisedImage getReleasedSprite()
	{
		return StartButton.released;
	}
}
