package unnamed.controller;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public class MouseController implements MouseListener
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
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			GameController.getInstance().clickAt(x, y);
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(int change)
	{
		if(change < 0)
		{
			GameController.getInstance().getView().getCamera().unzoom();
		}
		else
		{
			GameController.getInstance().getView().getCamera().zoom();
		}
	}

}
