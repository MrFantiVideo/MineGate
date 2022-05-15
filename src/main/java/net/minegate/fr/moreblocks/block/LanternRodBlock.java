package net.minegate.fr.moreblocks.block;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minegate.fr.moreblocks.state.BlockStateHelper;

import java.util.HashMap;
import java.util.Map;

public class LanternRodBlock extends Block implements Waterloggable
{
    public static final    BooleanProperty                 UP;
    public static final    BooleanProperty                 DOWN;
    public static final    BooleanProperty                 NORTH;
    public static final    BooleanProperty                 EAST;
    public static final    BooleanProperty                 SOUTH;
    public static final    BooleanProperty                 WEST;
    public static final    BooleanProperty                 WATERLOGGED;
    protected static final VoxelShape                      EMPTY_SHAPE;
    protected static final VoxelShape                      UP_SHAPE;
    protected static final VoxelShape                      DOWN_SHAPE;
    protected static final VoxelShape                      NORTH_SHAPE;
    protected static final VoxelShape                      EAST_SHAPE;
    protected static final VoxelShape                      SOUTH_SHAPE;
    protected static final VoxelShape                      WEST_SHAPE;
    protected static final Map<Direction, BooleanProperty> FACING_PROPERTIES;
    protected static final Map<Integer, VoxelShape>        VoxelShapeCache = new HashMap<Integer, VoxelShape>();

    /**
     * Creation of a lantern rod block.
     *
     * @param settings Block settings.
     **/

