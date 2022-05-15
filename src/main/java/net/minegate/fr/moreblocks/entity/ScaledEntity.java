package net.minegate.fr.moreblocks.entity;

import net.minecraft.entity.EntityDimensions;

public interface ScaledEntity
{
    float getScale();
    EntityDimensions scaleDimensions(EntityDimensions dimensions);
}