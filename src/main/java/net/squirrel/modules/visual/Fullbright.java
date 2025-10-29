package net.squirrel.modules.visual;

import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {

    public float saved_brightness;
    public Fullbright() {
        super("Fullbright", Keyboard.KEY_O, Category.VISUAL);
    }

    public void onEnable() {
        saved_brightness = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100;
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = saved_brightness;
    }
}
