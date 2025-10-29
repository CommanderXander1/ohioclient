package net.squirrel.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.squirrel.Client;
import net.squirrel.events.listeners.EventRenderGUI;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.ColorUtil;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;
import java.util.Comparator;

public class HUD {
    public boolean arrayListBackground = true;
    public boolean arrayListSideBar = true;
    public boolean arrayListRainbow = true;
    public boolean coordinates = true;
    public boolean clientVersion = false;
    public Minecraft mc = Minecraft.getMinecraft();
//    public int arrayListColor;
    public void draw() {
//        if(arrayListRainbow) {
//            arrayListColor = ColorUtil.getRainbow(4, 0.6f, 1f);
//        } else {
//            arrayListColor = -1;
//        }

        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        Client.modules.sort(Comparator.comparingInt(m ->
                fr.getStringWidth(((Module)m).name + " [" + Keyboard.getKeyName(((Module)m).getKey()) + "]")).reversed()
        );

        GlStateManager.pushMatrix();
        GlStateManager.translate(4, 4, 0);
        GlStateManager.scale(2, 2, 1);
        GlStateManager.translate(-4, -4, 0);
        fr.drawStringWithShadow(clientVersion ? Client.name + " v" + Client.version : Client.name, 4, 4, ColorUtil.getRainbow(8, 0.6f, 1f));
        GlStateManager.popMatrix();

        int count = 0;

        for (Module module : Client.modules) {
            if (module.isToggled()) {
                double offset = count * (fr.FONT_HEIGHT + 2) + 4;
                String combined_name = module.name.toLowerCase() + " [" + Keyboard.getKeyName(module.keyCode.getKeycode()).toLowerCase() + "]";
                if(arrayListBackground) {
                    if(arrayListSideBar) {
                        Gui.drawRect(sr.getScaledWidth() - 6, offset + 2, sr.getScaledWidth() - 4, 6 + fr.FONT_HEIGHT + offset - 2, ColorUtil.getRainbow(8, 0.6f, 1f, count * 150L));
                    }
                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(combined_name) - 10, offset + 2, sr.getScaledWidth() - 4, 6 + fr.FONT_HEIGHT + offset - 2, 0x70000000);
                }

                fr.drawStringWithShadow(combined_name, sr.getScaledWidth() - fr.getStringWidth(combined_name) - 8, 4 + offset, ColorUtil.getRainbow(8, 0.6f, 1f, count * 150L));
                count++;
            }
        }

        Client.onEvent(new EventRenderGUI());

        // info in corner
        if(coordinates) {
            fr.drawStringWithShadow("X: " + new DecimalFormat("#.##").format(mc.thePlayer.posX), 4, sr.getScaledHeight() - 4 - (fr.FONT_HEIGHT * 3), ColorUtil.getRainbow(8, 0.6f, 1f));
            fr.drawStringWithShadow("Y: " + new DecimalFormat("#.##").format(mc.thePlayer.posY), 4, sr.getScaledHeight() - 4 - (fr.FONT_HEIGHT * 2), ColorUtil.getRainbow(8, 0.6f, 1f));
            fr.drawStringWithShadow("Z: " + new DecimalFormat("#.##").format(mc.thePlayer.posZ), 4, sr.getScaledHeight() - 4 - (fr.FONT_HEIGHT), ColorUtil.getRainbow(8, 0.6f, 1f));
        }

    }
}
