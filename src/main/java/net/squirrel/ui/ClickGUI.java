package net.squirrel.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import net.squirrel.Client;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.Box;
import net.squirrel.settings.*;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ClickGUI extends GuiScreen {
    public List<Module.Category> categories = Arrays.asList(Module.Category.values());
    public List<Box> cboxes = new ArrayList<Box>();
    public List<Box> mboxes = new ArrayList<Box>();
    public List<Box> sboxes = new ArrayList<Box>();
    public ScaledResolution sr;
    public Module.Category selectedC;
    public Module selectedM;
    public Box bg;
    public Box cbg;
    public Box mbg;
    public Box sbg;
    Setting settingInput;
    String num;
    @Override
    public void initGui() {
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
        super.initGui();
        this.buttonList.clear();
        selectedC = null;
        selectedM = null;
        settingInput = null;
        num = " ";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        sr = new ScaledResolution(mc);
        int startW = sr.getScaledWidth()/5;
        int startH = sr.getScaledHeight()/5;

        int longestC = 0;
        for(Module.Category c : categories) {
            if(fr.getStringWidth(c.name) > longestC) {
                longestC = fr.getStringWidth(c.name);
            }
        }

        // establishes background and categories background
        bg = new Box(startW, startH, 500, 300, 0xcc000000, "background");
        cbg = new Box(startW + 20, startH + 20, bg.x2 - bg.x1 - 40, fr.FONT_HEIGHT + 10, 0xff000000, "categories background");

        this.drawGradientRect(bg.x1, bg.y1, bg.x2, bg.y2, bg.color, bg.color); // draw background
        this.drawGradientRect(cbg.x1, cbg.y1, cbg.x2, cbg.y2, cbg.color, cbg.color); // draw categories background

        // draws category names and backgrounds
        int count = 0;
        for(Module.Category c : categories) {
            int length = fr.getStringWidth(c.name);
            int currentW = cbg.x1 + ((cbg.x2 - cbg.x1)/categories.size()) * count;
            Box b = new Box(currentW + 10, startH + 20, length, fr.FONT_HEIGHT + 10, 0x90AAAAAA, c.name);
            b.category = c;
//            this.drawGradientRect(b.x1, b.y1, b.x2, b.y2, b.color, b.color);

            //changes color if hovered
            int color;
            if(mouseX >= b.x1 && mouseX <= b.x2 && mouseY >= b.y1 && mouseY <= b.y2) {
                color = 0xffff0000;
            } else {
                color = -1;
            }
            this.drawString(fr, c.name, b.x1, b.y1 + (fr.FONT_HEIGHT/2) + 1, color);
            cboxes.add(b);
            count++;
        }

        // displays modules in selected category
        if(selectedC != null) {
            mboxes.clear();

            int longestName = 0;
            for(Module m : Client.getModulesByCategory(selectedC)) {
                if(fr.getStringWidth(m.name) > longestName)
                    longestName = fr.getStringWidth(m.name);
            }

            mbg = new Box(bg.x1 + 20, bg.y1 + fr.FONT_HEIGHT + 40, longestName + 20, 230, 0xff000000, "modules background");
            this.drawGradientRect(mbg.x1, mbg.y1, mbg.x2, mbg.y2, mbg.color, mbg.color);

            count = 0;
            for(Module m : Client.getModulesByCategory(selectedC)) {
                Box b = new Box(mbg.x1 + 5, mbg.y1 + 5 + ((fr.FONT_HEIGHT + 10)* count), longestName + 10,  fr.FONT_HEIGHT + 5, 0x90AAAAAA, m.name);
                b.module = m;

                // changes color if hovering
                int color = -1;
                if(m.isToggled()) {
                    color = 0xff0000ff;
                }
                if(mouseX >= b.x1 && mouseX <= b.x2 && mouseY >= b.y1 && mouseY <= b.y2) {
                    color = 0xffff0000;
                }

                this.drawGradientRect(b.x1, b.y1, b.x2, b.y2, b.color, b.color);
                this.drawString(fr, m.name, b.x1 + 5, b.y1 + ((fr.FONT_HEIGHT)/2), color);
                mboxes.add(b);
                count++;
            }
        }

        // displays settings for selected module
        if(selectedM != null) {
            sboxes.clear();
            List<Setting> settings = selectedM.settings;

            // gets longest setting length
            int longestS = 0;
            for(Setting s : settings) {
                String other = getSettingSuffix(s);
                if(fr.getStringWidth(other) > longestS)
                    longestS = fr.getStringWidth(other);
            }

            // settings background
            Box sbg = new Box(mbg.x2 + 20, mbg.y1, bg.x2 - (mbg.x2 + 40),mbg.y2 - mbg.y1, 0xff000000, "settings background");
            this.drawGradientRect(sbg.x1, sbg.y1, sbg.x2, sbg.y2, sbg.color, sbg.color);
            int row = 0, column = 0;

            // draws settings
            for(Setting s : settings) {
                if(column > 2) {
                    column = 0;
                    row++;
                }
                Box b = new Box(sbg.x1 + 5 + ((sbg.x2 - sbg.x1)/3)*column, sbg.y1 + 5 + (fr.FONT_HEIGHT + 15) * row, longestS + 10, fr.FONT_HEIGHT + 3, 0x90aaaaaa, s.name);
                b.setting = s;

                int color = -1;
                if((mouseX >= b.x1 && mouseX <= b.x2 && mouseY >= b.y1 && mouseY <= b.y2) || b.setting == settingInput)
                    color = 0xffff0000;

                this.drawGradientRect(b.x1, b.y1, b.x2, b.y2, b.color, b.color);
                this.drawString(fr, getSettingSuffix(s), b.x1 + 5, b.y1 + (fr.FONT_HEIGHT/2) - 1, color);
                sboxes.add(b);
                column++;
            }
        }

    }

    @NotNull
    private static String getSettingSuffix(Setting s) {
        String other = "";
        if(s instanceof BooleanSetting) {
            other = s.name + ": " + ((BooleanSetting) s).isEnabled();
        } else if(s instanceof KeyBindSetting) {
            other = s.name + ": " + Keyboard.getKeyName(((KeyBindSetting) s).getKeycode());
        } else if(s instanceof ModeSetting) {
            other = s.name + ": " + ((ModeSetting) s).getMode();
        } else if(s instanceof NumberSetting) {
            other = s.name + ": " + ((NumberSetting) s).getValue();
        }
        return other;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(Box b : cboxes) {
            if(mouseX >= b.x1 && mouseX <= b.x2 && mouseY >= b.y1 && mouseY <= b.y2 && (mouseButton == 0 || mouseButton == 1)) {
                selectedC = b.category;
            }
        }

        for(Box b : mboxes) {
            if(mouseX >= b.x1 && mouseX <= b.x2 && mouseY >= b.y1 && mouseY <= b.y2 && mouseButton == 1) {
                selectedM = b.module;
            } else if(mouseX >= b.x1 && mouseX <= b.x2 && mouseY >= b.y1 && mouseY <= b.y2 && mouseButton == 0) {
                b.module.toggle();
            }
        }

        for(Box b : sboxes) {
            if(mouseX >= b.x1 && mouseX <= b.x2 && mouseY >= b.y1 && mouseY <= b.y2 && mouseButton == 0) {
                if(b.setting instanceof BooleanSetting) {
                    ((BooleanSetting) b.setting).toggle();
                } else if (b.setting instanceof KeyBindSetting) {
                    settingInput = b.setting;
                } else if (b.setting instanceof ModeSetting) {
                    ((ModeSetting) b.setting).cycle(true);
                } else if (b.setting instanceof NumberSetting) {
                    settingInput = b.setting;
                }
            } else if (mouseX >= b.x1 && mouseX <= b.x2 && mouseY >= b.y1 && mouseY <= b.y2 && mouseButton == 1) {
                if(b.setting instanceof ModeSetting) {
                    ((ModeSetting) b.setting).cycle(false);
                }
            }
        }

        for(Box b : sboxes) {
            if(b.setting instanceof NumberSetting && b.setting == settingInput) {
                if(!(mouseX >= b.x1 && mouseX <= b.x2 && mouseY >= b.y1 && mouseY <= b.y2 && mouseButton == 0)) {
                    num = " ";
                    settingInput = null;
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        //exits gui if rshift or escape is pressed
        if(keyCode == 54 || keyCode == 1) {
            for(Module module : Client.modules) {
                if(module instanceof net.squirrel.modules.visual.ClickGUI) {
                    module.toggle();
                    break;
                }
            }
        }

        // if settingInput is equal to a setting, input number/key
        if(settingInput != null) {
            if(settingInput instanceof KeyBindSetting) {
                if(keyCode == 1 || keyCode == 211) {
                    ((KeyBindSetting) settingInput).setKeycode(0);
                } else {
                    ((KeyBindSetting) settingInput).setKeycode(keyCode);
                }
                settingInput = null;
            }
            if(settingInput instanceof NumberSetting) {
                if(keyCode == 11 || keyCode == 2 || keyCode == 3 || keyCode == 4 || keyCode == 5 || keyCode == 6 || keyCode == 7 || keyCode == 8 || keyCode == 9 || keyCode == 10 || keyCode == 52) {
                    num += Keyboard.getKeyName(keyCode);
                    ((NumberSetting) settingInput).setValue(Double.parseDouble(num));
                }
                if(keyCode == 1 || keyCode == 211 || keyCode == 28) {
                    settingInput = null;
                    num = "";
                }

            }
        }
        super.keyTyped(typedChar, keyCode);
    }
}
