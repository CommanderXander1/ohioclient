package net.squirrel.modules.visual;

import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends Module {
    public List<Module.Category> categoryList = new ArrayList<Category>();
    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.VISUAL);
    }

    public void onEnable() {
        //open chat/pause game
//        mc.setIngameNotInFocus();
        mc.displayGuiScreen(new net.squirrel.ui.ClickGUI());
    }

    public void onDisable() {
        mc.setIngameFocus();
    }
}
