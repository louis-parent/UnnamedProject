package unnamed.controller;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

public class MouseController implements MouseListener
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
	public void mouseClicked(int button, int x, int y, int clickCount)
	{

	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy)
	{
		try
		{
			GameController.getInstance().mouseDragged(oldx, oldy, newx, newy);
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{

	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			try
			{
				GameController.getInstance().pressedAt(x, y);
			}
			catch(SlickException e)
			{
				e.printStackTrace();
			}
		}
		else if(button == Input.MOUSE_MIDDLE_BUTTON)
		{
			GameController.getInstance().wheelPressedAt(x, y);
		}
		else if(button == Input.MOUSE_LEFT_BUTTON)
		{
			GameController.getInstance().rightClickAt(x, y);
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			GameController.getInstance().clickAt(x, y);
		}
		else if(button == Input.MOUSE_MIDDLE_BUTTON)
		{
			GameController.getInstance().wheelReleasedAt(x, y);
		}
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		GameController.getInstance().mouseWheelMoved(change);
	}

}
