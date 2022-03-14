package monke.modules.movement;

import monke.events.Event;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;
import monke.settings.NumberSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class Timer extends Module
{
    public NumberSetting Timer = new NumberSetting("Speed", 2, 1, 10, 1);

    public Timer()
    {
        super("Timer", Keyboard.KEY_NONE, Category.MOVEMENT);
        this.addSettings(Timer);
    }
    

    public void onEnable()
    {

    }
    public void onDisable()
    {
        mc.timer.timerSpeed = 20.0f;

    }

    public void onEvent(Event e)
    {
        if(e instanceof EventUpdate)
        {
            if(e.isPre())
            {
                mc.timer.timerSpeed = (float) Timer.getValue();
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            }
        }
    }
}
