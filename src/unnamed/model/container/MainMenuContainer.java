package unnamed.model.container;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.Element;
import unnamed.model.element.button.ButtonFactory;

public class MainMenuContainer extends MenuContainer
{
	private static final long serialVersionUID = -4516876692738107879L;

	@Override
	public void init() throws SlickException
	{
		this.buildMenu(ButtonFactory.NEW_BUTTON, ButtonFactory.LOAD_BUTTON, ButtonFactory.QUIT_BUTTON);
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
				GameController.stop();
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
