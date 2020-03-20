package unnamed.model.element;

import org.newdawn.slick.Color;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public abstract class Element
{
	public static final Element EMPTY = new EmptyElement();

	private ElementContainer container;

	private float x;
	private float y;
	private int z;

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

	public void centerElementAt(int x, int y)
	{
		this.setX(x - (this.getWidth() / 2));
		this.setY(y - (this.getHeight() / 2));
	}

	public boolean isInside(float x, float y)
	{
		boolean isBefore = x < this.getX() || y < this.getY();
		boolean isAfter = x > this.getX() + this.getWidth() || y > this.getY() + this.getHeight();

		return !isBefore && !isAfter && !this.inTransparency(x, y);
	}

	public int getHeight()
	{
		return this.getSprite().getHeight();
	}

	public int getWidth()
	{
		return this.getSprite().getWidth();
	}

	protected boolean inTransparency(float x, float y)
	{
		int spriteX = (int) (x - this.getX());
		int spriteY = (int) (y - this.getY());

		PixelisedImage sprite = this.getSprite();
		Color pixelColor = sprite.getColor(spriteX, spriteY);

		return pixelColor.getAlpha() == 0;
	}

	public abstract PixelisedImage getSprite();

	public ElementContainer getContainer()
	{
		return container;
	}
	
	public abstract void tickUpdate();
	public abstract void click();
	public abstract void pressed();
	public abstract void mouseLeft();
	
	private static class EmptyElement extends Element
	{
		public EmptyElement()
		{
			super(ElementContainer.EMPTY);
		}

		@Override
		public PixelisedImage getSprite()
		{
			return PixelisedImage.EMPTY;

		}

		@Override
		public void click()
		{
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
	}
}
