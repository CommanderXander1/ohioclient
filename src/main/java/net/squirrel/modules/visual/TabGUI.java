package net.squirrel.modules.visual;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.squirrel.Client;
import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventKey;
import net.squirrel.events.listeners.EventRenderGUI;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.ColorUtil;
import net.squirrel.settings.*;
import org.lwjgl.input.Keyboard;
import java.util.List;

import java.awt.*;
import java.util.Comparator;

public class TabGUI extends Module {

    public int currentTab;
    public boolean expanded;
    public TabGUI() {

        super("TabGUI", Keyboard.KEY_Y, Category.VISUAL);
        this.toggled = true;
    }

    public void onEvent(Event event) {
        if(event instanceof EventRenderGUI) {
            FontRenderer fr = mc.fontRendererObj;

            int primaryColor = (int) ColorUtil.getRainbow(4, 0.6f, 1f), secondaryColor = 0xff0000aa;

            int top_padding = 30;
            int left_padding = 5;

            // finds longest category name
            int longest = 0;

            for (Category category : Module.Category.values()) {
                if (fr.getStringWidth(category.name()) > longest) {
                    longest = fr.getStringWidth(category.name);
                }
            }

            Gui.drawRect(left_padding, top_padding, left_padding + longest + 10, top_padding + 6 + (fr.FONT_HEIGHT * Category.values().length), 0x90000000);
            Gui.drawRect(left_padding + 1, top_padding + 2 + (currentTab * fr.FONT_HEIGHT), left_padding + longest + 9, top_padding + fr.FONT_HEIGHT + (fr.FONT_HEIGHT * currentTab), primaryColor);

            int count = 0;
            for (Category category : Module.Category.values()) {
                fr.drawStringWithShadow(category.name, left_padding + 5, top_padding + 2 + (count * fr.FONT_HEIGHT), -1);
                count++;
            }
            
            int left_padding_expanded = left_padding + longest + 11;
            int longest_expanded = 0;

            if(expanded) {
                Category category = Module.Category.values()[currentTab];
                List<Module> modules = Client.getModulesByCategory(category);

                if(modules.isEmpty()) {
                    return;
                }

                for (Module module: Client.getModulesByCategory(category)) {
                    if (fr.getStringWidth(module.name) > longest_expanded) {
                        longest_expanded = fr.getStringWidth(module.name);
                    }
                }

                // expansion
                Gui.drawRect(left_padding_expanded, top_padding, left_padding_expanded + longest_expanded + 10, top_padding + 6 + (fr.FONT_HEIGHT * modules.size()), 0x90000000);
                Gui.drawRect(left_padding_expanded + 1, top_padding + 2 + (category.moduleIndex * fr.FONT_HEIGHT), left_padding_expanded + longest_expanded + 9, top_padding + fr.FONT_HEIGHT + (category.moduleIndex * fr.FONT_HEIGHT), primaryColor);

                count = 0;
                for (Module module : modules) {
                    fr.drawStringWithShadow(module.name, left_padding_expanded + 5, top_padding + 2 + (count * fr.FONT_HEIGHT), -1);
                    if(count == category.moduleIndex && module.expanded) {
                        // CHANGE LONGEST
                        int index = 0, maxLength = 0;
                        for (Setting setting : module.settings) {
                            if (setting instanceof BooleanSetting) {
                                BooleanSetting bool = (BooleanSetting) setting;
                                if(maxLength < fr.getStringWidth(setting.name + ": " + (bool.isEnabled() ? "Enabled" : "Disabled"))) {
                                    maxLength = fr.getStringWidth(setting.name + ": " + (bool.isEnabled() ? "Enabled" : "Disabled"));
                                }
                            }
                            if (setting instanceof ModeSetting) {
                                ModeSetting mode = (ModeSetting) setting;
                                if(maxLength < fr.getStringWidth(setting.name + ": " + mode.getMode())) {
                                    maxLength = fr.getStringWidth(setting.name + ": " + mode.getMode());
                                }
                            }
                            if (setting instanceof NumberSetting) {
                                NumberSetting number = (NumberSetting) setting;
                                if(maxLength < fr.getStringWidth(setting.name + ": " + number.getValue())) {
                                    maxLength = fr.getStringWidth(setting.name + ": " + number.getValue());
                                }
                            }
                            if (setting instanceof KeyBindSetting) {
                                KeyBindSetting keybind = (KeyBindSetting) setting;
                                if(maxLength < fr.getStringWidth(setting.name + ": " + Keyboard.getKeyName(keybind.getKeycode()))) {
                                    maxLength = fr.getStringWidth(setting.name + ": " + Keyboard.getKeyName(keybind.getKeycode()));
                                }
                            }
                        }

                        if(!module.settings.isEmpty()) {
                            int left_setting = left_padding_expanded + longest_expanded + 11;
                            Gui.drawRect(left_setting, top_padding, left_setting + maxLength + 10, top_padding + 6 + (fr.FONT_HEIGHT * module.settings.size()), 0x90000000);
                            Gui.drawRect(left_setting + 1, top_padding + 2 + (module.index * fr.FONT_HEIGHT), left_setting + maxLength + 9, top_padding + fr.FONT_HEIGHT + (module.index * fr.FONT_HEIGHT), module.settings.get(module.index).focused ? secondaryColor : primaryColor);

                            index = 0;
                            for (Setting setting : module.settings) {
                                if (setting instanceof BooleanSetting) {
                                    BooleanSetting bool = (BooleanSetting) setting;
                                    fr.drawStringWithShadow(setting.name + ": " + (bool.isEnabled() ? "Enabled" : "Disabled"), left_setting + 5, top_padding + 2 + (index * fr.FONT_HEIGHT), -1);
                                }
                                if (setting instanceof ModeSetting) {
                                    ModeSetting mode = (ModeSetting) setting;
                                    fr.drawStringWithShadow(setting.name + ": " + mode.getMode(), left_setting + 5, top_padding + 2 + (index * fr.FONT_HEIGHT), -1);
                                }
                                if (setting instanceof NumberSetting) {
                                    NumberSetting number = (NumberSetting) setting;
                                    fr.drawStringWithShadow(setting.name + ": " + number.getValue(), left_setting + 5, top_padding + 2 + (index * fr.FONT_HEIGHT), -1);
                                }
                                if (setting instanceof KeyBindSetting) {
                                    KeyBindSetting keybind = (KeyBindSetting) setting;
                                    fr.drawStringWithShadow(setting.name + ": " + Keyboard.getKeyName(keybind.getKeycode()), left_setting + 5, top_padding + 2 + (index * fr.FONT_HEIGHT), -1);
                                }
                                index++;
                            }
                        }
                    }
                    count++;
                }


            }

        }
        if (event instanceof EventKey) {
            int code = ((EventKey) event).key;
            Category category = Module.Category.values()[currentTab];
            List<Module> modules = Client.getModulesByCategory(category);

            if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                Module module = modules.get(category.moduleIndex);

                if(!module.settings.isEmpty() && module.settings.get(module.index) instanceof KeyBindSetting && module.settings.get(module.index).focused) {
                    if(code != Keyboard.KEY_RETURN && code != Keyboard.KEY_UP && code != Keyboard.KEY_DOWN && code != Keyboard.KEY_RIGHT && code != Keyboard.KEY_LEFT && code != Keyboard.KEY_ESCAPE) {
                        KeyBindSetting keyBind = (KeyBindSetting) module.settings.get(module.index);
                        keyBind.setKeycode(code);
                        keyBind.focused = false;
                        if(code == Keyboard.KEY_BACK) {
                            keyBind.setKeycode(Keyboard.KEY_NONE);
                        }
                        return;
                    }

                }
            }

             if (code == Keyboard.KEY_UP) {
                 if (expanded) {
                     if(!modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                         Module module = modules.get(category.moduleIndex);
                         if (!module.settings.isEmpty()) {
                             if(!module.settings.get(module.index).focused) {
                                 if (module.index <= 0) {
                                     module.index = module.settings.size() - 1;
                                 } else {
                                     module.index--;
                                 }
                             } else {
                                 Setting setting = module.settings.get(module.index);
                                 if(setting instanceof BooleanSetting) {
                                     ((BooleanSetting) setting).toggle();
                                 } else if (setting instanceof ModeSetting) {
                                     ((ModeSetting) setting).cycle(true);
                                 } else if (setting instanceof NumberSetting) {
                                     ((NumberSetting) setting).increment(true);
                                 }
                             }
                         }
                     } else {
                         if (category.moduleIndex <= 0) {
                             category.moduleIndex = modules.size() - 1;
                         } else {
                             category.moduleIndex--;
                         }
                     }

                 } else {
                     if (currentTab <= 0) {
                         currentTab = Category.values().length -1;
                     } else {
                         currentTab--;
                     }
                 }


             }
             if (code == Keyboard.KEY_DOWN) {
                 if (expanded) {
                     if(!modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                         Module module = modules.get(category.moduleIndex);
                         if(!module.settings.isEmpty()) {
                             if(!module.settings.get(module.index).focused) {
                                 if (module.index >= module.settings.size() - 1) {
                                     module.index = 0;
                                 } else {
                                     module.index++;
                                 }
                             } else {
                                 Setting setting = module.settings.get(module.index);
                                 if(setting instanceof BooleanSetting) {
                                     ((BooleanSetting) setting).toggle();
                                 } else if (setting instanceof ModeSetting) {
                                     ((ModeSetting) setting).cycle(false);
                                 } else if (setting instanceof NumberSetting) {
                                     ((NumberSetting) setting).increment(false);
                                 }
                             }
                         }
                     } else {
                         if (category.moduleIndex >= modules.size() - 1) {
                             category.moduleIndex = 0;
                         } else {
                             category.moduleIndex++;
                         }
                     }

                 } else {
                     if (currentTab >= Category.values().length -1) {
                         currentTab = 0;
                     } else {
                         currentTab++;
                     }
                 }


             }
            if (code == Keyboard.KEY_RETURN) {
                if (expanded && !modules.isEmpty()) {
                    Module module = modules.get(category.moduleIndex);
                    if(!module.expanded && !module.settings.isEmpty()) {
                        module.expanded = true;
                    } else if (!module.settings.isEmpty()){
                        module.settings.get(module.index).focused = !module.settings.get(module.index).focused;
                    } else {

                    }
                } else {
                    expanded = true;
                }
            }
            if (code == Keyboard.KEY_RIGHT) {
                if (expanded && !modules.isEmpty()) {
                    Module module = modules.get(category.moduleIndex);
                    if(module.expanded && !module.settings.isEmpty()) {
                        Setting setting = module.settings.get(module.index);
                        if(setting instanceof BooleanSetting) {
                            ((BooleanSetting) setting).toggle();
                        } else if (setting instanceof ModeSetting) {
                            ((ModeSetting) setting).cycle(true);
                        } else if (setting instanceof NumberSetting) {
                            ((NumberSetting) setting).increment(true);
                        }
                    } else {
                        if(!module.name.equals("TabGUI")) {
                            module.toggle();
                        }
                    }

                } else {
                    expanded = true;
                }
            }
            if (code == Keyboard.KEY_LEFT) {
                if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                    Module module = modules.get(category.moduleIndex);
                    if(!module.settings.isEmpty()) {
                        if(!module.settings.get(module.index).focused) {
                            modules.get(category.moduleIndex).expanded = false;
                        } else {
                            Setting setting = module.settings.get(module.index);
                            if(setting instanceof BooleanSetting) {
                                ((BooleanSetting) setting).toggle();
                            } else if (setting instanceof ModeSetting) {
                                ((ModeSetting) setting).cycle(false);
                            } else if (setting instanceof NumberSetting) {
                                ((NumberSetting) setting).increment(false);
                            }
                        }
                    }
                } else {
                    expanded = false;
                }
            }

        }
    }


}
