package unnamed.view;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import unnamed.controller.GameController;
import unnamed.model.element.Element;

public class Camera
{

	private static final float ZOOM_SPEED = 0.1f;

	private static final float STANDARD_CAMERA_SPEED = 0.6f;

	private float offsetX;
	private float offsetY;

	private int horizontalSpeedMultiplicator;
	private int verticalSpeedMultiplicator;

	private float zoomMultiplicator;

	public Camera()
	{
		this.setToOrigin();
	}

	public void update(int delta)
	{
		this.offsetXBy(((Camera.STANDARD_CAMERA_SPEED * this.horizontalSpeedMultiplicator) / this.zoomMultiplicator) * delta);
		this.offsetYBy(((Camera.STANDARD_CAMERA_SPEED * this.verticalSpeedMultiplicator) / this.zoomMultiplicator) * delta);
	}

	public void render(Graphics g)
	{
		g.scale(this.zoomMultiplicator, this.zoomMultiplicator);
		g.translate(this.offsetX, this.offsetY);
		g.flush();
	}

	private void offsetXBy(float i)
	{
		float sum = this.offsetX + i;
		int mapWidth = GameController.getInstance().getWidth();
		float maxWidth = -(mapWidth - (GameController.GAME_WIDTH / this.zoomMultiplicator));

		if(sum > 0)
		{
			this.offsetX = 0;
		}
		else if(sum < maxWidth)
		{
			this.offsetX = maxWidth;
		}
		else
		{
			this.offsetX = sum;
		}
	}

	private void offsetYBy(float i)
	{
		float sum = this.offsetY + i;
		int mapHeight = GameController.getInstance().getHeight();
		float maxHeight = -(mapHeight - (GameController.GAME_HEIGHT / this.zoomMultiplicator));

		if(sum > 0)
		{
			this.offsetY = 0;
		}
		else if(sum < maxHeight)
		{
			this.offsetY = maxHeight;
		}
		else
		{
			this.offsetY = sum;
		}
	}

	private void changeHorizontalSpeed(int i)
	{
		this.horizontalSpeedMultiplicator += i;
	}

	private void changeVerticalSpeed(int i)
	{
		this.verticalSpeedMultiplicator += i;
	}

	public void movingUp()
	{
		this.changeVerticalSpeed(1);
	}

	public void movingDown()
	{
		this.changeVerticalSpeed(-1);
	}

	public void movingLeft()
	{
		this.changeHorizontalSpeed(1);
	}

	public void movingRight()
	{
		this.changeHorizontalSpeed(-1);
	}

	public void stopMovingUp()
	{
		this.changeVerticalSpeed(-1);
	}

	public void stopMovingDown()
	{
		this.changeVerticalSpeed(1);
	}

	public void stopMovingLeft()
	{
		this.changeHorizontalSpeed(-1);
	}

	public void stopMovingRight()
	{
		this.changeHorizontalSpeed(1);
	}

	public float getOffsetX()
	{
		return this.offsetX;
	}

	public float getOffsetY()
	{
		return this.offsetY;
	}

	public float getZoomMultiplicator()
	{
		return this.zoomMultiplicator;
	}

	public void zoom()
	{
		this.zoomMultiplicator += Camera.ZOOM_SPEED;

		if(this.zoomMultiplicator > 5)
		{
			this.zoomMultiplicator = 5;
		}
		else
		{
			this.offsetX -= this.getZoomingOffset(1920);
			this.offsetY -= this.getZoomingOffset(1080);
		}
	}

	public void unzoom()
	{
		this.zoomMultiplicator -= Camera.ZOOM_SPEED;

		if(this.zoomMultiplicator < 1)
		{
			this.zoomMultiplicator = 1;
		}
		else
		{
			this.offsetX += this.getUnzoomingOffset(1920);
			this.offsetY += this.getUnzoomingOffset(1080);
		}
	}

	public void setToOrigin()
	{
		this.offsetX = 0;
		this.offsetY = 0;

		this.horizontalSpeedMultiplicator = 0;
		this.verticalSpeedMultiplicator = 0;

		this.zoomMultiplicator = 1;
	}

	private float getZoomingOffset(int value)
	{
		float actualValue = value - (value / this.zoomMultiplicator);
		float previousValue = value - (value / (this.zoomMultiplicator - Camera.ZOOM_SPEED));

		return (actualValue - previousValue) / 2;
	}

	private float getUnzoomingOffset(int value)
	{
		float previousValue = value - (value / (this.zoomMultiplicator + Camera.ZOOM_SPEED));
		float actualValue = value - (value / this.zoomMultiplicator);

		return (previousValue - actualValue) / 2;
	}

	public void moveFromPointTo(float oldx, float oldy, float newx, float newy)
	{
		this.offsetX -= oldx - newx;
		this.offsetY -= oldy - newy;
	}

	public boolean isVisible(Element elem) throws SlickException
	{
		float startX = -this.offsetX;
		float endX = -this.offsetX + (GameController.GAME_WIDTH / this.zoomMultiplicator);

		boolean isInX = startX <= elem.getX() + elem.getWidth() && endX >= elem.getX();

		float startY = -this.offsetY;
		float endY = -this.offsetY + (GameController.GAME_HEIGHT / this.zoomMultiplicator);

		boolean isInY = startY <= elem.getY() + elem.getHeight() && endY >= elem.getY();

		return isInX && isInY;
	}
}
