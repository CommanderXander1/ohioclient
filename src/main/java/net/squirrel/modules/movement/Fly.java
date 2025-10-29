package net.squirrel.modules.movement;

import net.squirrel.events.Event;
import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;
import net.squirrel.events.listeners.EventUpdate;

public class Fly extends Module {
    public Fly() {
        super("Fly", Keyboard.KEY_G, Category.MOVEMENT);
    }

    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
    }
    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            if(event.isPre()) {
                mc.thePlayer.capabilities.isFlying = true;
            }
        }
    }
}
