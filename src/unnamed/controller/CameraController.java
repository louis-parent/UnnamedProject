package unnamed.controller;

import unnamed.view.Camera;

public class CameraController
{
	private Camera camera;

	public CameraController(Camera camera)
	{
		this.camera = camera;
	}

	public void movingUp()
	{
		this.camera.movingUp();
	}

	public void movingDown()
	{
		this.camera.movingDown();
	}

	public void movingLeft()
	{
		this.camera.movingLeft();
	}

	public void movingRight()
	{
		this.camera.movingRight();
	}

	public void stopMovingUp()
	{
		this.camera.stopMovingUp();
	}

	public void stopMovingDown()
	{
		this.camera.stopMovingDown();
	}

	public void stopMovingLeft()
	{
		this.camera.stopMovingLeft();
	}

	public void stopMovingRight()
	{
		this.camera.stopMovingRight();
	}

	public void zoom()
	{
		this.camera.zoom();
	}

	public void unzoom()
	{
		this.camera.unzoom();
	}

	public void reset()
	{
		this.camera.setToOrigin();
	}

	public void mouseWheelDragged(int oldx, int oldy, int newx, int newy)
	{
		this.camera.moveFromPointTo(oldx, oldy, newx, newy);
	}

	public void zoom(int times)
	{
		for(int i = 0; i < times; i++)
		{
			this.zoom();
		}
	}
}
