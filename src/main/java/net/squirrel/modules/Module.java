package net.squirrel.modules;

import net.minecraft.client.Minecraft;
import net.squirrel.Client;
import net.squirrel.events.Event;
import net.squirrel.settings.KeyBindSetting;
import net.squirrel.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Module {
    public String name;
    public boolean toggled;
    public KeyBindSetting keyCode = new KeyBindSetting(0);
    public Category category;
    public int index;
    public Minecraft mc = Minecraft.getMinecraft();
    public boolean expanded;
    public List<Setting> settings = new ArrayList<Setting>();


    public Module(String name, int key, Category category) {
        this.name = name;
        this.keyCode.setKeycode(key);
        this.category = category;
        this.addSettings(this.keyCode);
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
    }

    public boolean isToggled() {
        return toggled;
    }

    public int getKey() {
        return this.keyCode.getKeycode();
    }

    public void onEvent(Event event) {

    }

    public void toggle() {
        toggled = !toggled;
        if(toggled)
        {
            onEnable();
        } else {
            onDisable();
        }
        System.out.println("[" + Client.name + "] " + name + " toggled.");
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public enum Category {
        COMBAT("Combat"),
        PLAYER("Player"),
        MOVEMENT("Movement"),

        VISUAL("Visual"),
        MISC("Misc");

        public final String name;
        public int moduleIndex;

        Category(String name) {
            this.name = name;
        }
    }
}
