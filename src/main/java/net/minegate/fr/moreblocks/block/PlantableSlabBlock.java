package net.minegate.fr.moreblocks.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.shape.VoxelShape;

public class PlantableSlabBlock extends SlabBlock implements Waterloggable
{
    public static final  EnumProperty<SlabType> TYPE;
    public static final  BooleanProperty        WATERLOGGED;
    static final         VoxelShape             BOTTOM_SHAPE;
    static final         VoxelShape             TOP_SHAPE;

    /**
     * Creation of a plantable slab block.
     *
     * @param settings Block settings.
     **/

    public PlantableSlabBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.TOP).with(WATERLOGGED, Boolean.FALSE));
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}