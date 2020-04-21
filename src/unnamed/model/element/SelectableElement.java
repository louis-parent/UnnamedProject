package unnamed.model.element;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;
import unnamed.model.container.ElementContainer;

public interface SelectableElement
{
	public static final SelectableElement EMPTY = new EmptySelectableElement();

	public abstract boolean isSelected();

	public abstract void selectEvent();

	public abstract void deselectEvent();

	public abstract void setSelected(boolean toSelect);

	public static SelectableElement getEmptySelectable()
	{
		return SelectableElement.EMPTY;
	}

	public static final class EmptySelectableElement extends Element implements SelectableElement
	{
		public EmptySelectableElement()
		{
			super(ElementContainer.getEmptyContainer());
		}

		private boolean isSelected;

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

		@Override
		public Image getSprite() throws SlickException
		{
			return PixelisedImage.getEmptyPixelisedImage();
		}

		@Override
		public void tickUpdate()
		{
		}

		@Override
		public void clickEvent() throws SlickException
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

	}
}
