package unnamed.model.element.menu.field;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.SelectableElement;
import unnamed.model.element.menu.FormattedString;
import unnamed.model.element.menu.MenuElement;

public abstract class Field extends MenuElement implements SelectableElement
{
	private static final long serialVersionUID = -174723088589298269L;

	private static final Field EMPTY = new EmptyField();

	private static Image background;

	private FormattedString text;

	private boolean isSelected;

	public static void init() throws SlickException
	{
		Field.background = new PixelisedImage("assets/menu/field_background.png");
	}

	public Field(ElementContainer container)
	{
		this("", container);
	}

	public Field(String defaultText, ElementContainer container)
	{
		super(container);
		this.text = new FormattedString(defaultText, Color.white, 25, 7, 3);

		this.isSelected = false;
	}

	@Override
	public Image getSprite() throws SlickException
	{
		return Field.background;
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
		if((key == Input.KEY_BACK) && !this.text.isEmpty())
		{
			this.text.substring(0, this.text.length() - 1);
		}
		else if(this.isValid(c))
		{
			this.text.append(c);
		}
	}

	@Override
	public void clickEvent()
	{

	}

	@Override
	public boolean isSelected()
	{
		return this.isSelected;
	}

	@Override
	public void selectEvent()
	{

	}

	@Override
	public void deselectEvent()
	{

	}

	@Override
	public void setSelected(boolean toSelect)
	{
		this.isSelected = toSelect;
	}

	public abstract boolean isValid(char c);

	public static Field getEmptyField()
	{
		return Field.EMPTY;
	}

	private static class EmptyField extends Field
	{
		private static final long serialVersionUID = 3752879867259651027L;

		public EmptyField()
		{
			super(ElementContainer.getEmptyContainer());
		}

		@Override
		public boolean isValid(char c)
		{
			return false;
		}

		@Override
		public boolean isEmpty()
		{
			return true;
		}

		@Override
		public boolean isSelected()
		{
			return false;
		}

		@Override
		public void selectEvent()
		{

		}

		@Override
		public void deselectEvent()
		{

		}

		@Override
		public void setSelected(boolean toSelect)
		{

		}

	}
}
