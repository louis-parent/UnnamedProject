package unnamed.view;

import java.util.List;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.Element;
import unnamed.model.element.menu.FormattedString;

public class GameWindow extends BasicGame
{
	private GameController controller;
	private Camera cam;

	public GameWindow(String gameName, GameController controller)
	{
		super(gameName);
		this.controller = controller;
		this.cam = new Camera();
	}

	@Override
	public void init(GameContainer container) throws SlickException
	{
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

	public void updateWindow(int delta)
	{
		this.cam.update(delta);
	}

	public void renderWindow(Graphics g, List<Element> elements) throws SlickException
	{
		this.cam.render(g);
		for(Element element : elements)
		{
			g.drawImage(element.getSprite(), element.getX(), element.getY());

			FormattedString formattedString = element.getFormattedText();
			if(!formattedString.isEmpty())
			{
				g.setColor(formattedString.getColor());
				g.drawString(formattedString.getFormatedText(), element.getX() + formattedString.getOffsetX(), element.getY() + formattedString.getOffsetY());
			}
		}
	}

	public Camera getCamera()
	{
		return this.cam;
	}
}