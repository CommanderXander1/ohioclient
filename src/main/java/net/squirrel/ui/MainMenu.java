package net.squirrel.ui;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.squirrel.Client;
import net.squirrel.ui.altmanager.AltManager;

public class MainMenu extends GuiScreen {
    public String currentScreen;
    public int currentScreenIndex;
    public MainMenu() {
//        currentScreen = getScreens().get(0);
        currentScreenIndex = 0;
        currentScreen = "preston2.jpg";
    }

    public void InitGui() {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("squirrel/" + currentScreen));
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        this.drawGradientRect(0, height - 100, width, height, 0x00000000, 0xff000000);

        String[] buttons = {"Singleplayer", "Multiplayer", "Settings", "Language", "Quit", "Cycle background", "Alt manager"};

        int count = 0;
        for(String name : buttons) {

            float x = (width/buttons.length) * count + (width/buttons.length/2f) + 8 - (mc.fontRendererObj.getStringWidth(name)/2f);
            float y = height - 40;
            boolean hovered = (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT);

            this.drawCenteredString(mc.fontRendererObj, name, (width/buttons.length) * count + (width/buttons.length/2f) + 8, y, hovered ? 0xff0000ff : -1);
            count++;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(width/2f, height/2f, 0);
        GlStateManager.scale(3, 3, 1);
        GlStateManager.translate(-(width/2f), -(height/2f), 0);
        this.drawCenteredString(mc.fontRendererObj, Client.name, width/2f, height/2f - mc.fontRendererObj.FONT_HEIGHT/2f, -1);
        GlStateManager.popMatrix();
    }

    public void cycleBackground() {
        String[] screens = {"xander.jpg", "urinate.jpg", "squirrel.jpg", "preston2.jpg", "preston.jpg", "jump.jpg", "james.jpg", "jacked.jpg", "hawktuah.jpg", "dog.jpg"};
        if(currentScreenIndex < screens.length - 1) {
            currentScreenIndex++;
        } else {
            currentScreenIndex = 0;
        }
        currentScreen = screens[currentScreenIndex];
    }

//    public List<String> getScreens() {
//        List<String> results = new ArrayList<String>();
//        File asset_dir = new File(new ResourceLocation("squirrel").getResourceDomain());
//        File[] files = asset_dir.listFiles();
//
//        assert files != null;
//        for (File file : files) {
//            if (file.isFile() && file.getName().contains(".jpg")) {
//                results.add(file.getName());
//            }
//        }
//        return results;
//    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        String[] buttons = {"Singleplayer", "Multiplayer", "Settings", "Language", "Quit", "Cycle background", "Alt manager"};

        int count = 0;
        for(String name : buttons) {
            float x = (width/buttons.length) * count + (width/buttons.length/2f) + 8 - (mc.fontRendererObj.getStringWidth(name)/2f);
            float y = height - 40;
            if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
                switch(name) {
                    case "Singleplayer":
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    case "Multiplayer":
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    case "Settings":
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                    case "Language":
                        mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                        break;
                    case "Quit":
                        mc.shutdown();
                        break;
                    case "Cycle background":
                        cycleBackground();
                        break;
                    case "Alt manager":
                        mc.displayGuiScreen(new AltManager());
                        break;
                }
            }
            count++;
        }
    }

    public void onGuiClosed() {

    }
}
