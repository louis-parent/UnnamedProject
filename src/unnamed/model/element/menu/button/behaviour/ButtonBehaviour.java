package unnamed.model.element.menu.button.behaviour;

import org.newdawn.slick.SlickException;

import unnamed.model.element.menu.button.Button;

public interface ButtonBehaviour
{	
	public abstract void action() throws SlickException;
	
	public abstract void tickUpdate();
	
	public abstract void setButton(Button button);
}
