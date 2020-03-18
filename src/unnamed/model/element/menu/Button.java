package unnamed.model.element.menu;

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

	protected abstract void action();
}
