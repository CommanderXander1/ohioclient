package net.squirrel.modules.movement;

import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.Timer;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    Timer timer = new Timer();
    public Speed() {
        super("Speed", Keyboard.KEY_X, Category.MOVEMENT);
    }

    public void onEnable() {

    }

    public void onDisable() {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isKeyDown());
    }

    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            if(event.isPre()) {
                if(mc.thePlayer.onGround) {
                    if(timer.hasTimeElapsed(100, true)) {
                        mc.thePlayer.jump();
                    }
                }
                if(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally) {
                    mc.thePlayer.setSprinting(true);
                }

            }
        }

    }


}
