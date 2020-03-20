package unnamed.model.element.button;

import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;
import unnamed.model.element.button.main.PlayButton;
import unnamed.model.element.button.main.QuitButton;
import unnamed.model.element.button.pause.MenuButton;
import unnamed.model.element.button.pause.ResumeButton;
import unnamed.model.element.button.pause.SaveButton;

public class ButtonFactory
{
	public static final String PLAY_BUTTON = "play";
	public static final String QUIT_BUTTON = "quit";
	public static final String MENU_BUTTON = "menu";
	public static final String SAVE_BUTTON = "save";
	public static final String RESUME_BUTTON = "resume";
	
	public static void init() throws SlickException
	{
		PlayButton.init();
		QuitButton.init();
		MenuButton.init();
		SaveButton.init();
		ResumeButton.init();
	}
	
	public static Button create(String type, ElementContainer container)
	{
		switch(type)
		{
			case PLAY_BUTTON:
				return new PlayButton(container);
				
			case QUIT_BUTTON:
				return new QuitButton(container);
				
			case MENU_BUTTON:
				return new MenuButton(container);
				
			case SAVE_BUTTON:
				return new SaveButton(container);
				
			case RESUME_BUTTON:
				return new ResumeButton(container);
				
			default:
				return Button.EMPTY;
		}
	}
}
