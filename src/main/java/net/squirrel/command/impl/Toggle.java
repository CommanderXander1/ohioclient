package net.squirrel.command.impl;

import net.squirrel.Client;
import net.squirrel.command.Command;
import net.squirrel.modules.Module;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", "Toggles a module", "toggle <name>", "t");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if(args.length > 0) {
            String moduleName = args[0];

            boolean foundModule = false;

            for(Module module : Client.modules) {
                if(module.name.equalsIgnoreCase(moduleName)) {
                    module.toggle();

                    Client.addChatMessage((module.isToggled() ? "Enabled" : "Disabled") + " " + module.name);

                    foundModule = true;
                    break;
                }
            }
            if(!foundModule) {
                Client.addChatMessage("Could not find module");
            }
        }
    }
}
