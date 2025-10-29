package net.squirrel.command.impl;

import net.squirrel.Client;
import net.squirrel.command.Command;
import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    public Bind() {
        super("Bind", "Binds a module", "bind <name> <key> | clear", "b");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if(args.length == 2) {
            String moduleName = args[0];
            String keyName = args[1];

            boolean foundModule = false;

            for(Module module : Client.modules) {
                if(module.name.equalsIgnoreCase(moduleName)) {
                    foundModule = true;
                    module.keyCode.setKeycode(Keyboard.getKeyIndex(keyName.toUpperCase()));
                    Client.addChatMessage(String.format("Bound %s to %s", module.name, Keyboard.getKeyName(module.getKey())));
                    break;
                }
            }
            if(!foundModule) {
                Client.addChatMessage("Could not find module");
            }
        }
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("clear")) {
                for(Module module : Client.modules) {
                    module.keyCode.setKeycode(Keyboard.KEY_NONE);
                }
                Client.addChatMessage("Cleared all binds");
            }
        }
    }
}
