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
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class StairsVerticalBlock extends HorizontalFacingBlock implements Waterloggable
{
    public static final    DirectionProperty FACING;
    public static final    BooleanProperty   WATERLOGGED;
    protected static final VoxelShape        NORTH_SIDING_SHAPE;
    protected static final VoxelShape        EAST_SIDING_SHAPE;
    protected static final VoxelShape        SOUTH_SIDING_SHAPE;
    protected static final VoxelShape        WEST_SIDING_SHAPE;
    protected static final VoxelShape        NORTH_EXTRA_SHAPE;
    protected static final VoxelShape        EAST_EXTRA_SHAPE;
    protected static final VoxelShape        SOUTH_EXTRA_SHAPE;
    protected static final VoxelShape        WEST_EXTRA_SHAPE;
    protected static final VoxelShape        NORTH_SHAPE;
    protected static final VoxelShape        EAST_SHAPE;
    protected static final VoxelShape        SOUTH_SHAPE;
    protected static final VoxelShape        WEST_SHAPE;

    /**
     * Creation of a stairs vertical block.
     *
     * @param settings Block settings.
     **/

    public StairsVerticalBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        BlockPos blockPos_1 = ctx.getBlockPos();
        FluidState fluidState_1 = ctx.getWorld().getFluidState(blockPos_1);
        double xPos = ctx.getHitPos().getX() - blockPos_1.getX();
        double zPos = ctx.getHitPos().getZ() - blockPos_1.getZ();
        Direction direction_1 = ctx.getPlayerFacing().getOpposite();
        Direction facing = ctx.getSide();
        if (facing.getAxis().isVertical())
        {
            if (direction_1 == Direction.EAST || direction_1 == Direction.WEST)
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
            if (zPos < 0.5) direction_1 = direction_1.rotateYClockwise();
        }
        else if (direction_1 == Direction.WEST)
        {
            if (zPos > 0.5) direction_1 = direction_1.rotateYClockwise();
        }
        else if (direction_1 == Direction.SOUTH)
        {
            if (xPos > 0.5) direction_1 = direction_1.rotateYClockwise();
        }
        else
        {
            if (xPos < 0.5) direction_1 = direction_1.rotateYClockwise();
        }
        return this.getDefaultState().with(FACING, direction_1).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        Direction dir = state.get(FACING);
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
    }

    /**
     * Definition of block properties.
     **/

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, WATERLOGGED);
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
        FACING = Properties.HORIZONTAL_FACING;
        WATERLOGGED = Properties.WATERLOGGED;
        NORTH_SIDING_SHAPE = SlabVerticalBlock.NORTH_SHAPE;
        EAST_SIDING_SHAPE = SlabVerticalBlock.EAST_SHAPE;
        SOUTH_SIDING_SHAPE = SlabVerticalBlock.SOUTH_SHAPE;
        WEST_SIDING_SHAPE = SlabVerticalBlock.WEST_SHAPE;
        NORTH_EXTRA_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        EAST_EXTRA_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        SOUTH_EXTRA_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        WEST_EXTRA_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        NORTH_SHAPE = VoxelShapes.union(NORTH_SIDING_SHAPE, NORTH_EXTRA_SHAPE);
        EAST_SHAPE = VoxelShapes.union(EAST_SIDING_SHAPE, EAST_EXTRA_SHAPE);
        SOUTH_SHAPE = VoxelShapes.union(SOUTH_SIDING_SHAPE, SOUTH_EXTRA_SHAPE);
        WEST_SHAPE = VoxelShapes.union(WEST_SIDING_SHAPE, WEST_EXTRA_SHAPE);
    }
}