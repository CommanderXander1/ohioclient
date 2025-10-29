package net.squirrel.command.impl;

import net.squirrel.Client;
import net.squirrel.command.Command;
import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;

public class Info extends Command {
    public Info() {
        super("Info", "Displays client information", "info", "i");
    }

    @Override
    public void onCommand(String[] args, String command) {
        Client.addChatMessage("v" + Client.version + " by Xander");
    }
}
