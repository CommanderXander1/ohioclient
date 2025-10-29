package net.squirrel.modules.visual;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.squirrel.events.Event;
import net.squirrel.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.stream.Collectors;

public class ESP extends Module {
    public ESP() {
        super("ESP", Keyboard.KEY_NONE, Category.VISUAL);
    }

    public void onEnable() {
        try {
            for (Entity e : mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList())) {
                System.out.println(e.getCollisionBoundingBox().toString());
//            RenderGlobal.drawOutlinedBoundingBox(e.getCollisionBoundingBox(), 0, 255, 255 ,255);
            }
        } catch (NullPointerException error) {
            System.out.println(error);
        }

    }

    public void onDisable() {

    }

    public void onEvent(Event event) {

    }
}
