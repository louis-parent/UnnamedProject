package unnamed.model.element.menu;

import org.newdawn.slick.Color;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;
import unnamed.model.element.Element;

public abstract class Button extends Element
{

	private boolean pressed;

	public Button(int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);
		this.pressed = false;
	}

	@Override
	public void click()
	{
		if(this.isPressed())
		{
			this.pressed = false;
			this.action();
		}
	}

	@Override
	public void pressed()
	{
		this.pressed = true;
	}

	@Override
	public void mouseLeft()
	{
		this.pressed = false;
	}

	public boolean isPressed()
	{
		return this.pressed;
	}

	@Override
	public int getHeight()
	{
		return this.getBiggerSpriteForHeight().getHeight();
	}

	private PixelisedImage getBiggerSpriteForHeight()
	{
		int pressedHeight = this.getPressedSprite().getHeight();
		int releasedHeight = this.getReleasedSprite().getHeight();

		return pressedHeight > releasedHeight ? this.getPressedSprite() : this.getReleasedSprite();
	}

	@Override
	public int getWidth()
	{
		return this.getBiggerSpriteForWidth().getWidth();
	}

	private PixelisedImage getBiggerSpriteForWidth()
	{
		int pressedWidth = this.getPressedSprite().getWidth();
		int releasedWidth = this.getReleasedSprite().getWidth();

		return pressedWidth > releasedWidth ? this.getPressedSprite() : this.getReleasedSprite();
	}

	@Override
	public PixelisedImage getSprite()
	{
		if(this.isPressed())
		{
			return this.getPressedSprite();
		}
		else
		{
			return this.getReleasedSprite();
		}
	}
	
	@Override
	protected boolean inTransparency(float x, float y)
	{
		int spriteX = (int) (x - this.getX());
		int spriteY = (int) (y - this.getY());

		PixelisedImage pressedSprite = this.getPressedSprite();
		Color pressedPixelColor = pressedSprite.getColor(spriteX, spriteY);
		
		PixelisedImage releasedSprite = this.getReleasedSprite();
		Color releasedPixelColor = releasedSprite.getColor(spriteX, spriteY);

		return pressedPixelColor.getAlpha() == 0 && releasedPixelColor.getAlpha() == 0;
	}

	protected abstract PixelisedImage getPressedSprite();

	protected abstract PixelisedImage getReleasedSprite();

	protected abstract void action();
}
