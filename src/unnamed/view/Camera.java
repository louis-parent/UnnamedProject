package unnamed.view;

import org.newdawn.slick.Graphics;

import unnamed.controller.GameController;

public class Camera
{

	private static final int STANDARD_CAMERA_SPEED = 10;

	private float offsetX;
	private float offsetY;

	private int horizontalSpeedMultiplicator;
	private int verticalSpeedMultiplicator;

	public Camera()
	{
		this.offsetX = 0;
		this.offsetY = 0;

		this.horizontalSpeedMultiplicator = 0;
		this.verticalSpeedMultiplicator = 0;
	}

	public void update()
	{
		this.offsetXBy(STANDARD_CAMERA_SPEED * this.horizontalSpeedMultiplicator);
		this.offsetYBy(STANDARD_CAMERA_SPEED * this.verticalSpeedMultiplicator);
	}

	public void render(Graphics g)
	{
		g.translate(this.offsetX, this.offsetY);
	}

	private void offsetXBy(float i)
	{
		float sum = this.offsetX + i;
		int mapWidth = GameController.getInstance().getMapWidth();
		int maxWidth = -(mapWidth - GameController.GAME_WIDTH);

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
		int maxHeight = -(mapHeight - GameController.GAME_HEIGHT);

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
}
