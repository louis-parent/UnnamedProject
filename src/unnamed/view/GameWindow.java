package unnamed.view;

import java.util.List;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.controller.MouseController;
import unnamed.model.Element;

public class GameWindow extends BasicGame
{
	private GameContainer container;
	private GameController controller;

	public GameWindow(String gameName, GameController controller)
	{
		super(gameName);
		this.controller = controller;
	}

	@Override
	public void init(GameContainer container) throws SlickException
	{
		this.container = container;
		this.controller.init(container);
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException
	{
		this.controller.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException
	{
		this.controller.render(container, g);
	}

	public void renderWindow(Graphics g, List<Element> elements)
	{
		for(Element element : elements)
		{
			g.drawImage(element.getSprite(), element.getX(), element.getY());
		}
	}

	@Override
	public void keyReleased(int key, char c)
	{
		if(Input.KEY_ESCAPE == key)
		{
			container.exit();
		}
	}
}