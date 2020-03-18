package unnamed.controller;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class KeyController implements KeyListener
{
	@Override
	public void inputEnded()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void inputStarted()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAcceptingInput()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int key, char c)
	{
		GameController.getInstance().keyPressed(key, c);
	}

	@Override
	public void keyReleased(int key, char c)
	{
		GameController.getInstance().keyReleased(key, c);
	}

}
