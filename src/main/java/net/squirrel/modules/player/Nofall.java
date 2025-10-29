package net.squirrel.modules.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;

public class Nofall extends Module {
    public Nofall() {
        super(
                "NoFall", Keyboard.KEY_U, Category.PLAYER);
    }

    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            if(event.isPre()) {
                if(mc.thePlayer.fallDistance > 2) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                }
            }
        }

    }


}