    public LanternRodBlock(Settings settings)
    {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(UP, Boolean.TRUE).with(DOWN, Boolean.TRUE).with(NORTH, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(WEST, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Consideration of placement position.
     */

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        boolean up = Boolean.TRUE;
        boolean down = Boolean.TRUE;
        boolean east = Boolean.FALSE;
        boolean north = Boolean.FALSE;
        boolean west = Boolean.FALSE;
        boolean south = Boolean.FALSE;

        BlockState blockStateUp = world.getBlockState(pos.up());
        BlockState blockStateDown = world.getBlockState(pos.down());
        BlockState blockStateNorth = world.getBlockState(pos.north());
        BlockState blockStateEast = world.getBlockState(pos.east());
        BlockState blockStateSouth = world.getBlockState(pos.south());
        BlockState blockStateWest = world.getBlockState(pos.west());

        Block blockUp = blockStateUp.getBlock();
        Block blockDown = blockStateDown.getBlock();
        Block blockNorth = blockStateNorth.getBlock();
        Block blockEast = blockStateEast.getBlock();
        Block blockSouth = blockStateSouth.getBlock();
        Block blockWest = blockStateWest.getBlock();

        if (!(blockUp instanceof LanternRodBlock) && !(blockUp instanceof HorizontalConnectingBlock) && !cannotConnect(blockStateUp) && VoxelShapes.matchesAnywhere(blockStateUp.getSidesShape(world, pos.up()).getFace(Direction.DOWN), DOWN_SHAPE, BooleanBiFunction.ONLY_SECOND))
        {
            up = false;
        }
        if (!(blockDown instanceof LanternRodBlock) && !(blockDown instanceof HorizontalConnectingBlock) && !cannotConnect(blockStateDown) && VoxelShapes.matchesAnywhere(blockStateDown.getSidesShape(world, pos.down()).getFace(Direction.UP), UP_SHAPE, BooleanBiFunction.ONLY_SECOND))
        {
            down = false;
        }
        if (!cannotConnect(blockStateEast) && blockStateEast.isSideSolidFullSquare(world, pos.east(), Direction.WEST))
        {
            east = true;
            if (blockEast instanceof LanternRodBlock)
            {
                world.setBlockState(pos.east(), blockStateEast.with(WEST, Boolean.TRUE));
            }
        }
        if (!cannotConnect(blockStateNorth) && blockStateNorth.isSideSolidFullSquare(world, pos.north(), Direction.SOUTH))
        {
            north = true;
            if (blockNorth instanceof LanternRodBlock)
            {
                world.setBlockState(pos.north(), blockStateNorth.with(SOUTH, Boolean.TRUE));
            }
        }
        if (!cannotConnect(blockStateWest) && blockStateWest.isSideSolidFullSquare(world, pos.west(), Direction.EAST))
        {
            west = true;
            if (blockWest instanceof LanternRodBlock)
            {
                world.setBlockState(pos.west(), blockStateWest.with(EAST, Boolean.TRUE));
            }
        }
        if (!cannotConnect(blockStateSouth) && blockStateSouth.isSideSolidFullSquare(world, pos.south(), Direction.NORTH))
        {
            south = true;
            if (blockSouth instanceof LanternRodBlock)
            {
                world.setBlockState(pos.south(), blockStateSouth.with(NORTH, Boolean.TRUE));
            }
        }
        return getDefaultState().with(UP, up).with(DOWN, down).with(EAST, east).with(NORTH, north).with(WEST, west).with(SOUTH, south).with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
    }

    /**
     * Allows you to update the surrounding blocks.
     **/

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
    {
        if (state.get(WATERLOGGED))
        {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return direction.getAxis().isHorizontal() || direction.getAxis().isVertical() ? state.with(FACING_PROPERTIES.get(direction), this.connectsTo(neighborState, neighborState.isSideSolidFullSquare(world, neighborPos, direction.getOpposite()))) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    /**
     * See if he is connecting the blocks together.
     **/

    public final boolean connectsTo(BlockState state, boolean sideSolidFullSquare)
    {
        return !cannotConnect(state) && sideSolidFullSquare || state.getBlock() instanceof LanternRodBlock;
    }

    /**
     * Custom outlines of the block.
     **/

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        int voxelShapeIndex = BlockStateHelper.getVoxelShapeIndex(state);
        if (VoxelShapeCache.containsKey(voxelShapeIndex))
            return VoxelShapeCache.get(voxelShapeIndex);

        VoxelShape voxelShapes = VoxelShapes.empty();

        if (state.get(NORTH))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, NORTH_SHAPE);
        }
        if (state.get(EAST))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, EAST_SHAPE);
        }
        if (state.get(SOUTH))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, SOUTH_SHAPE);
        }
        if (state.get(WEST))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, WEST_SHAPE);
        }
        if (state.get(DOWN) || state.equals(state.with(UP, Boolean.FALSE).with(DOWN, Boolean.FALSE).with(NORTH, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(WEST, Boolean.FALSE)))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, DOWN_SHAPE);
        }

        voxelShapes = VoxelShapes.union(voxelShapes, UP_SHAPE);
        VoxelShapeCache.put(voxelShapeIndex, voxelShapes);

        return voxelShapes;
    }

    /**
     * Definition of block properties.
     **/

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(UP, DOWN, EAST, NORTH, SOUTH, WEST, WATERLOGGED);
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
        UP = Properties.UP;
        DOWN = Properties.DOWN;
        EAST = Properties.EAST;
        NORTH = Properties.NORTH;
        SOUTH = Properties.SOUTH;
        WEST = Properties.WEST;
        WATERLOGGED = Properties.WATERLOGGED;
        FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter((entry) ->
                (entry.getKey()).getAxis().isHorizontal() || (entry.getKey()).getAxis().isVertical()).collect(Util.toMap());
        EMPTY_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        UP_SHAPE = Block.createCuboidShape(7.0D, 14.0D, 7.0D, 9.0D, 16.0D, 9.0D);
        DOWN_SHAPE = Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D);
        NORTH_SHAPE = Block.createCuboidShape(7.0D, 14.0D, 0.0D, 9.0D, 16.0D, 7.0D);
        EAST_SHAPE = Block.createCuboidShape(9.0D, 14.0D, 7.0D, 16.0D, 16.0D, 9.0D);
        SOUTH_SHAPE = Block.createCuboidShape(7.0D, 14.0D, 9.0D, 9.0D, 16.0D, 16.0D);
        WEST_SHAPE = Block.createCuboidShape(0.0D, 14.0D, 7.0D, 7.0D, 16.0D, 9.0D);
    }
}