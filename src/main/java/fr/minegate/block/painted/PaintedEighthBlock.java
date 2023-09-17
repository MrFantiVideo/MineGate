package fr.minegate.block.painted;

import fr.minegate.block.PaintedBlock;
import fr.minegate.state.property.Properties;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class PaintedEighthBlock extends PaintedBlock implements Waterloggable
{
    public static final    BooleanProperty TOP_NORTH;
    public static final    BooleanProperty TOP_EAST;
    public static final    BooleanProperty TOP_SOUTH;
    public static final    BooleanProperty TOP_WEST;
    public static final    BooleanProperty BOTTOM_NORTH;
    public static final    BooleanProperty BOTTOM_EAST;
    public static final    BooleanProperty BOTTOM_SOUTH;
    public static final    BooleanProperty BOTTOM_WEST;
    public static final    BooleanProperty WATERLOGGED;
    protected static final VoxelShape      TOP_NORTH_SHAPE;
    protected static final VoxelShape      TOP_EAST_SHAPE;
    protected static final VoxelShape      TOP_SOUTH_SHAPE;
    protected static final VoxelShape      TOP_WEST_SHAPE;
    protected static final VoxelShape      BOTTOM_NORTH_SHAPE;
    protected static final VoxelShape      BOTTOM_EAST_SHAPE;
    protected static final VoxelShape      BOTTOM_SOUTH_SHAPE;
    protected static final VoxelShape      BOTTOM_WEST_SHAPE;

    /**
     * Creation of an eighth block.
     *
     * @param settings Block settings.
     */

    public PaintedEighthBlock(AbstractBlock.Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(TOP_NORTH, Boolean.FALSE).with(TOP_EAST, Boolean.FALSE).with(TOP_SOUTH, Boolean.FALSE).with(TOP_WEST, Boolean.FALSE).with(BOTTOM_NORTH, Boolean.FALSE).with(BOTTOM_EAST, Boolean.FALSE).with(BOTTOM_SOUTH, Boolean.FALSE).with(BOTTOM_WEST, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Definition of block properties.
     */

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TOP_NORTH, TOP_EAST, TOP_SOUTH, TOP_WEST, BOTTOM_NORTH, BOTTOM_EAST, BOTTOM_SOUTH, BOTTOM_WEST, WATERLOGGED);
    }

    /**
     * Custom outlines of the block.
     */

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        VoxelShape voxelShapes = VoxelShapes.empty();
        if (state.get(TOP_NORTH))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, TOP_NORTH_SHAPE);
        }
        if (state.get(TOP_EAST))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, TOP_EAST_SHAPE);
        }
        if (state.get(TOP_SOUTH))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, TOP_SOUTH_SHAPE);
        }
        if (state.get(TOP_WEST))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, TOP_WEST_SHAPE);
        }
        if (state.get(BOTTOM_NORTH))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, BOTTOM_NORTH_SHAPE);
        }
        if (state.get(BOTTOM_EAST))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, BOTTOM_EAST_SHAPE);
        }
        if (state.get(BOTTOM_SOUTH))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, BOTTOM_SOUTH_SHAPE);
        }
        if (state.get(BOTTOM_WEST))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, BOTTOM_WEST_SHAPE);
        }
        if (isFullCube(state) || voxelShapes.isEmpty())
        {
            voxelShapes = VoxelShapes.fullCube();
        }
        return voxelShapes;
    }

    /**
     * Consideration of placement position.
     */

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        BlockPos pos = ctx.getBlockPos();
        BlockState state = ctx.getWorld().getBlockState(pos);
        Direction direction = ctx.getSide();

        double x = Math.round((ctx.getHitPos().getX() - pos.getX()) * 1000.0) / 1000.0;
        double y = Math.round((ctx.getHitPos().getY() - pos.getY()) * 1000.0) / 1000.0;
        double z = Math.round((ctx.getHitPos().getZ() - pos.getZ()) * 1000.0) / 1000.0;

        if (!state.isOf(this))
        {
            FluidState fluidState = ctx.getWorld().getFluidState(pos);
            state = this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
        if (y >= 0.5 && !((direction == Direction.DOWN) && y != 1))
        {
            if (x >= 0.5D && !((direction == Direction.WEST) && x != 1 && x != 1.5))
            {
                if (z >= 0.5D && !((direction == Direction.NORTH) && z != 1 && z != 1.5))
                {
                    state = state.with(TOP_SOUTH, Boolean.TRUE);
                }
                else
                {
                    state = state.with(TOP_EAST, Boolean.TRUE);
                }
            }
            else
            {
                if (z >= 0.5D && !((direction == Direction.NORTH) && z != 1 && z != 1.5))
                {
                    state = state.with(TOP_WEST, Boolean.TRUE);
                }
                else
                {
                    state = state.with(TOP_NORTH, Boolean.TRUE);
                }
            }
        }
        else
        {
            if (x >= 0.5D && !((direction == Direction.WEST) && x != 1 && x != 1.5))
            {
                if (z >= 0.5D && !((direction == Direction.NORTH) && z != 1 && z != 1.5))
                {
                    state = state.with(BOTTOM_SOUTH, Boolean.TRUE);
                }
                else
                {
                    state = state.with(BOTTOM_EAST, Boolean.TRUE);
                }
            }
            else
            {
                if (z >= 0.5D && !((direction == Direction.NORTH) && z != 1 && z != 1.5))
                {
                    state = state.with(BOTTOM_WEST, Boolean.TRUE);
                }
                else
                {
                    state = state.with(BOTTOM_NORTH, Boolean.TRUE);
                }
            }
        }
        if (isFullCube(state))
        {
            return state.with(WATERLOGGED, Boolean.FALSE);
        }
        return state;
    }

    /**
     * Adds the possibility to change the block already placed by adding the same block.
     */

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context)
    {
        return !(state == this.getPlacementState(context)) && context.getStack().isOf(this.asItem());
    }

    /**
     * Allows to obtain the fluid state.
     */

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    /**
     * Try filling it with fluid.
     */

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState)
    {
        return !isFullCube(state) && Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    /**
     * Check if it can be filled with fluid.
     */

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid)
    {
        return !isFullCube(state) && Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
    }

    /**
     * Check if all states make a complete block.
     */

    private static boolean isFullCube(BlockState state)
    {
        return state.get(TOP_NORTH) && state.get(TOP_EAST) && state.get(TOP_SOUTH) && state.get(TOP_WEST) && state.get(BOTTOM_NORTH) && state.get(BOTTOM_EAST) && state.get(BOTTOM_SOUTH) && state.get(BOTTOM_WEST);
    }

    static
    {
        TOP_NORTH = Properties.TOP_NORTH;
        TOP_EAST = Properties.TOP_EAST;
        TOP_SOUTH = Properties.TOP_SOUTH;
        TOP_WEST = Properties.TOP_WEST;
        BOTTOM_NORTH = Properties.BOTTOM_NORTH;
        BOTTOM_EAST = Properties.BOTTOM_EAST;
        BOTTOM_SOUTH = Properties.BOTTOM_SOUTH;
        BOTTOM_WEST = Properties.BOTTOM_WEST;
        WATERLOGGED = net.minecraft.state.property.Properties.WATERLOGGED;
        TOP_NORTH_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_EAST_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_SOUTH_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_WEST_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        BOTTOM_NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_EAST_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_WEST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
    }
}
