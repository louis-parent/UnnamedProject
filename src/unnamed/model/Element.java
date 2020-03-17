package unnamed.model;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import unnamed.controller.GameController;

public abstract class Element
{
	private float x;
	private float y;
	private int z;

	public Element()
	{
		this(0, 0, 0);
	}

	public Element(float x, float y, int z)
	{
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
		GameController.getInstance().getModel().changeZOfElement(this, oldZ);
	}

	public boolean isInside(float x, float y)
	{
		boolean isBefore = x < this.getX() || y < this.getY();
		boolean isAfter = x > this.getX() + this.getSprite().getWidth() || y > this.getY() + this.getSprite().getHeight();

		return !isBefore && !isAfter && !this.inTransparency(x, y);
	}

	private boolean inTransparency(float x, float y)
	{
		int spriteX = (int) (x - this.getX());
		int spriteY = (int) (y - this.getY());

		Image sprite = this.getSprite();
		Color pixelColor = sprite.getColor(spriteX, spriteY);

		return pixelColor.getAlpha() == 0;
	}

	public abstract Image getSprite();

}
