package unnamed.model.element.menu.button;

import org.newdawn.slick.SlickException;

import unnamed.model.container.ElementContainer;
import unnamed.model.element.menu.MenuElement;
import unnamed.model.element.menu.button.behaviour.ButtonBehaviour;
import unnamed.model.element.menu.button.behaviour.DefaultBehaviour;
import unnamed.model.element.menu.button.behaviour.LoadBehaviour;
import unnamed.model.element.menu.button.behaviour.MenuBehaviour;
import unnamed.model.element.menu.button.behaviour.NewBehaviour;
import unnamed.model.element.menu.button.behaviour.QuitBehaviour;
import unnamed.model.element.menu.button.behaviour.ResumeBehaviour;
import unnamed.model.element.menu.button.behaviour.SaveBehaviour;
import unnamed.model.element.menu.button.behaviour.StartBehaviour;
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
		Button.init();
		Field.init();
	}

	public static MenuElement create(String type, ElementContainer container)
	{
		switch(type)
		{
			case NEW_BUTTON:
				return MenuFactory.createButton(ButtonType.NEW, container);
				
			case LOAD_BUTTON:
				return MenuFactory.createButton(ButtonType.LOAD, container);

			case QUIT_BUTTON:
				return MenuFactory.createButton(ButtonType.QUIT, container);

			case MENU_BUTTON:
				return MenuFactory.createButton(ButtonType.MENU, container);

			case SAVE_BUTTON:
				return MenuFactory.createButton(ButtonType.SAVE, container);

			case RESUME_BUTTON:
				return MenuFactory.createButton(ButtonType.RESUME, container);

			case START_BUTTON:
				return MenuFactory.createButton(ButtonType.START, container);

			case SEED_FIELD:
				return new SeedField(container);

			default:
				return MenuElement.getEmptyMenuElement();
		}
	}
	
	private static Button createButton(ButtonType type, ElementContainer container)
	{
		ButtonBehaviour behaviour = DefaultBehaviour.getEmptyBehaviour();
		
		switch(type)
		{
			case LOAD:
				behaviour = new LoadBehaviour(Button.getEmptyButton());
				break;
				
			case MENU:
				behaviour = new MenuBehaviour(Button.getEmptyButton());
				break;
				
			case NEW:
				behaviour = new NewBehaviour(Button.getEmptyButton());
				break;
				
			case QUIT:
				behaviour = new QuitBehaviour(Button.getEmptyButton());
				break;
				
			case RESUME:
				behaviour = new ResumeBehaviour(Button.getEmptyButton());
				break;
				
			case SAVE:
				behaviour = new SaveBehaviour(Button.getEmptyButton());
				break;
				
			case START:
				behaviour = new StartBehaviour(Field.getEmptyField(), Button.getEmptyButton());
				break;			
		}
		
		Button butt = new Button(type, behaviour, container);
		behaviour.setButton(butt);
		return butt;
	}
}
