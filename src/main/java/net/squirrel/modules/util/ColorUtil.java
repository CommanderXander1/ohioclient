package net.squirrel.modules.util;

import java.awt.*;

public class ColorUtil {
    public static int getRainbow(int seconds, float saturation, float brightness) {
        float hue = (System.currentTimeMillis() % (int) (seconds * 1000)) / (float)(1000f * seconds);
        return (int) Color.HSBtoRGB(hue, saturation, brightness);
    }
    public static int getRainbow(int seconds, float saturation, float brightness, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int) (seconds * 1000)) / (float)(1000f * seconds);
        return (int) Color.HSBtoRGB(hue, saturation, brightness);
    }
}
