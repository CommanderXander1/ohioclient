package net.squirrel.modules.movement;

import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Keyboard.KEY_I, Category.MOVEMENT);
    }

    public void onEnable() {

    }

    public void onDisable() {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isKeyDown());
    }

    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            if(event.isPre()) {
                if(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally && !(mc.thePlayer.hurtTime > 0)) {
                    mc.thePlayer.setSprinting(true);
                }

            }
        }

    }


}
