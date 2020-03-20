package unnamed.model.container;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.Element;
import unnamed.model.element.button.ButtonFactory;

public class PauseMenuContainer extends MenuContainer
{
	private static final long serialVersionUID = 1412919249921734035L;

	@Override
	public void init() throws SlickException
	{
		this.buildMenu(ButtonFactory.RESUME_BUTTON, ButtonFactory.SAVE_BUTTON, ButtonFactory.MENU_BUTTON);
	}

	@Override
	public void tickUpdate()
	{
	}

	@Override
	public void mouseWheelMoved(int change)
	{
	}

	@Override
	public void keyReleased(int key, char c)
	{
	}

	@Override
	public void keyPressed(int key, char c)
	{
		switch(key)
		{
			case Input.KEY_ESCAPE:
				GameController.getInstance().playGame();
				break;
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy)
	{
		Element oldElement = this.getTopElementAt(oldx, oldy);
		Element newElement = this.getTopElementAt(newx, newy);

		if(oldElement != newElement)
		{
			oldElement.mouseLeft();
		}
	}

	@Override
	public void wheelPressedAt(int x, int y)
	{
	}

	@Override
	public void wheelReleasedAt(int x, int y)
	{
	}
}
