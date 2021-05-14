package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.shape.VoxelShape;

public class SlabBlock extends net.minecraft.block.SlabBlock
{
    public SlabBlock(Settings blockSettings)
    {
        super(blockSettings);
    }

    public static final    EnumProperty<SlabType> TYPE;
    public static final    BooleanProperty        WATERLOGGED;
    protected static final VoxelShape             BOTTOM_SHAPE;
    protected static final VoxelShape             TOP_SHAPE;

    static
    {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}