package fr.minegate.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class QuarterBlock extends Block implements Waterloggable
{
    protected static final EnumProperty<Direction.Axis> AXIS;
    protected static final BooleanProperty              NORTH;
    protected static final BooleanProperty              EAST;
    protected static final BooleanProperty              SOUTH;
    protected static final BooleanProperty              WEST;
    protected static final BooleanProperty              WATERLOGGED;
    protected static final VoxelShape                   X_NORTH_SHAPE;
    protected static final VoxelShape                   X_EAST_SHAPE;
    protected static final VoxelShape                   X_SOUTH_SHAPE;
    protected static final VoxelShape                   X_WEST_SHAPE;
    protected static final VoxelShape                   X_SHAPE;
    protected static final VoxelShape                   Y_NORTH_SHAPE;
    protected static final VoxelShape                   Y_EAST_SHAPE;
    protected static final VoxelShape                   Y_SOUTH_SHAPE;
    protected static final VoxelShape                   Y_WEST_SHAPE;
    protected static final VoxelShape                   Y_SHAPE;
    protected static final VoxelShape                   Z_NORTH_SHAPE;
    protected static final VoxelShape                   Z_EAST_SHAPE;
    protected static final VoxelShape                   Z_SOUTH_SHAPE;
    protected static final VoxelShape                   Z_WEST_SHAPE;
    protected static final VoxelShape                   Z_SHAPE;

    /**
     * Creation of a quarter block.
     *
     * @param settings Block settings.
     **/

    public QuarterBlock(AbstractBlock.Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.Y).with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Definition of block properties.
     **/

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(AXIS, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    /**
     * Custom outlines of the block.
     **/

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        VoxelShape voxelShapes = VoxelShapes.empty();

        if (state == state.with(AXIS, Direction.Axis.X))
        {
            if (state.get(NORTH))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, X_NORTH_SHAPE);
            }
            if (state.get(EAST))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, X_EAST_SHAPE);
            }
            if (state.get(SOUTH))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, X_SOUTH_SHAPE);
            }
            if (state.get(WEST))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, X_WEST_SHAPE);
            }
            if (state == state.with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, X_SHAPE);
            }
        }
        else if (state == state.with(AXIS, Direction.Axis.Y))
        {
            if (state.get(NORTH))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Y_NORTH_SHAPE);
            }
            if (state.get(EAST))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Y_EAST_SHAPE);
            }
            if (state.get(SOUTH))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Y_SOUTH_SHAPE);
            }
            if (state.get(WEST))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Y_WEST_SHAPE);
            }
            if (state == state.with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Y_SHAPE);
            }
        }
        else if (state == state.with(AXIS, Direction.Axis.Z))
        {
            if (state.get(NORTH))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Z_NORTH_SHAPE);
            }
            if (state.get(EAST))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Z_EAST_SHAPE);
            }
            if (state.get(SOUTH))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Z_SOUTH_SHAPE);
            }
            if (state.get(WEST))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Z_WEST_SHAPE);
            }
            if (state == state.with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE))
            {
                voxelShapes = VoxelShapes.union(voxelShapes, Z_SHAPE);
            }
        }
        return voxelShapes;
    }

    /**
     * Consideration of placement position.
     **/

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        BlockPos pos = ctx.getBlockPos();
        BlockState state = ctx.getWorld().getBlockState(pos);
        Direction direction = ctx.getSide();
        Direction playerDirection = ctx.getHorizontalPlayerFacing().getOpposite();

        double x = Math.round((ctx.getHitPos().getX() - pos.getX()) * 1000.0) / 1000.0;
        double y = Math.round((ctx.getHitPos().getY() - pos.getY()) * 1000.0) / 1000.0;
        double z = Math.round((ctx.getHitPos().getZ() - pos.getZ()) * 1000.0) / 1000.0;

        if (!state.isOf(this))
        {
            FluidState fluidState = ctx.getWorld().getFluidState(pos);
            state = this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }

        if (state.get(AXIS) == Direction.Axis.Y && state == state.with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE))
        {
            if (y == 0 || y == 1)
            {
                state = state.with(AXIS, Direction.Axis.Y);
            }
            else if (playerDirection == Direction.EAST || playerDirection == Direction.WEST)
            {
                state = state.with(AXIS, Direction.Axis.Z);
            }
            else if (playerDirection == Direction.NORTH || playerDirection == Direction.SOUTH)
            {
                state = state.with(AXIS, Direction.Axis.X);
            }
        }

        if (state == state.with(AXIS, Direction.Axis.X))
        {
            if (state == isCenterAxis(state))
            {
                if (y >= 0.25D && y <= 0.75D)
                {
                    if (x >= 0.25D && x <= 0.75D)
                    {
                        return isCenterAxis(state);
                    }
                }
            }
            if (y >= 0.5 && !((direction == Direction.DOWN) && y != 1))
            {
                if (z >= 0.5D && !((direction == Direction.NORTH) && z != 1 && z != 1.5))
                {
                    state = state.with(EAST, Boolean.TRUE);
                }
                else
                {
                    state = state.with(NORTH, Boolean.TRUE);
                }
            }
            else
            {
                if (z >= 0.5D && !((direction == Direction.NORTH) && z != 1 && z != 1.5))
                {
                    state = state.with(SOUTH, Boolean.TRUE);
                }
                else
                {
                    state = state.with(WEST, Boolean.TRUE);
                }
            }
        }

        if (state == state.with(AXIS, Direction.Axis.Y))
        {
            if (state == isCenterAxis(state))
            {
                if (x >= 0.25D && x <= 0.75D)
                {
                    if (z >= 0.25D && z <= 0.75D)
                    {
                        return isCenterAxis(state);
                    }
                }
            }
            if (x >= 0.5D && !((direction == Direction.WEST) && x != 1 && x != 1.5))
            {
                if (z >= 0.5D && !((direction == Direction.NORTH) && z != 1 && z != 1.5))
                {
                    state = state.with(SOUTH, Boolean.TRUE);
                }
                else
                {
                    state = state.with(EAST, Boolean.TRUE);
                }
            }
            else
            {
                if (z >= 0.5D && !((direction == Direction.NORTH) && z != 1 && z != 1.5))
                {
                    state = state.with(WEST, Boolean.TRUE);
                }
                else
                {
                    state = state.with(NORTH, Boolean.TRUE);
                }
            }
        }

        if (state == state.with(AXIS, Direction.Axis.Z))
        {
            if (state == isCenterAxis(state))
            {
                if (y >= 0.25D && y <= 0.75D)
                {
                    if (z >= 0.25D && z <= 0.75D)
                    {
                        return isCenterAxis(state);
                    }
                }
            }
            if (y >= 0.5)
            {
                if (x >= 0.5D)
                {
                    state = state.with(SOUTH, Boolean.TRUE);
                }
                else
                {
                    state = state.with(WEST, Boolean.TRUE);
                }
            }
            else
            {
                if (x >= 0.5D)
                {
                    state = state.with(EAST, Boolean.TRUE);
                }
                else
                {
                    state = state.with(NORTH, Boolean.TRUE);
                }
            }
        }
        if (QuarterBlock.isFullCube(state))
        {
            return state.with(WATERLOGGED, Boolean.FALSE);
        }
        return state;
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context)
    {
        return !(state == this.getPlacementState(context)) && context.getStack().isOf(this.asItem()) && state != isCenterAxis(state);
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
     * Check if all states make a complete block.
     */

    private static boolean isFullCube(BlockState state)
    {
        return state.get(NORTH) && state.get(EAST) && state.get(SOUTH) && state.get(WEST);
    }

    private static BlockState isCenterAxis(BlockState state)
    {
        return state.with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE);
    }

    static
    {
        AXIS = Properties.AXIS;
        NORTH = net.minecraft.state.property.Properties.NORTH;
        EAST = net.minecraft.state.property.Properties.EAST;
        SOUTH = net.minecraft.state.property.Properties.SOUTH;
        WEST = net.minecraft.state.property.Properties.WEST;
        WATERLOGGED = net.minecraft.state.property.Properties.WATERLOGGED;

        X_SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        X_EAST_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        X_NORTH_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        X_WEST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        X_SHAPE = Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);

        Y_NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        Y_EAST_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        Y_SOUTH_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        Y_WEST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        Y_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

        Z_EAST_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        Z_SOUTH_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        Z_WEST_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D);
        Z_NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D);
        Z_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    }
}