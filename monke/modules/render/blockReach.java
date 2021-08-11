package monke.modules.render;

import org.lwjgl.input.Keyboard;

import monke.modules.Module.Category;
import monke.settings.NumberSetting;
import monke.events.Event;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;

public class blockReach extends Module
{
	public static NumberSetting blockReach = new NumberSetting("blockReach", 4, 1, 10, 1);
	
	public blockReach()
	{
		super("blockReach", Keyboard.KEY_NONE, Category.RENDER);
		this.addSettings(blockReach);
	}
	
}
