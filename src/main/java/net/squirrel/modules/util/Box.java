package net.squirrel.modules.util;

import net.squirrel.modules.Module;
import net.squirrel.settings.Setting;

public class Box {
    public int x1, y1, x2, y2, color;
    public String name;
    public Module.Category category;
    public Module module;
    public Setting setting;
    public Box(int x1, int y1, int width, int height, int color, String name) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x1 + width;
        this.y2 = y1 + height;
        this.color = color;
        this.name = name;
    }

    public void update(int width, int height) {
        x2 = x1 + width;
        y2 = y1 + height;
    }
}
