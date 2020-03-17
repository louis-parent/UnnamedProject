package unnamed.controller;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import unnamed.view.Camera;
import unnamed.view.GameWindow;

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
		GameController instance = GameController.getInstance();
		GameWindow view = instance.getView();
		Camera cam = view.getCamera();

		switch(key)
		{
			case Input.KEY_UP:
				cam.movingUp();
				break;
			case Input.KEY_DOWN:
				cam.movingDown();
				break;
			case Input.KEY_LEFT:
				cam.movingLeft();
				break;
			case Input.KEY_RIGHT:
				cam.movingRight();
				break;
		}
	}

	@Override
	public void keyReleased(int key, char c)
	{
		GameController instance = GameController.getInstance();
		GameWindow view = instance.getView();
		Camera cam = view.getCamera();

		switch(key)
		{
			case Input.KEY_UP:
				cam.stopMovingUp();
				break;
			case Input.KEY_DOWN:
				cam.stopMovingDown();
				break;
			case Input.KEY_LEFT:
				cam.stopMovingLeft();
				break;
			case Input.KEY_RIGHT:
				cam.stopMovingRight();
				break;
		}
	}

}
