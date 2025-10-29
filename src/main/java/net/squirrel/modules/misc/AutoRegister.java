package net.squirrel.modules.misc;

import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.Timer;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class AutoRegister extends Module {
    public boolean sent;
    Timer timer = new Timer();
    public AutoRegister() {
        super("Auto Register", Keyboard.KEY_NONE, Category.MISC);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            List<String> msgs = mc.ingameGUI.getChatGUI().getSentMessages();
            if(!msgs.isEmpty() && (msgs.get(msgs.size() - 1).toLowerCase().contains("/register") || msgs.get(msgs.size() - 1).toLowerCase().contains("/registrar"))) {
                mc.thePlayer.sendChatMessage("/register 123456 123456");
                sent = true;
            }
            if(!msgs.isEmpty() && (msgs.get(msgs.size() - 1).toLowerCase().contains("/login"))) {
                mc.thePlayer.sendChatMessage("/login 123456");
                sent = true;
            }
            if(timer.hasTimeElapsed(1200, true)) {
                sent = false;
            }
        }
    }

}
