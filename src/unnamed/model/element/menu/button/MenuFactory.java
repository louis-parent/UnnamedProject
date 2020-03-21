package unnamed.model.element.menu.button;

import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.MenuElement;
import unnamed.model.element.menu.button.main.LoadButton;
import unnamed.model.element.menu.button.main.NewButton;
import unnamed.model.element.menu.button.main.QuitButton;
import unnamed.model.element.menu.button.pause.MenuButton;
import unnamed.model.element.menu.button.pause.ResumeButton;
import unnamed.model.element.menu.button.pause.SaveButton;
import unnamed.model.element.menu.button.seed.StartButton;
import unnamed.model.element.menu.field.Field;
import unnamed.model.element.menu.field.SeedField;

public class MenuFactory
{
	public static final String NEW_BUTTON = "new";
	public static final String LOAD_BUTTON = "load";
	public static final String QUIT_BUTTON = "quit";
	public static final String MENU_BUTTON = "menu";
	public static final String SAVE_BUTTON = "save";
	public static final String RESUME_BUTTON = "resume";
	public static final String START_BUTTON = "start";
	
	public static final String SEED_FIELD = "seed";
	
	public static void init() throws SlickException
	{
		NewButton.init();
		LoadButton.init();
		QuitButton.init();
		MenuButton.init();
		SaveButton.init();
		ResumeButton.init();
		StartButton.init();
		Field.init();
	}
	
	public static MenuElement create(String type, ElementContainer container)
	{
		switch(type)
		{
			case NEW_BUTTON:
				return new NewButton(container);
				
			case LOAD_BUTTON:
				return new LoadButton(container);
				
			case QUIT_BUTTON:
				return new QuitButton(container);
				
			case MENU_BUTTON:
				return new MenuButton(container);
				
			case SAVE_BUTTON:
				return new SaveButton(container);
				
			case RESUME_BUTTON:
				return new ResumeButton(container);
				
			case START_BUTTON:
				return new StartButton(Field.EMPTY, container);
				
			case SEED_FIELD:
				return new SeedField(container);
				
			default:
				return MenuElement.EMPTY;
		}
	}
}
