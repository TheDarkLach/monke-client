package monke.modules.combat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import monke.events.Event;
import monke.events.listeners.EventMotion;
import monke.modules.Module;
import monke.settings.BooleanSetting;
import monke.settings.ModeSetting;
import monke.settings.NumberSetting;
import monke.util.ColorUtil;
import monke.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C0APacketAnimation;

public class Killaura extends Module
{
	public Timer timer = new Timer();
	public static NumberSetting range = new NumberSetting("Range", 4, 1, 8, 0.1);
	public NumberSetting aps = new NumberSetting("APS", 10, 1, 20, 1);
	public BooleanSetting noSwing = new BooleanSetting("No Swing", false);
	public BooleanSetting headSpin = new BooleanSetting("Head Spin", true);
	//public ModeSetting test = new ModeSetting("Test", "One" ,"one" ,"two" ,"three");
	
	public Killaura()
	{
		super("Killaura", Keyboard.KEY_R, Category.COMBAT);
		this.addSettings(range, aps, noSwing, headSpin); //test);
	}
	//help
	public void onEnable()
	{

	}
	public void onDisable()
	{

	}
	
	public void onEvent(Event e)
	{
		if (e instanceof EventMotion)
		{
			if(e.isPre())
			{
				EventMotion event = (EventMotion)e;
				
				
				List<EntityLivingBase> targets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
				//makes list with all hittable entities within 4 blocks
				targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity != mc.thePlayer && !entity.isDead && entity.getHealth() > 0).collect(Collectors.toList());
				
				targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
				
				//only hits players
				//targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());

				if(!targets.isEmpty())
				{
					//sets target to closest thing
					EntityLivingBase target = targets.get(0);
					
					//kill aura legit, rotates head client side. dont really know how id do server side rotation only
					//mc.thePlayer.rotationYaw = (getRotations(target)[0]);
					//mc.thePlayer.rotationPitch = (getRotations(target)[1]);

					if(headSpin.isEnabled())
					{
						mc.thePlayer.rotationYaw = (getRotations(target)[0]);
						mc.thePlayer.rotationPitch = (getRotations(target)[1]);
					}
					else
					{
						//return;
					}
					
					
					//this 10 would be 10 cps I believe 
					if(timer.hasTimeElapsed((long) (1000/ aps.getValue()), true))
					{	
						if(noSwing.isEnabled())
						{
							mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
						}
						else
						{
							mc.thePlayer.swingItem();
						}
							mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
					}
				}
			}
		}
	}
	
	public float[] getRotations(Entity e)
	{
		double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
			   deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
			   deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
			   distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
		
		float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
			  pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
		
		if(deltaX < 0 && deltaZ < 0)
		{
			yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
			
		}
		else if (deltaX > 0 && deltaZ < 0)
		{
			yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
		}
		return new float[] {yaw, pitch};
	}
}
