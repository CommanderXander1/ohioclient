package net.squirrel.command.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.squirrel.Client;
import net.squirrel.command.Command;
import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;

public class Say extends Command {
    public Say() {
        super("Say", "Says a message", "say", "s");
    }

    @Override
    public void onCommand(String[] args, String command) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.join(" ", args)));
    }
}
