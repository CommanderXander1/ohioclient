package net.squirrel.modules.player;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import net.squirrel.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

public class ChestStealer extends Module {
    NumberSetting mindelay = new NumberSetting("Min delay", 250, 0, 1000, 10);
    NumberSetting maxdelay = new NumberSetting("Max delay", 300, 0, 1000, 10);
    public ChestStealer() {
        super("ChestStealer", Keyboard.KEY_Z, Category.PLAYER);
        this.addSettings(mindelay, maxdelay);
    }

    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            if(mc.thePlayer.openContainer instanceof ContainerChest) {
                ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
                Thread one = new Thread(() -> {
                    try {
                        for(int i = 0; i < chest.numRows * 9; i++) {
                            Slot slot = (Slot) chest.getSlot(i);
                            if(slot.getStack() == null)
                                continue;
                            Thread.sleep((long) (Math.random() * (maxdelay.getValue() - mindelay.getValue()) + mindelay.getValue()));
                            mc.playerController.windowClick(chest.windowId, i, 0, 1 , mc.thePlayer);
                        }
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                one.start();
            }
        }
    }


}
