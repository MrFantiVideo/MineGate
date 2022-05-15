package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.enums.QuarterVerticalType;

public class QuarterVerticalBlock extends HorizontalFacingBlock implements Waterloggable
{
    public static final    DirectionProperty                 FACING;
    public static final    EnumProperty<QuarterVerticalType> TYPE;
    public static final    BooleanProperty                   WATERLOGGED;
    protected static final VoxelShape                        CENTER_SHAPE;
    protected static final VoxelShape                        BOTTOM_SHAPE;
    protected static final VoxelShape                        NORTH_SHAPE;
    protected static final VoxelShape                        EAST_SHAPE;
    protected static final VoxelShape                        SOUTH_SHAPE;
    protected static final VoxelShape                        WEST_SHAPE;
    protected static final VoxelShape                        CENTER_X_NORTH_SHAPE;
    protected static final VoxelShape                        CENTER_X_EAST_SHAPE;
    protected static final VoxelShape                        CENTER_X_SOUTH_SHAPE;
    protected static final VoxelShape                        CENTER_X_WEST_SHAPE;

    /**
     * Creation of a quarter vertical block.
     *
     * @param settings Block settings.
     **/

    public QuarterVerticalBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TYPE, QuarterVerticalType.BOTTOM).with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        BlockPos blockPos_1 = ctx.getBlockPos();
        FluidState fluidState_1 = ctx.getWorld().getFluidState(blockPos_1);
        double xPos = ctx.getHitPos().getX() - blockPos_1.getX();
        double yPos = ctx.getHitPos().getY() - blockPos_1.getY();
        double zPos = ctx.getHitPos().getZ() - blockPos_1.getZ();
        Direction direction_1 = ctx.getPlayerFacing().getOpposite();
        Direction facing = ctx.getSide();
        if (facing.getAxis().isVertical())
        {
            if (xPos > 0.3D && xPos < 0.6D && zPos > 0.3D && zPos < 0.6D)
            {
                return this.getDefaultState().with(FACING, direction_1).with(TYPE, QuarterVerticalType.CENTER_Z).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
            }
            else if (direction_1 == Direction.EAST || direction_1 == Direction.WEST)
            {
                if (xPos > 0.5) direction_1 = Direction.WEST;
                else direction_1 = Direction.EAST;
            }
            else
            {
                if (zPos > 0.5) direction_1 = Direction.NORTH;
                else direction_1 = Direction.SOUTH;
            }
        }
        if (direction_1 == Direction.EAST)
        {
            if (zPos > 0.3D && zPos < 0.6D && yPos > 0.3D && yPos < 0.6D)
            {
                return this.getDefaultState().with(FACING, direction_1).with(TYPE, QuarterVerticalType.CENTER_X).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
            }
            if (zPos < 0.5) direction_1 = direction_1.rotateYClockwise();
        }
        else if (direction_1 == Direction.WEST)
        {
            if (zPos > 0.3D && zPos < 0.6D && yPos > 0.3D && yPos < 0.6D)
            {
                return this.getDefaultState().with(FACING, direction_1).with(TYPE, QuarterVerticalType.CENTER_X).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
            }
            if (zPos > 0.5) direction_1 = direction_1.rotateYClockwise();
        }
        else if (direction_1 == Direction.SOUTH)
        {
            if (yPos > 0.3D && yPos < 0.6D && xPos > 0.3D && xPos < 0.6D)
            {
                return this.getDefaultState().with(FACING, direction_1).with(TYPE, QuarterVerticalType.CENTER_X).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
            }
            if (xPos > 0.5) direction_1 = direction_1.rotateYClockwise();
        }
        else
        {
            if (yPos > 0.3D && yPos < 0.6D && xPos > 0.3D && xPos < 0.6D)
            {
                return this.getDefaultState().with(FACING, direction_1).with(TYPE, QuarterVerticalType.CENTER_X).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
            }
            if (xPos < 0.5) direction_1 = direction_1.rotateYClockwise();
        }
        return this.getDefaultState().with(FACING, direction_1).with(TYPE, QuarterVerticalType.BOTTOM).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        Direction dir = state.get(FACING);
        QuarterVerticalType slabType = (QuarterVerticalType) state.get(TYPE);
        switch (slabType)
        {
            case BOTTOM:
                switch (dir)
                {
                    case EAST:
                        return EAST_SHAPE;
                    case SOUTH:
                        return SOUTH_SHAPE;
                    case WEST:
                        return WEST_SHAPE;
                    default:
                        return NORTH_SHAPE;
                }
            case CENTER_Z:
                return CENTER_SHAPE;
            case CENTER_X:
                switch (dir)
                {
                    case EAST:
                        return CENTER_X_EAST_SHAPE;
                    case SOUTH:
                        return CENTER_X_SOUTH_SHAPE;
                    case WEST:
                        return CENTER_X_WEST_SHAPE;
                    default:
                        return CENTER_X_NORTH_SHAPE;
                }
        }
        return BOTTOM_SHAPE;
    }

    /**
     * Definition of block properties.
     **/

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, TYPE, WATERLOGGED);
    }

    /**
     * Allows you to make the block waterlogged.
     **/

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    /**
     * Allows entities to walk through it.
     **/

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type)
    {
        return false;
    }

    static
    {
        TYPE = net.minegate.fr.moreblocks.state.Properties.QUARTER_VERTICAL_TYPE;
        FACING = Properties.HORIZONTAL_FACING;
        WATERLOGGED = Properties.WATERLOGGED;
        NORTH_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        EAST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        WEST_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        CENTER_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        CENTER_X_NORTH_SHAPE = Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
        CENTER_X_EAST_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
        CENTER_X_SOUTH_SHAPE = Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
        CENTER_X_WEST_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    }
}