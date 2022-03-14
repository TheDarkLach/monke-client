package monke.modules.render;

import org.lwjgl.input.Keyboard;

import monke.events.Event;
import monke.modules.Module;
import monke.modules.Module.Category;
import monke.settings.BooleanSetting;
import monke.settings.NumberSetting;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FreeCam extends Module
{
	private final NumberSetting speedValue = new NumberSetting("Speed", 1, 1, 3, 0.1);
	private final BooleanSetting flyValue = new BooleanSetting("Fly", true);
	private final BooleanSetting noClipValue = new BooleanSetting("NoClip", true);
	
	
	  private EntityOtherPlayerMP fakePlayer = null;
	  private double oldX;
	  private double oldY;
	  private double oldZ;
	    
	public FreeCam()
	{
		super("FreeCam", Keyboard.KEY_NONE, Category.RENDER);
		this.addSettings(speedValue,flyValue,noClipValue); //test);
	}
	
	public void onEnable()
	{
		 if(mc.thePlayer == null)
	            return;
	
        oldX = mc.thePlayer.posX;
        oldY = mc.thePlayer.posY;
        oldZ = mc.thePlayer.posZ;

        fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        fakePlayer.clonePlayer(mc.thePlayer, true);

        fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
        fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);

        mc.theWorld.addEntityToWorld(-1000, fakePlayer);

        if(noClipValue.isEnabled())
        {
            mc.thePlayer.noClip = true;
        }
	}
	
	public void onDisable()
	{
		 if(mc.thePlayer == null || fakePlayer == null)
	            return;

        mc.thePlayer.setPositionAndRotation(oldX, oldY, oldZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        mc.theWorld.removeEntityFromWorld(fakePlayer.getEntityId());
        fakePlayer = null;
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionY = 0;
        mc.thePlayer.motionZ = 0;
	}
	
	 
	 public void onUpdate(Event event) 
	 {
		 if(noClipValue.isEnabled())
         {
             mc.thePlayer.noClip = true;
         }
         mc.thePlayer.fallDistance = 0;

         if(flyValue.isEnabled()) {
             final float value = (float) speedValue.getValue();

             mc.thePlayer.motionY = 0;
             mc.thePlayer.motionX = 0;
             mc.thePlayer.motionZ = 0;
             if(mc.gameSettings.keyBindJump.isPressed())
                mc.thePlayer.motionY += value;
             if(mc.gameSettings.keyBindSneak.isPressed())
                mc.thePlayer.motionY -= value;
             //MovementUtils.strafe(value);
         }
	  }

}
