package net.squirrel.modules.player;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import net.squirrel.settings.BooleanSetting;
import net.squirrel.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

public class InventoryManager extends Module {
    NumberSetting mindelay = new NumberSetting("Min delay", 250, 0, 1000, 10);
    NumberSetting maxdelay = new NumberSetting("Max delay", 300, 0, 1000, 10);
    BooleanSetting onopen = new BooleanSetting("Open inventory", true);

    public InventoryManager() {
        super("InventoryManager", Keyboard.KEY_Z, Category.PLAYER);
        this.addSettings(mindelay, maxdelay, onopen);
    }

    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            InventoryPlayer inventory = mc.thePlayer.inventory;
            if(onopen.isEnabled()) {
//                if(mc.thePlayer.openContainer.equals(inventory)) {
//                    System.out.println("inventory open");
//                    sort(inventory);
//                }
//                does not work
            } else {
                System.out.println("sorting");
                sort(inventory);
            }

        }
    }

    public void sort(InventoryPlayer inventory) {
        Thread one = new Thread(() -> {
            try {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if (stack == null)
                        continue;

                    Thread.sleep((long) (Math.random() * (maxdelay.getValue() - mindelay.getValue()) + mindelay.getValue()));
                    mc.playerController.sendPacketDropItem(stack);
                    if(stack.getItem() instanceof ItemSword) {
                        System.out.println("sword");
//                        mc.playerController.sendPacketDropItem(stack, );
                        mc.thePlayer.dropItem(stack, true, true);
//                        mc.playerController.windowClick(inventory.window)
                        // weapon, bow, blocks, arrow, potion, food, armor, item
                    }
//                    mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        one.start();
    }

}
