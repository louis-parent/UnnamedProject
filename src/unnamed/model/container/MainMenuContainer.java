package unnamed.model.container;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.Element;
import unnamed.model.element.menu.PlayButton;
import unnamed.model.element.menu.QuitButton;

public class MainMenuContainer extends ElementContainer
{

	@Override
	public void init() throws SlickException
	{
		PlayButton.init();
		PlayButton play = new PlayButton(0, 0, 0, this);
		this.addElement(play);
		play.centerElementAt(GameController.GAME_WIDTH / 2, (GameController.GAME_HEIGHT / 2) - play.getHeight());

		QuitButton.init();
		QuitButton quit = new QuitButton(0, 0, 0, this);
		this.addElement(quit);
		quit.centerElementAt(GameController.GAME_WIDTH / 2, (GameController.GAME_HEIGHT / 2) + quit.getHeight());
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
		Element oldElement = getTopElementAt(oldx, oldy);
		Element newElement = getTopElementAt(newx, newy);

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
