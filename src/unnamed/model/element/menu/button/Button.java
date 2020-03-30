package unnamed.model.element.menu.button;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.MenuElement;
import unnamed.model.element.menu.button.behaviour.ButtonBehaviour;
import unnamed.model.element.menu.button.behaviour.DefaultBehaviour;

public class Button extends MenuElement
{
	private static final long serialVersionUID = 410744874078962405L;

	private static ButtonImageRegistry imageRegistry;

	private static Button EMPTY;

	public static void init() throws SlickException
	{
		Button.imageRegistry = new ButtonImageRegistry();
	}

	private ButtonType type;
	private ButtonStatus status;

	private ButtonBehaviour behaviour;

	public Button(ButtonType type, ButtonBehaviour behaviour, ElementContainer container)
	{
		this(type, behaviour, 0, 0, 0, container);
	}

	public Button(ButtonType type, ButtonBehaviour behaviour, int x, int y, int z, ElementContainer container)
	{
		super(x, y, z, container);

		this.type = type;
		this.status = ButtonStatus.RELEASED;

		this.behaviour = behaviour;
	}

	@Override
	public void tickUpdate()
	{
		this.behaviour.tickUpdate();
	}

	@Override
	public void clickEvent() throws SlickException
	{
		if(this.isPressed())
		{
			this.status = ButtonStatus.RELEASED;
			this.action();
		}
	}

	@Override
	public void pressed()
	{
		if(this.status == ButtonStatus.RELEASED)
		{
			this.status = ButtonStatus.PRESSED;
		}
	}

	@Override
	public void mouseLeft()
	{
		if(this.isPressed())
		{
			this.status = ButtonStatus.RELEASED;
		}
	}

	public boolean isPressed()
	{
		return this.status == ButtonStatus.PRESSED;
	}

	@Override
	public int getHeight()
	{
		return this.getBiggerSpriteForHeight().getHeight();
	}

	private Image getBiggerSpriteForHeight()
	{
		Image pressedSprite = Button.imageRegistry.getImageFor(this.type, ButtonStatus.PRESSED);
		Image releasedSprite = Button.imageRegistry.getImageFor(this.type, ButtonStatus.RELEASED);

		int pressedHeight = pressedSprite.getHeight();
		int releasedHeight = releasedSprite.getHeight();

		return pressedHeight > releasedHeight ? pressedSprite : releasedSprite;
	}

	@Override
	public int getWidth()
	{
		return this.getBiggerSpriteForWidth().getWidth();
	}

	private Image getBiggerSpriteForWidth()
	{
		Image pressedSprite = Button.imageRegistry.getImageFor(this.type, ButtonStatus.PRESSED);
		Image releasedSprite = Button.imageRegistry.getImageFor(this.type, ButtonStatus.RELEASED);

		int pressedWidth = pressedSprite.getWidth();
		int releasedWidth = releasedSprite.getWidth();

		return pressedWidth > releasedWidth ? pressedSprite : releasedSprite;
	}

	@Override
	public Image getSprite()
	{
		return Button.imageRegistry.getImageFor(this.type, this.status);
	}

	@Override
	protected boolean inTransparency(float x, float y)
	{
		int spriteX = (int) (x - this.getX());
		int spriteY = (int) (y - this.getY());

		Image pressedSprite = Button.imageRegistry.getImageFor(this.type, ButtonStatus.PRESSED);
		Color pressedPixelColor = pressedSprite.getColor(spriteX, spriteY);

		Image releasedSprite = Button.imageRegistry.getImageFor(this.type, ButtonStatus.RELEASED);
		Color releasedPixelColor = releasedSprite.getColor(spriteX, spriteY);

		return (pressedPixelColor.getAlpha() == 0) && (releasedPixelColor.getAlpha() == 0);
	}

	protected void action() throws SlickException
	{
		this.behaviour.action();
	}

	public void setStatus(ButtonStatus status)
	{
		this.status = status;
	}

	public ButtonStatus getStatus()
	{
		return this.status;
	}

	public static Button getEmptyButton()
	{
		if(Button.EMPTY == null)
		{
			Button.EMPTY = new Button(ButtonType.MENU, DefaultBehaviour.getEmptyBehaviour(), ElementContainer.getEmptyContainer());
		}

		return Button.EMPTY;
	}

	public ButtonBehaviour getBehaviour()
	{
		return this.behaviour;
	}
}
