package net.minegate.fr.moreblocks.block;

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
import net.minegate.fr.moreblocks.entity.ISitBlock;

import java.util.stream.Stream;

public class StoolBlock extends HorizontalFacingBlock implements ISitBlock, Waterloggable
{
    public static final    DirectionProperty FACING;
    public static final    BooleanProperty   WATERLOGGED;
    protected static final VoxelShape        NORTH_SHAPE;
    protected static final VoxelShape        SOUTH_SHAPE;
    protected static final VoxelShape        WEST_SHAPE;
    protected static final VoxelShape        EAST_SHAPE;

    /**
     * Creation of a stool block.
     *
     * @param settings Block settings.
     **/

    public StoolBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Consideration of placement position.
     **/

    @Override
    public BlockState getPlacementState(ItemPlacementContext placementContext)
    {
        return getDefaultState().with(FACING, placementContext.getPlayerFacing().getOpposite());
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
        builder.add(Properties.HORIZONTAL_FACING, WATERLOGGED);
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

    @Override
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
                Block.createCuboidShape(4, 0, 4, 6, 6, 6),
                Block.createCuboidShape(4, 0, 10, 6, 6, 12),
                Block.createCuboidShape(10, 0, 4, 12, 6, 6),
                Block.createCuboidShape(10, 0, 10, 12, 6, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        SOUTH_SHAPE = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(4, 0, 4, 6, 6, 6),
                Block.createCuboidShape(4, 0, 10, 6, 6, 12),
                Block.createCuboidShape(10, 0, 4, 12, 6, 6),
                Block.createCuboidShape(10, 0, 10, 12, 6, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        WEST_SHAPE = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(4, 0, 4, 6, 6, 6),
                Block.createCuboidShape(4, 0, 10, 6, 6, 12),
                Block.createCuboidShape(10, 0, 4, 12, 6, 6),
                Block.createCuboidShape(10, 0, 10, 12, 6, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        EAST_SHAPE = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(4, 0, 4, 6, 6, 6),
                Block.createCuboidShape(4, 0, 10, 6, 6, 12),
                Block.createCuboidShape(10, 0, 4, 12, 6, 6),
                Block.createCuboidShape(10, 0, 10, 12, 6, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}