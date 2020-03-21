package unnamed.model.element.menu;

import org.newdawn.slick.Color;

public class FormattedString
{
	public static FormattedString EMPTY = new FormattedString("");
	
	private String text;

	private Color color;
	
	private int maxLength;

	private int offsetX;
	private int offsetY;

	public FormattedString(String text)
	{
		this(text, Color.black, Integer.MAX_VALUE, 0, 0);
	}

	public FormattedString(String text, Color color, int maxLength, int offsetX, int offsetY)
	{
		this.text = text;
		this.color = color;
		this.maxLength = maxLength;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public String getText()
	{
		return this.text;
	}
	
	public String getFormatedText()
	{
		int minLength = this.length() < this.maxLength ? this.length() : this.maxLength;
		return this.text.substring(this.length() - minLength, this.length());
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public int getOffsetX()
	{
		return this.offsetX;
	}

	public void setOffsetX(int offsetX)
	{
		this.offsetX = offsetX;
	}

	public int getOffsetY()
	{
		return this.offsetY;
	}

	public void setOffsetY(int offsetY)
	{
		this.offsetY = offsetY;
	}
	
	public boolean isEmpty()
	{
		return this.text.isEmpty();
	}
	
	public int length()
	{
		return this.text.length();
	}
	
	public void substring(int begin, int end)
	{
		this.text = this.text.substring(begin, end);
	}
	
	public void append(char c)
	{
		this.text += c;
	}
}
