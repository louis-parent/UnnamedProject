package unnamed.model.element.menu.field;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.FormattedString;
import unnamed.model.element.menu.MenuElement;

public class TextField extends MenuElement 
{
	public static final TextField EMPTY = new EmptyField();
	
	private static PixelisedImage sprite;
	
	private static final long serialVersionUID = -174723088589298269L;
	
	private FormattedString text;
	
	public static void init() throws SlickException
	{
		TextField.sprite = new PixelisedImage("assets/menu/seed_field.png");
	}

	public TextField(ElementContainer container)
	{
		this("", container);
	}
	
	public TextField(String defaultText, ElementContainer container)
	{
		super(container);
		this.text = new FormattedString(defaultText, Color.white, 25, 7, 3);
	}

	@Override
	public PixelisedImage getSprite() throws SlickException
	{
		return TextField.sprite;
	}
	
	public void setText(String text)
	{
		this.text.setText(text);
	}
	
	@Override
	public FormattedString getFormattedText()
	{
		return this.text;
	}

	@Override
	public void tickUpdate()
	{
	}

	@Override
	public void keyPressed(int key, char c)
	{
		if(key == Input.KEY_BACK && !this.text.isEmpty())
		{
			this.text.substring(0, this.text.length() - 1);
		}
		else
		{
			this.text.append(c);
		}
	}
	
	private static class EmptyField extends TextField
	{
		private static final long serialVersionUID = 3752879867259651027L;

		public EmptyField()
		{
			super(ElementContainer.EMPTY);
		}
		
	}
}
