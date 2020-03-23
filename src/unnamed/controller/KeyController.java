package unnamed.controller;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

public class KeyController implements KeyListener
{
	@Override
	public void inputEnded()
	{

	}

	@Override
	public void inputStarted()
	{

	}

	@Override
	public boolean isAcceptingInput()
	{
		return true;
	}

	@Override
	public void setInput(Input arg0)
	{

	}

	@Override
	public void keyPressed(int key, char c)
	{
		try
		{
			GameController.getInstance().keyPressed(key, c);
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void keyReleased(int key, char c)
	{
		GameController.getInstance().keyReleased(key, c);
	}

}
