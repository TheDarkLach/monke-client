package monke.ui;

import java.util.Collections;
import java.util.Comparator;

import monke.Client;
import monke.events.listeners.EventRenderGUI;
import monke.modules.Module;
import monke.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class HUD
{
	
	public Minecraft mc = Minecraft.getMinecraft();
	
	/* old sorting method
	public static class ModuleComparator implements Comparator<Module>
	{

		@Override
		public int compare(Module arg0, Module arg1) 
		{
			
			if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.name) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.name))
			{
				return -1;
			}
			if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.name) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.name))
			{
				return 1;
			}
			
			return 0;
		}
		
	}*/
	//nvm bruh i need forge for this shit
	private final ResourceLocation watermark = new ResourceLocation("Monke/Capture.PNG");
	
	
	
	public void draw()
	{
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		FontRenderer fr = mc.fontRendererObj;
		
		//Collections.sort(Client.modules, new ModuleComparator());
		
		Client.modules.sort(Comparator.comparingInt(m -> fr.getStringWidth(((Module)m).name)).reversed());
		
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(4, sr.getScaledHeight() - 18, 0);
		GlStateManager.scale(1.55, 1.55, 1);
		GlStateManager.translate(-4, -4, 0);
		fr.drawStringWithShadow(Client.name + " v" + Client.version, 4, 4, -1);
		GlStateManager.popMatrix();
		
		mc.renderEngine.bindTexture(watermark);
		Gui.drawScaledCustomSizeModalRect(90, sr.getScaledHeight() - 30, 0, 0, 25, 25, 25, 25, 25, 25);
		
		int count = 0;
		int count2 = 0;
		
		for(Module m : Client.modules)
		{
			if (!m.toggled || m.name.equals("TabGUI"))
			{
				continue;
			}
			
			double offset = count*(fr.FONT_HEIGHT + 6);
			
			//little white box thing
			Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 10, offset, sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, 6 + fr.FONT_HEIGHT + offset, ColorUtil.getRainbow(4, 0.8f, 1, count * 100));
			//boxes behind actual hack
			Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, 0x90000000);
			fr.drawStringWithShadow(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4, 4 + offset, -1);
			
			count++;
		}
		
		Client.onEvent(new EventRenderGUI());
	}

}
