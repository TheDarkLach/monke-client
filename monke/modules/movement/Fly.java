package monke.modules.movement;

import org.lwjgl.input.Keyboard;

import monke.events.Event;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;

public class Fly extends Module
{
	public Fly()
	{
		super("Fly", Keyboard.KEY_G	, Category.MOVEMENT);
	}
	
	public void onDisable()
	{
		mc.thePlayer.capabilities.isFlying = false;
	}
	
	public void onEvent(Event e)
	{
		if(e instanceof EventUpdate)
		{
			if(e.isPre())
			{
				//continue n shi
				mc.thePlayer.capabilities.isFlying = true;
			}
		}
	}
}
