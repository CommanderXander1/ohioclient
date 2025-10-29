package net.squirrel.modules.misc;

import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.Timer;
import org.lwjgl.input.Keyboard;

public class HiKevin extends Module {
    public boolean sent;
    Timer timer = new Timer();
    public HiKevin() {
        super("HiKevin", Keyboard.KEY_L, Category.MISC);
        this.sent = false;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            if(event.isPre()) {

                if(!mc.ingameGUI.getChatGUI().getSentMessages().isEmpty() && mc.ingameGUI.getChatGUI().getSentMessages().get(mc.ingameGUI.getChatGUI().getSentMessages().size() - 1).toLowerCase().contains("hi xander") && !sent) {
                    mc.thePlayer.sendChatMessage("hi kevin");
                    mc.ingameGUI.getChatGUI().addToSentMessages("hi kevin");
                    sent = true;
                    timer.reset();
                }
                if(sent && timer.hasTimeElapsed(1200, true)) {
                    sent = false;
                }
            }
        }

    }


}
