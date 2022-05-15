package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.stream.IntStream;

public class QuarterBlock extends Block implements Waterloggable
{
    public static final    DirectionProperty        FACING;
    public static final    EnumProperty<BlockHalf>  HALF;
    public static final    EnumProperty<StairShape> SHAPE;
    public static final    BooleanProperty          WATERLOGGED;
    protected static final VoxelShape               TOP_SHAPE;
    protected static final VoxelShape               BOTTOM_SHAPE;
    protected static final VoxelShape               BOTTOM_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape               BOTTOM_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape               TOP_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape               TOP_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape               BOTTOM_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape               BOTTOM_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape               TOP_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape               TOP_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape[]             TOP_SHAPES;
    protected static final VoxelShape[]             BOTTOM_SHAPES;
    private static final   int[]                    SHAPE_INDICES;

    /**
     * Creation of a quarter block.
     *
     * @param settings Block settings.
     **/

    public QuarterBlock(Settings settings)
    {
        super(settings);
    }

    private static VoxelShape[] composeShapes(VoxelShape base, VoxelShape northWest, VoxelShape northEast, VoxelShape southWest, VoxelShape southEast)
    {
        return IntStream.range(0, 16).mapToObj((i) -> composeShape(i, base, northWest, northEast, southWest, southEast)).toArray(VoxelShape[]::new);
    }

    private static VoxelShape composeShape(int i, VoxelShape base, VoxelShape northWest, VoxelShape northEast, VoxelShape southWest, VoxelShape southEast)
    {
        VoxelShape voxelShape = base;
        if ((i & 1) != 0)
        {
            voxelShape = VoxelShapes.union(base, northWest);
        }

        if ((i & 2) != 0)
        {
            voxelShape = VoxelShapes.union(voxelShape, northEast);
        }

        if ((i & 4) != 0)
        {
            voxelShape = VoxelShapes.union(voxelShape, southWest);
        }

        if ((i & 8) != 0)
        {
            voxelShape = VoxelShapes.union(voxelShape, southEast);
        }
        return voxelShape;
    }

    public boolean hasSidedTransparency(BlockState state)
    {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return (state.get(HALF) == BlockHalf.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_INDICES[this.getShapeIndexIndex(state)]];
    }

    private int getShapeIndexIndex(BlockState state)
    {
        return state.get(SHAPE).ordinal() * 4 + state.get(FACING).getHorizontal();
    }

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        Direction direction = ctx.getSide();
        BlockState blockState = this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(HALF, direction != Direction.DOWN && (direction == Direction.UP || ctx.getHitPos().y - (double) blockPos.getY() <= 0.5D) ? BlockHalf.BOTTOM : BlockHalf.TOP).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        Direction playerHorizontalFacing = ctx.getPlayerFacing();
        Direction direction_1 = playerHorizontalFacing.getOpposite();
        double xPos = ctx.getHitPos().getX() - blockPos.getX();
        double zPos = ctx.getHitPos().getZ() - blockPos.getZ();
        if (direction_1 == Direction.EAST || direction_1 == Direction.WEST)
        {
            if (xPos > 0.5) direction_1 = Direction.EAST;
            else direction_1 = Direction.WEST;
        }
        else
        {
            if (zPos > 0.5) direction_1 = Direction.SOUTH;
            else direction_1 = Direction.NORTH;
        }
        return blockState.with(SHAPE, getStairShape(blockState, ctx.getWorld(), blockPos)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(FACING, direction_1);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom)
    {
        if (state.get(WATERLOGGED))
        {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return direction.getAxis().isHorizontal() ? state.with(SHAPE, getStairShape(state, world, pos)) : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    private static StairShape getStairShape(BlockState state, BlockView world, BlockPos pos)
    {
        Direction direction = state.get(FACING);
        BlockState blockState = world.getBlockState(pos.offset(direction));
        if (isStairs(blockState) && state.get(HALF) == blockState.get(HALF))
        {
            Direction direction2 = blockState.get(FACING);
            if (direction2.getAxis() != state.get(FACING).getAxis() && method_10678(state, world, pos, direction2.getOpposite()))
            {
                if (direction2 == direction.rotateYCounterclockwise())
                {
                    return StairShape.OUTER_LEFT;
                }
                return StairShape.OUTER_RIGHT;
            }
        }
        BlockState blockState2 = world.getBlockState(pos.offset(direction.getOpposite()));
        if (isStairs(blockState2) && state.get(HALF) == blockState2.get(HALF))
        {
            Direction direction3 = blockState2.get(FACING);
            if (direction3.getAxis() != state.get(FACING).getAxis() && method_10678(state, world, pos, direction3))
            {
                if (direction3 == direction.rotateYCounterclockwise())
                {
                    return StairShape.INNER_LEFT;
                }
                return StairShape.INNER_RIGHT;
            }
        }
        return StairShape.STRAIGHT;
    }

    private static boolean method_10678(BlockState state, BlockView world, BlockPos pos, Direction dir)
    {
        BlockState blockState = world.getBlockState(pos.offset(dir));
        return !isStairs(blockState) || blockState.get(FACING) != state.get(FACING) || blockState.get(HALF) != state.get(HALF);
    }

    public static boolean isStairs(BlockState state)
    {
        return state.getBlock() instanceof QuarterBlock;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation)
    {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror)
    {
        Direction direction = state.get(FACING);
        StairShape stairShape = state.get(SHAPE);
        switch (mirror)
        {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z)
                {
                    switch (stairShape)
                    {
                        case INNER_LEFT:
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                        case INNER_RIGHT:
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                        case OUTER_LEFT:
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                        default:
                            return state.rotate(BlockRotation.CLOCKWISE_180);
                    }
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X)
                {
                    switch (stairShape)
                    {
                        case INNER_LEFT:
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                        case INNER_RIGHT:
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                        case OUTER_LEFT:
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                        case STRAIGHT:
                            return state.rotate(BlockRotation.CLOCKWISE_180);
                    }
                }
                break;
            default:
                break;
        }
        return super.mirror(state, mirror);
    }

    /**
     * Definition of block properties.
     **/

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, HALF, SHAPE, WATERLOGGED);
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
        FACING = HorizontalFacingBlock.FACING;
        HALF = Properties.BLOCK_HALF;
        SHAPE = Properties.STAIR_SHAPE;
        WATERLOGGED = Properties.WATERLOGGED;
        TOP_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        TOP_SHAPES = composeShapes(TOP_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE, BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
        BOTTOM_SHAPES = composeShapes(BOTTOM_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        SHAPE_INDICES = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    }
}