package net.squirrel.settings;

public class KeyBindSetting extends Setting{
    public int code;

    public KeyBindSetting(int code) {
        this.name = "Keybind";
        this.code = code;
    }
    public int getKeycode() {
        return code;
    }

    public void setKeycode(int code) {
        this.code = code;
    }


}
