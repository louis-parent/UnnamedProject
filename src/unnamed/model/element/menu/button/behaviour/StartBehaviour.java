package unnamed.model.element.menu.button.behaviour;

import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.menu.button.Button;
import unnamed.model.element.menu.field.Field;

public class StartBehaviour extends FieldValidatorBehaviour
{

	public StartBehaviour(Field field, Button button)
	{
		super(field, button);
	}

	@Override
	public void action(String value) throws SlickException
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
			long c = value.charAt(i) % 255;
			c = c << ((currentByte - 1) * 8);

			if(((i / 8) % 2) == 0)
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
}
