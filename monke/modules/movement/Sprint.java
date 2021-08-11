package monke.modules.movement;

import org.lwjgl.input.Keyboard;

import monke.events.Event;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;

public class Sprint extends Module
{
	public Sprint()
	{
		super("Sprint", Keyboard.KEY_N, Category.MOVEMENT);
	}
	
	public void onEnable()
	{

	}
	public void onDisable()
	{
		mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.getIsKeyPressed());
	}
	
	public void onEvent(Event e)
	{
		if(e instanceof EventUpdate)
		{
			if(e.isPre())
			{
				//continue n shi
				if(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally)
				{
					mc.thePlayer.setSprinting(true);
				}
			}
		}
	}
}
