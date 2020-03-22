package unnamed.model.element;

import java.io.Serializable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.FormattedString;

public abstract class Element implements Serializable
{
	private static final long serialVersionUID = -8180400148689092894L;

	public static final Element EMPTY = new EmptyElement();

	private ElementContainer container;

	private float x;
	private float y;
	private int z;

	private boolean isSelected;

	public Element(ElementContainer container)
	{
		this(0, 0, 0, container);
	}

	public Element(float x, float y, int z, ElementContainer container)
	{
		this.container = container;

		this.x = x;
		this.y = y;
		this.z = z;

		this.isSelected = false;
	}

	public float getX()
	{
		return this.x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return this.y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public int getZ()
	{
		return this.z;
	}

	public void setZ(int z)
	{
		int oldZ = this.z;
		this.z = z;
		this.container.changeZOfElement(this, oldZ);
	}

	public void centerElementAt(int x, int y) throws SlickException
	{
		this.setX(x - (this.getWidth() / 2));
		this.setY(y - (this.getHeight() / 2));
	}

	public boolean isInside(float x, float y) throws SlickException
	{
		boolean isBefore = (x < this.getX()) || (y < this.getY());
		boolean isAfter = (x > (this.getX() + this.getWidth())) || (y > (this.getY() + this.getHeight()));

		return !isBefore && !isAfter && !this.inTransparency(x, y);
	}

	public int getHeight() throws SlickException
	{
		return this.getSprite().getHeight();
	}

	public int getWidth() throws SlickException
	{
		return this.getSprite().getWidth();
	}

	protected boolean inTransparency(float x, float y) throws SlickException
	{
		int spriteX = (int) (x - this.getX());
		int spriteY = (int) (y - this.getY());

		Image sprite = this.getSprite();
		Color pixelColor = sprite.getColor(spriteX, spriteY);

		return pixelColor.getAlpha() == 0;
	}

	public ElementContainer getContainer()
	{
		return this.container;
	}

	public boolean isSelected()
	{
		return this.isSelected;
	}

	public void click() throws SlickException
	{
		if(this.isSelected())
		{
			this.deselect();
		}
		else
		{
			this.select();
		}
	}

	public void select() throws SlickException
	{
		this.isSelected = true;
		this.updateSelect();
	}

	public void deselect()
	{
		this.isSelected = false;
		this.updateDeselect();
	}
	
	public boolean isEmpty()
	{
		return false;
	}

	public abstract Image getSprite() throws SlickException;

	public abstract FormattedString getFormattedText();

	public abstract void tickUpdate();

	protected abstract void updateSelect() throws SlickException;

	protected abstract void updateDeselect();

	public abstract void pressed();

	public abstract void mouseLeft();

	private static class EmptyElement extends Element
	{
		private static final long serialVersionUID = -8396774274171993181L;

		public EmptyElement()
		{
			super(ElementContainer.EMPTY);
		}

		@Override
		public Image getSprite() throws SlickException
		{
			return PixelisedImage.getEmpty();

		}

		@Override
		public void pressed()
		{
		}

		@Override
		public void mouseLeft()
		{
		}

		@Override
		public void tickUpdate()
		{
		}

		@Override
		public FormattedString getFormattedText()
		{
			return FormattedString.EMPTY;
		}

		@Override
		public void updateSelect()
		{

		}

		@Override
		public void updateDeselect()
		{

		}
		
		@Override
		public boolean isEmpty()
		{
			return true;
		}
	}
}
