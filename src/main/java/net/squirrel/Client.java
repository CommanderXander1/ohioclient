package net.squirrel;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.squirrel.command.Command;
import net.squirrel.command.CommandManager;
import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventChat;
import net.squirrel.events.listeners.EventKey;
import net.squirrel.modules.Module;
import net.squirrel.modules.combat.Killaura;
import net.squirrel.modules.misc.AutoRegister;
import net.squirrel.modules.misc.HiKevin;
import net.squirrel.modules.movement.*;
import net.squirrel.modules.visual.*;
import net.squirrel.modules.player.*;
import net.squirrel.ui.HUD;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {
    public static String name = "Ohio";
    public static String version = "1";
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public static HUD hud = new HUD();
    public static CommandManager commandManager = new CommandManager();

    public static void start() {
        Display.setTitle(name + " v" + version);

        modules.add(new Fly());
        modules.add(new Sprint());
        modules.add(new Fullbright());
        modules.add(new Nofall());
        modules.add(new TabGUI());
        modules.add(new HiKevin());
        modules.add(new Killaura());
        modules.add(new Speed());
        modules.add(new ESP());
        modules.add(new ClickGUI());
        modules.add(new AutoRegister());
        modules.add(new BPS());
        modules.add(new TargetHUD());
        modules.add(new ChestStealer());
        modules.add(new InventoryManager());
    }

    public static void onEvent(Event event) {
        if(event instanceof EventChat) {
            commandManager.handleChat((EventChat) event);
        }

        for(Module module : modules) {
            if(!module.isToggled()) {
                continue;
            }
            module.onEvent(event);
        }
    }

    public static void keyPress(int key) {
        Client.onEvent(new EventKey(key));

        for (Module module : modules) {
            if (module.getKey() == key) {
                module.toggle();
            }
        }
    }

    public static List<Module> getModulesByCategory(Module.Category category) {
        List<Module> modules = new ArrayList<Module>();

        for (Module module : Client.modules) {
            if(module.category == category) {
                modules.add(module);
            }
        }

        return modules;
    }

    public static void addChatMessage(String message) {
        message = "\2479" + "[" + "\2474" + name + "\2479" + "] " + "\2477" + message;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

}
