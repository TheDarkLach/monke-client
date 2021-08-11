package monke.modules.movement;

import org.lwjgl.input.Keyboard;

import monke.events.Event;
import monke.events.listeners.EventKey;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;
import monke.settings.NumberSetting;

public class Jump extends Module
{
	
	public NumberSetting Jump = new NumberSetting("Jump", 2, 1, 10, 1);
	public Jump()
	{
		super("Jump", Keyboard.KEY_NONE, Category.MOVEMENT);
		this.addSettings(Jump);
	}
	
	public void onEnable()
	{

	}
	public void onDisable()
	{
		
	}
	
	public void onEvent(Event e)
	{
		/*if(e instanceof EventUpdate)
		{
			if(e.isPre())
			{
					if(Keyboard.KEY_SPACE == this.mc.gameSettings.keyBindJump.getKeyCode())
						mc.thePlayer.motionY += Jump.getValue();
					mc.thePlayer.velocityChanged = true;
					//mc.thePlayer.addVelocity(0.0D, 1.0D, 0.0D);
					//mc.thePlayer.speedOnGround=3;
			}
		}*/
		if(e instanceof EventKey)
		{
			int code = ((EventKey)e).code;
			
			if(code == Keyboard.KEY_SPACE)
			{
				System.out.println("Jump");
				mc.thePlayer.jump();
				//mc.thePlayer.motionY += Jump.getValue();
				mc.thePlayer.addVelocity(0.0D, Jump.getValue(), 0.0D);
				//mc.thePlayer.setVelocity(mc.thePlayer.motionX, mc.thePlayer.motionY + Jump.getValue(), mc.thePlayer.motionZ);
				mc.thePlayer.velocityChanged = true;
			}
		}
	}
}
