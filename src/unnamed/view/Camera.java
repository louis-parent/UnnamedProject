package unnamed.view;

import org.newdawn.slick.Graphics;

import unnamed.controller.GameController;

public class Camera
{

	private static final double ZOOM_SPEED = 0.1;

	private static final int STANDARD_CAMERA_SPEED = 20;

	private float offsetX;
	private float offsetY;

	private int horizontalSpeedMultiplicator;
	private int verticalSpeedMultiplicator;

	private float zoomMultiplicator;

	public Camera()
	{
		this.setToOrigin();
	}

	public void update()
	{
		this.offsetXBy((STANDARD_CAMERA_SPEED * this.horizontalSpeedMultiplicator) / this.zoomMultiplicator);
		this.offsetYBy((STANDARD_CAMERA_SPEED * this.verticalSpeedMultiplicator) / this.zoomMultiplicator);
	}

	public void render(Graphics g)
	{
		g.scale(this.zoomMultiplicator, this.zoomMultiplicator);
		g.translate(this.offsetX, this.offsetY);
	}

	private void offsetXBy(float i)
	{
		float sum = this.offsetX + i;
		int mapWidth = GameController.getInstance().getMapWidth();
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
		int mapHeight = GameController.getInstance().getMapHeight();
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
		return zoomMultiplicator;
	}

	public void zoom()
	{
		this.zoomMultiplicator += ZOOM_SPEED;
		this.zoomMultiplicator = this.zoomMultiplicator > 5 ? 5 : this.zoomMultiplicator;
	}

	public void unzoom()
	{
		this.zoomMultiplicator -= ZOOM_SPEED;
		this.zoomMultiplicator = this.zoomMultiplicator < 1 ? 1 : this.zoomMultiplicator;
	}

	public void setToOrigin()
	{
		this.offsetX = 0;
		this.offsetY = 0;

		this.horizontalSpeedMultiplicator = 0;
		this.verticalSpeedMultiplicator = 0;

		this.zoomMultiplicator = 1;
	}
}
