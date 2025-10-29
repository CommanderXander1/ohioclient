package net.squirrel.modules.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.squirrel.Client;
import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventRenderGUI;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.ColorUtil;
import net.squirrel.modules.util.DistanceComparator;
import net.squirrel.settings.BooleanSetting;
import net.squirrel.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TargetHUD extends Module {
    public NumberSetting range = new NumberSetting("range", 3, 1, 6, 1);
    public NumberSetting x = new NumberSetting("x", new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), 0, new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() * 2, 1);
    public NumberSetting y = new NumberSetting("y", new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), 0, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() * 2, 1);
    public BooleanSetting onlyPlayer = new BooleanSetting("Players only", true);
    public List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
    public TargetHUD() {
        super("TargetHUD", Keyboard.KEY_NONE, Category.VISUAL);
        this.addSettings(x, y, onlyPlayer);
    }

    public void onEvent(Event event) {
        if(event instanceof EventRenderGUI) {
            targets.clear();
            FontRenderer fr = mc.fontRendererObj;
            ArrayList<EntityLivingBase> possible = new ArrayList<EntityLivingBase>();
            for(Object o : mc.theWorld.loadedEntityList) {
                if(o.equals(mc.thePlayer))
                    continue;
                if(onlyPlayer.isEnabled() && !(o instanceof EntityPlayer))
                    continue;
                if(o instanceof EntityLivingBase && ((EntityLivingBase) o).getDistanceToEntity(mc.thePlayer) <= range.getValue()) {
                    possible.add((EntityLivingBase) o);
                }
            }

            possible.sort(new DistanceComparator());
            for(int i = 0; i < 3; i++) {
                if(possible.size() >= i + 1) {
                    targets.add(possible.get(i));
                }
            }

            for(int i = 0; i < targets.size(); i++) {
                boolean column = i > 1;
                boolean row = i % 2 == 1;
                double x2 = x.getValue() + (column ? 130 : 0);
                double y2 = y.getValue() + (row ? 60 : 0);
                Gui.drawRect(x2, y2, x2 + 120, y2 + 36, 0xcc000000);
                fr.drawStringWithShadow(targets.get(i).getName(), x2 + 5, y2 + 5, -1);
                fr.drawStringWithShadow("§c❤ §f" + new DecimalFormat("#.#").format(targets.get(i).getHealth()), x2 + 5 + fr.getStringWidth(targets.get(i).getName()) + 5, y2 + 5, -1);
                // background for dynamic healthbar
                Gui.drawRect(x2 + 5, y2 + fr.FONT_HEIGHT + 10, x2 + 5 + 110, y2 + fr.FONT_HEIGHT + 10 + 12, 0xff000000);
                // dynamic healthbar
                Gui.drawRect(x2 + 5, y2 + fr.FONT_HEIGHT + 10, x2 + 5 + (110 * (targets.get(i).getHealth()/targets.get(i).getMaxHealth())), y2 + fr.FONT_HEIGHT + 10 + 12, 0xffff0000);

            }
        }
    }
}
