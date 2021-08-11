package monke.modules.movement;

import org.lwjgl.input.Keyboard;

import monke.events.Event;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;

public class Bunny extends Module
{
	public Bunny()
	{
		super("Bunny", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	
	public void onEnable()
	{

	}
	public void onDisable()
	{
		mc.thePlayer.speedInAir=0.02f;
		//mc.thePlayer.speedOnGround = 0.1f;
	}
	
	public void onEvent(Event e)
	{
		if(e instanceof EventUpdate)
		{
			if(e.isPre())
			{
				//continue n shi
				if(!mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking())
				{
					mc.thePlayer.speedInAir=0.02f*10;
					if(mc.thePlayer.onGround)
						mc.thePlayer.jump();
					
					//mc.thePlayer.speedOnGround=0.5f;
				}
			}
		}
	}
}

