package monke.modules.movement;

import org.lwjgl.input.Keyboard;

import monke.events.Event;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;
import monke.modules.Module.Category;
import monke.settings.NumberSetting;

public class Freeze extends Module
{
	
	public Freeze()
	{
		super("Freeze", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	
	public void onEnable()
	{
		//mc.thePlayer.isDead = true;
		//mc.thePlayer.rotationYaw = mc.thePlayer.cameraYaw;
		//mc.thePlayer.rotationPitch = mc.thePlayer.cameraPitch;
	}
	public void onDisable()
	{
		mc.thePlayer.isDead = false;
	}
	
	public void onEvent(Event e)
	{
		if(e instanceof EventUpdate)
		{
			if(e.isPre())
			{
				mc.thePlayer.isDead = true;
				//mc.thePlayer.rotationYaw = mc.thePlayer.cameraYaw;
				//mc.thePlayer.rotationPitch = mc.thePlayer.cameraPitch;
			}
		}
	}

}
