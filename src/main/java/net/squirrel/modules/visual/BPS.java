package net.squirrel.modules.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventRenderGUI;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.ColorUtil;
import net.squirrel.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;

public class BPS extends Module {
    public NumberSetting x = new NumberSetting("x", 0, 0, new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() * 2, 1);
    public NumberSetting y = new NumberSetting("y", 0, 0, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() * 2, 1);
    public BPS() {
        super("BPS", Keyboard.KEY_NONE, Category.VISUAL);
        this.addSettings(x, y);
    }

    public void onEvent(Event event) {
        if(event instanceof EventRenderGUI) {
            double dx = Math.abs(mc.thePlayer.posX - mc.thePlayer.lastTickPosX);
            double dz = Math.abs(mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ);
            mc.fontRendererObj.drawStringWithShadow(new DecimalFormat("#.#").format(Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2))), x.getValue(), y.getValue(), ColorUtil.getRainbow(8, 0.6f, 1f));
        }
    }
}
