package fr.minegate.block.painted;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.stream.Stream;

public class PaintedChairBlock extends PaintedHorizontalFacingBlock implements BlockEntityProvider, Waterloggable
{
    public static final    DirectionProperty FACING;
    public static final    BooleanProperty   WATERLOGGED;
    protected static final VoxelShape        NORTH_SHAPE;
    protected static final VoxelShape        SOUTH_SHAPE;
    protected static final VoxelShape        WEST_SHAPE;
    protected static final VoxelShape        EAST_SHAPE;

    /**
     * Creation of a chair block.
     *
     * @param settings Block settings.
     **/

    public PaintedChairBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Consideration of placement position.
     **/

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        FluidState iFluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGGED, iFluidState.getFluid() == Fluids.WATER);
    }

    /**
     * Custom outlines of the block.
     **/

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        Direction dir = state.get(FACING);
        switch (dir)
        {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return VoxelShapes.fullCube();
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
     * Position of player when sitting.
     **/


    public Vec3d getSitPosition()
    {
        return new Vec3d(0.5D, 0.25D, 0.5D);
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
        FACING = HorizontalFacingBlock.FACING;
        WATERLOGGED = Properties.WATERLOGGED;
        NORTH_SHAPE = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(3, 0, 11, 5, 6, 13),
                Block.createCuboidShape(3, 8, 11, 5, 16, 13),
                Block.createCuboidShape(11, 0, 11, 13, 6, 13),
                Block.createCuboidShape(11, 8, 11, 13, 16, 13),
                Block.createCuboidShape(5, 13, 12, 11, 14, 13),
                Block.createCuboidShape(5, 10, 12, 11, 11, 13),
                Block.createCuboidShape(3, 0, 3, 5, 6, 5),
                Block.createCuboidShape(11, 0, 3, 13, 6, 5)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        SOUTH_SHAPE = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(11, 0, 3, 13, 6, 5),
                Block.createCuboidShape(11, 8, 3, 13, 16, 5),
                Block.createCuboidShape(3, 0, 3, 5, 6, 5),
                Block.createCuboidShape(3, 8, 3, 5, 16, 5),
                Block.createCuboidShape(5, 13, 3, 11, 14, 4),
                Block.createCuboidShape(5, 10, 3, 11, 11, 4),
                Block.createCuboidShape(11, 0, 11, 13, 6, 13),
                Block.createCuboidShape(3, 0, 11, 5, 6, 13)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        WEST_SHAPE = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(11, 0, 11, 13, 6, 13),
                Block.createCuboidShape(11, 8, 11, 13, 16, 13),
                Block.createCuboidShape(11, 0, 3, 13, 6, 5),
                Block.createCuboidShape(11, 8, 3, 13, 16, 5),
                Block.createCuboidShape(12, 13, 5, 13, 14, 11),
                Block.createCuboidShape(12, 10, 5, 13, 11, 11),
                Block.createCuboidShape(3, 0, 11, 5, 6, 13),
                Block.createCuboidShape(3, 0, 3, 5, 6, 5)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        EAST_SHAPE = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(3, 0, 3, 5, 6, 5),
                Block.createCuboidShape(3, 8, 3, 5, 16, 5),
                Block.createCuboidShape(3, 0, 11, 5, 6, 13),
                Block.createCuboidShape(3, 8, 11, 5, 16, 13),
                Block.createCuboidShape(3, 13, 5, 4, 14, 11),
                Block.createCuboidShape(3, 10, 5, 4, 11, 11),
                Block.createCuboidShape(11, 0, 3, 13, 6, 5),
                Block.createCuboidShape(11, 0, 11, 13, 6, 13)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}