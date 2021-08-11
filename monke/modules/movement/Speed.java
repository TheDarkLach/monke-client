package monke.modules.movement;

import org.lwjgl.input.Keyboard;

import monke.events.Event;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;
import monke.settings.NumberSetting;

public class Speed extends Module
{
	
	public NumberSetting speed = new NumberSetting("Speed", 2, 1, 10, 1);
	public Speed()
	{
		super("Speed", Keyboard.KEY_NONE, Category.MOVEMENT);
		this.addSettings(speed);
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
				if(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally)
				{
					mc.thePlayer.speedInAir=(float) (0.02f*speed.getValue());
					//mc.thePlayer.speedOnGround=3;
				}
			}
		}
	}
}
