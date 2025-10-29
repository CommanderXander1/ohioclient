package net.squirrel.ui.altmanager;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import net.squirrel.Client;
import net.squirrel.events.listeners.EventRenderGUI;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.ColorUtil;
import net.squirrel.ui.MainMenu;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;

public class AltManager extends GuiScreen {
    GuiTextField input;
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        input.drawTextBox();
        this.drawCenteredString(fontRendererObj, "Currently logged in as: " + mc.getSession().getUsername(), this.width/2, 20, 0xccaaaaaa);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.enabled) {
            if(button.id == 0)
            {}
            if(button.id == 1)
                mc.displayGuiScreen(new MainMenu());
            if(button.id == 2) {
                this.mc.session = new Session(input.getText(), "", "", "mojang");
            }
        }
        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        input.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);
        this.buttonList.add(new GuiButton(0, this.width/2 - (150/2), this.height - 60, 150, 20, "Add alt"));
        this.buttonList.add(new GuiButton(1, 20, this.height - 40, 50, 20, "Back"));
        this.buttonList.add(new GuiButton(2, this.width/2 - (150/2), this.height - 40, 150, 20, "Cracked login"));
        input = new GuiTextField(3, fontRendererObj, this.width/2 - (150/2), this.height - 130, 150, 20);
        input.setMaxStringLength(16);
        input.setFocused(true);
        super.initGui();
    }


    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}
