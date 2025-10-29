package net.squirrel.modules.visual;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.squirrel.events.Event;
import net.squirrel.modules.Module;
import net.squirrel.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

import java.util.stream.Collectors;

public class Animations extends Module {
    public ModeSetting mode;
    public Animations() {
        super("Animations", Keyboard.KEY_NONE, Category.VISUAL);
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onEvent(Event event) {

    }
}
