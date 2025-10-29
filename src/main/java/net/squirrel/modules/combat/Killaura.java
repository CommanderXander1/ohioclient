package net.squirrel.modules.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.squirrel.events.Event;
import net.squirrel.events.listeners.EventMotion;
import net.squirrel.events.listeners.EventUpdate;
import net.squirrel.modules.Module;
import net.squirrel.modules.util.Timer;
import net.squirrel.settings.BooleanSetting;
import net.squirrel.settings.ModeSetting;
import net.squirrel.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Killaura extends Module {
    public Timer timer = new Timer();
    public NumberSetting range = new NumberSetting("Range", 3, 1, 6, 0.1);
    public BooleanSetting noSwing = new BooleanSetting("No Swing", false);
    public NumberSetting maxcps = new NumberSetting("Max cps", 2, 1, 30, 1);
    public NumberSetting mincps = new NumberSetting("Min cps", 2, 1, 30, 1);
    public NumberSetting minrotation = new NumberSetting("Min rotation speed", 100, 1, 360, 1);
    public NumberSetting maxrotation = new NumberSetting("Max rotation speed", 100, 1, 360, 1);
    public ModeSetting autoblock = new ModeSetting("Autoblock", "None", "None", "Fake", "Vanilla");

    EntityLivingBase target;
    float yaw, pitch;

    public Killaura() {
        super("Killaura", Keyboard.KEY_R, Category.COMBAT);
        this.addSettings(range, maxcps, mincps, autoblock, noSwing, minrotation, maxrotation);
    }

    @Override
    public void onEvent(Event event) {
        target = null;
        double closestDist = range.getValue();
        for(Entity o : mc.theWorld.loadedEntityList) {
            if(o.equals(mc.thePlayer)) {
                continue;
            }
            if(o instanceof EntityLivingBase && !(o instanceof EntityArmorStand)) {
                if(o.getDistanceToEntity(mc.thePlayer) < closestDist) {
                    closestDist = o.getDistanceToEntity(mc.thePlayer);
                    target = (EntityLivingBase) o;
                }
            }
        }
        if(target != null && target.getHealth() > 0) {
            float[] rots = getRotations(target);
            mc.thePlayer.rotationYaw = rots[0];
            mc.thePlayer.rotationPitch = rots[1];
        }
        if(target != null && target.getHealth() > 0 && timer.hasTimeElapsed((long) (1000 / (mincps.getValue() + (int)(Math.random() * (maxcps.getValue() - mincps.getValue())))), true)) {
            mc.playerController.attackEntity(mc.thePlayer, target);
            mc.thePlayer.swingItem();
        }
    }

    public float[] getRotations(EntityLivingBase tar) {
        // Get the entity's bounding box
        AxisAlignedBB boundingBox = tar.getEntityBoundingBox();

        // Generate a random point inside the bounding box with subtle offsets
        Random random = new Random();

        // Define a factor for how "subtle" the randomness is (e.g., 0.1 means 10% of the bounding box size)
        double randomFactor = 0.2;

        // Apply subtle randomness to the coordinates (scaled by randomFactor)
        double offsetX = (random.nextDouble() - 0.5) * (boundingBox.maxX - boundingBox.minX) * randomFactor;
        double offsetY = (random.nextDouble() - 0.5) * (boundingBox.maxY - boundingBox.minY) * randomFactor;
        double offsetZ = (random.nextDouble() - 0.5) * (boundingBox.maxZ - boundingBox.minZ) * randomFactor;

        // Compute the target position inside the bounding box, centered on the entity
        double targetX = boundingBox.minX + (boundingBox.maxX - boundingBox.minX) * 0.5 + offsetX;
        double targetY = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * 0.5 + offsetY;
        double targetZ = boundingBox.minZ + (boundingBox.maxZ - boundingBox.minZ) * 0.5 + offsetZ;

        // Calculate the deltas between the player and the random point inside the bounding box
        double deltaX = targetX - mc.thePlayer.posX;
        double deltaY = (targetY + tar.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight()); // Correct deltaY using eye height
        double deltaZ = targetZ - mc.thePlayer.posZ;

        // Calculate the horizontal distance to the target
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        // Calculate yaw (horizontal angle)
        float yaw;
        if (deltaZ != 0) {
            yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ));
        } else {
            yaw = (deltaX < 0) ? 180 : 0; // Handle the case where deltaZ == 0
        }

        // Adjust yaw based on the quadrant to ensure it's correctly pointing in all directions
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        // Calculate pitch (vertical angle) using atan2 for more precise calculation
        float pitch = (distance != 0) ? (float) Math.toDegrees(Math.atan2(deltaY, distance)) : 0;

        return new float[] { yaw, pitch };
    }
}
