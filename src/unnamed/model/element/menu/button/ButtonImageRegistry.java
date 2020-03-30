package unnamed.model.element.menu.button;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import unnamed.model.PixelisedImage;

public class ButtonImageRegistry
{
	private static final String BUTTONS_PATH = "assets/menu/";
	private static final String IMAGE_EXTENSION = ".png";
	
	private Map<ButtonType, Map<ButtonStatus, Image>> images;
	
	public ButtonImageRegistry() throws SlickException
	{
		this.images = new HashMap<ButtonType, Map<ButtonStatus, Image>>();
		
		for(ButtonType type : ButtonType.values())
		{
			HashMap<ButtonStatus, Image> statusMap = new HashMap<ButtonStatus, Image>();
			this.images.put(type, statusMap);
			
			for(ButtonStatus status : ButtonStatus.values())
			{
				String fileName = this.getFileName(type, status);
				
				if(new File(fileName).exists())
				{
					statusMap.put(status, new PixelisedImage(fileName));
				}
				else
				{
					statusMap.put(status, new PixelisedImage(this.getFileName(type, ButtonStatus.RELEASED)));
				}
			}
		}
	}
	
	public Image getImageFor(ButtonType type, ButtonStatus status)
	{
		return this.images.get(type).get(status);
	}

	private String getFileName(ButtonType type, ButtonStatus status)
	{
		return BUTTONS_PATH + type.toString() + "_" + status.toString() + IMAGE_EXTENSION;
	}
}
