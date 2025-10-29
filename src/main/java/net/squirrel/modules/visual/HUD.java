package net.squirrel.modules.visual;

import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;

public class HUD extends Module {
    public float saved_brightness;
    public HUD() {
        super("HUD", Keyboard.KEY_NONE, Category.VISUAL);
    }

    public void onEnable() {

    }

    public void onDisable() {

    }
}
