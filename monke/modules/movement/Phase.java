package monke.modules.movement;

import monke.events.Event;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;
import monke.settings.NumberSetting;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;


    public class Phase extends Module
    {

      //  public NumberSetting phase = new NumberSetting("sneaking", 1, 1, 2, 1);

        public Phase()
        {
            super("phase", Keyboard.KEY_NONE, Category.MOVEMENT);
            //this.addSettings(phase);
        }
        @Override
        public void onEvent(Event event)
        {
            if (event instanceof EventUpdate)
            {
                doAAC1910();
            }
        }

        private void doAAC1910() {
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 2.0D, mc.thePlayer.posZ);
            mc.thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive(100));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY - 2.0D, mc.thePlayer.posZ, true));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY - 2.0D, mc.thePlayer.posZ, false));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY - 2.0D, mc.thePlayer.posZ, true));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY - 2.0D, mc.thePlayer.posZ, false));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY - 2.0D, mc.thePlayer.posZ, true));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY - 2.0D, mc.thePlayer.posZ, false));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY - 2.0D, mc.thePlayer.posZ, true));
        }
    }
