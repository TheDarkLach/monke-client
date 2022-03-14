package monke.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class SplashProgress
{
    private static final int MAX = 9;
    private static int PROGRESS = 0;
    private static String CURRENT = "";
    private static ResourceLocation splash;
    private static UnicodeFontRenderer ufr;

    public static void update()
    {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null)
        {
            return;
        }
        drawSplash(Minecraft.getMinecraft().getTextureManager());
    }

    public static void setProgress(int givenProgress, String givenText)
    {
        PROGRESS = givenProgress;
        CURRENT = givenText;
        update();
    }

    public static void drawSplash(TextureManager tm)
    {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        int scaleFactor = scaledResolution.getScaleFactor();

        Framebuffer framebuffer = new Framebuffer(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor,true);
        framebuffer.bindFramebuffer(false);

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D,(double)scaledResolution.getScaledWidth(),(double)scaledResolution.getScaledHeight(),0.0D,1000.0D,3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F,0.0F,-2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.func_179098_w();

        if (splash == null)
        {
            splash = new ResourceLocation("Monke/splash.png");
        }

        tm.bindTexture(splash);
        GlStateManager.func_179117_G();
        GlStateManager.color(1.0F,1.0F,1.0F,1.0F);
        Gui.drawScaledCustomSizeModalRect(0,0,0,0,1920,1080,scaledResolution.getScaledWidth(),scaledResolution.getScaledHeight(),1920,1080);
        drawProgress();
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516,0.1F);

        Display.update();

    }

    private static void drawProgress()
    {
        if(Minecraft.getMinecraft().gameSettings == null || Minecraft.getMinecraft().getTextureManager() == null)
        {
            return;
        }

        if (ufr == null)
        {
            ufr = UnicodeFontRenderer.getFontOnPC("Arial",25);
        }

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);

        double nProgress = (double)PROGRESS;
        double calc = (nProgress / MAX) * sr.getScaledWidth();

        Gui.drawRect(0, sr.getScaledHeight() - 35, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0,0,0,50).getRGB());

        GlStateManager.func_179117_G();
        resetTextureState();

        ufr.drawString(CURRENT, 20, sr.getScaledHeight() - 25, Color.WHITE.getRGB());

        String step = PROGRESS + "/" + MAX;
        ufr.drawString(step, sr.getScaledWidth() - 20 - ufr.getStringWidth(step), sr.getScaledHeight() - 25, Color.WHITE.getRGB());

        GlStateManager.func_179117_G();
        resetTextureState();

        Gui.drawRect(0, sr.getScaledHeight() - 2, (int)calc, sr.getScaledHeight(), new Color(149,201,144).getRGB());

        Gui.drawRect(0, sr.getScaledHeight() - 2, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0,0,0,10).getRGB());
    }

    //clearing memory
    private static void resetTextureState()
    {
        GlStateManager.field_179174_p[GlStateManager.field_179162_o].field_179059_b = -1;
    }

}
