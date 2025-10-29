package net.squirrel.modules.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Object> {
    public int compare(EntityLivingBase one, EntityLivingBase two) {
        return (int) (one.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) - two.getDistanceToEntity(Minecraft.getMinecraft().thePlayer));
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
