package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.enums.EighthType;

public class EighthBlock extends HorizontalFacingBlock implements Waterloggable
{
    public static final    DirectionProperty        FACING;
    public static final    EnumProperty<EighthType> TYPE;
    public static final    BooleanProperty          WATERLOGGED;
    protected static final VoxelShape               TOP_NORTH_SHAPE;
    protected static final VoxelShape               TOP_EAST_SHAPE;
    protected static final VoxelShape               TOP_SOUTH_SHAPE;
    protected static final VoxelShape               TOP_WEST_SHAPE;
    protected static final VoxelShape               MIDDLE_NORTH_SHAPE;
    protected static final VoxelShape               MIDDLE_EAST_SHAPE;
    protected static final VoxelShape               MIDDLE_SOUTH_SHAPE;
    protected static final VoxelShape               MIDDLE_WEST_SHAPE;
    protected static final VoxelShape               BOTTOM_NORTH_SHAPE;
    protected static final VoxelShape               BOTTOM_EAST_SHAPE;
    protected static final VoxelShape               BOTTOM_SOUTH_SHAPE;
    protected static final VoxelShape               BOTTOM_WEST_SHAPE;
    protected static final VoxelShape               TOP_CENTER_NORTH_SHAPE;
    protected static final VoxelShape               TOP_CENTER_EAST_SHAPE;
    protected static final VoxelShape               TOP_CENTER_SOUTH_SHAPE;
    protected static final VoxelShape               TOP_CENTER_WEST_SHAPE;
    protected static final VoxelShape               MIDDLE_CENTER_TOP_SHAPE;
    protected static final VoxelShape               MIDDLE_CENTER_NORTH_SHAPE;
    protected static final VoxelShape               MIDDLE_CENTER_EAST_SHAPE;
    protected static final VoxelShape               MIDDLE_CENTER_SOUTH_SHAPE;
    protected static final VoxelShape               MIDDLE_CENTER_WEST_SHAPE;
    protected static final VoxelShape               MIDDLE_CENTER_BOTTOM_SHAPE;
    protected static final VoxelShape               BOTTOM_CENTER_NORTH_SHAPE;
    protected static final VoxelShape               BOTTOM_CENTER_EAST_SHAPE;
    protected static final VoxelShape               BOTTOM_CENTER_SOUTH_SHAPE;
    protected static final VoxelShape               BOTTOM_CENTER_WEST_SHAPE;

    /**
     * Creation of an eighth block.
     *
     * @param settings Block settings.
     **/

    public EighthBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TYPE, EighthType.BOTTOM).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Consideration of placement position.
     **/

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        Direction direction = ctx.getPlayerFacing().getOpposite();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        EighthType type = EighthType.BOTTOM_CENTER;

        double x = ctx.getHitPos().getX() - blockPos.getX();
        double y = ctx.getHitPos().getY() - blockPos.getY();
        double z = ctx.getHitPos().getZ() - blockPos.getZ();

        if (y >= 0.7D && y <= 1.0D)
        {
            type = EighthType.TOP;
            if (x >= 0.3D && x <= 0.7D && z >= 0.3D && z <= 0.7D)
            {
                type = EighthType.MIDDLE_CENTER_TOP;
            }
            if (x >= 0.0D && x <= 0.3D && z >= 0.0D && z <= 0.3D)
            {
                direction = Direction.SOUTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.0D && x <= 0.3D && z >= 0.3D && z <= 0.7D)
            {
                direction = Direction.EAST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.TOP_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.0D && x <= 0.3D && z >= 0.7D && z <= 1.0D)
            {
                direction = Direction.EAST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.3D && x <= 0.7D && z >= 0.0D && z <= 0.3D)
            {
                direction = Direction.SOUTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.TOP_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.3D && x <= 0.7D && z >= 0.7D && z <= 1.0D)
            {
                direction = Direction.NORTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.TOP_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.7D && x <= 1.0D && z >= 0.0D && z <= 0.3D)
            {
                direction = Direction.WEST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.7D && x <= 1.0D && z >= 0.3D && z <= 0.7D)
            {
                direction = Direction.WEST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.TOP_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.7D && x <= 1.0D && z >= 0.7D && z <= 1.0D)
            {
                direction = Direction.NORTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
        }
        if (y >= 0.3D && y <= 0.7D)
        {
            type = EighthType.MIDDLE;
            if (x >= 0.3D && x <= 0.7D || z >= 0.3D && z <= 0.7D)
            {
                type = EighthType.MIDDLE_CENTER;
            }
            if (x >= 0.0D && x <= 0.3D && z >= 0.0D && z <= 0.3D)
            {
                direction = Direction.SOUTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.0D && x <= 0.3D && z >= 0.3D && z <= 0.7D)
            {
                direction = Direction.EAST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.MIDDLE_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.0D && x <= 0.3D && z >= 0.7D && z <= 1.0D)
            {
                direction = Direction.EAST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.3D && x <= 0.7D && z >= 0.0D && z <= 0.3D)
            {
                direction = Direction.SOUTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.MIDDLE_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.3D && x <= 0.7D && z >= 0.7D && z <= 1.0D)
            {
                direction = Direction.NORTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.MIDDLE_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.7D && x <= 1.0D && z >= 0.0D && z <= 0.3D)
            {
                direction = Direction.WEST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.7D && x <= 1.0D && z >= 0.3D && z <= 0.7D)
            {
                direction = Direction.WEST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.MIDDLE_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.7D && x <= 1.0D && z >= 0.7D && z <= 1.0D)
            {
                direction = Direction.NORTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
        }
        if (y >= 0.0D && y <= 0.3D)
        {
            type = EighthType.BOTTOM;
            if (x >= 0.3D && x <= 0.7D || z >= 0.3D && z <= 0.7D)
            {
                type = EighthType.MIDDLE_CENTER_BOTTOM;
            }
            if (x >= 0.0D && x <= 0.3D && z >= 0.0D && z <= 0.3D)
            {
                direction = Direction.SOUTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.0D && x <= 0.3D && z >= 0.3D && z <= 0.7D)
            {
                direction = Direction.EAST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.BOTTOM_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.0D && x <= 0.3D && z >= 0.7D && z <= 1.0D)
            {
                direction = Direction.EAST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.3D && x <= 0.7D && z >= 0.0D && z <= 0.3D)
            {
                direction = Direction.SOUTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.BOTTOM_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.3D && x <= 0.7D && z >= 0.7D && z <= 1.0D)
            {
                direction = Direction.NORTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.BOTTOM_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.7D && x <= 1.0D && z >= 0.0D && z <= 0.3D)
            {
                direction = Direction.WEST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.7D && x <= 1.0D && z >= 0.3D && z <= 0.7D)
            {
                direction = Direction.WEST;
                return this.getDefaultState().with(FACING, direction).with(TYPE, EighthType.BOTTOM_CENTER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            if (x >= 0.7D && x <= 1.0D && z >= 0.7D && z <= 1.0D)
            {
                direction = Direction.NORTH;
                return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
        }
        return this.getDefaultState().with(FACING, direction).with(TYPE, type).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    /**
     * Custom outlines of the block.
     **/

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        Direction direction = state.get(FACING);
        EighthType eighthType = state.get(TYPE);
        switch (eighthType)
        {
            case TOP:
                return switch (direction)
                        {
                            case EAST -> TOP_EAST_SHAPE;
                            case SOUTH -> TOP_SOUTH_SHAPE;
                            case WEST -> TOP_WEST_SHAPE;
                            default -> TOP_NORTH_SHAPE;
                        };
            case MIDDLE:
                return switch (direction)
                        {
                            case EAST -> MIDDLE_EAST_SHAPE;
                            case SOUTH -> MIDDLE_SOUTH_SHAPE;
                            case WEST -> MIDDLE_WEST_SHAPE;
                            default -> MIDDLE_NORTH_SHAPE;
                        };
            case BOTTOM:
                return switch (direction)
                        {
                            case EAST -> BOTTOM_EAST_SHAPE;
                            case SOUTH -> BOTTOM_SOUTH_SHAPE;
                            case WEST -> BOTTOM_WEST_SHAPE;
                            default -> BOTTOM_NORTH_SHAPE;
                        };
            case TOP_CENTER:
                return switch (direction)
                        {
                            case EAST -> TOP_CENTER_EAST_SHAPE;
                            case SOUTH -> TOP_CENTER_SOUTH_SHAPE;
                            case WEST -> TOP_CENTER_WEST_SHAPE;
                            default -> TOP_CENTER_NORTH_SHAPE;
                        };
            case MIDDLE_CENTER_TOP:
            {
                return MIDDLE_CENTER_TOP_SHAPE;
            }
            case MIDDLE_CENTER:
                return switch (direction)
                        {
                            case EAST -> MIDDLE_CENTER_EAST_SHAPE;
                            case SOUTH -> MIDDLE_CENTER_SOUTH_SHAPE;
                            case WEST -> MIDDLE_CENTER_WEST_SHAPE;
                            default -> MIDDLE_CENTER_NORTH_SHAPE;
                        };
            case MIDDLE_CENTER_BOTTOM:
            {
                return MIDDLE_CENTER_BOTTOM_SHAPE;
            }
            case BOTTOM_CENTER:
                return switch (direction)
                        {
                            case EAST -> BOTTOM_CENTER_EAST_SHAPE;
                            case SOUTH -> BOTTOM_CENTER_SOUTH_SHAPE;
                            case WEST -> BOTTOM_CENTER_WEST_SHAPE;
                            default -> BOTTOM_CENTER_NORTH_SHAPE;
                        };
        }
        return BOTTOM_NORTH_SHAPE;
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
        FACING = Properties.HORIZONTAL_FACING;
        TYPE = net.minegate.fr.moreblocks.state.Properties.EIGHTH_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        TOP_NORTH_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_EAST_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        TOP_SOUTH_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_WEST_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        MIDDLE_NORTH_SHAPE = Block.createCuboidShape(8.0D, 4.0D, 8.0D, 16.0D, 12.0D, 16.0D);
        MIDDLE_EAST_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 8.0D, 8.0D, 12.0D, 16.0D);
        MIDDLE_SOUTH_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 0.0D, 8.0D, 12.0D, 8.0D);
        MIDDLE_WEST_SHAPE = Block.createCuboidShape(8.0D, 4.0D, 0.0D, 16.0D, 12.0D, 8.0D);
        BOTTOM_NORTH_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_EAST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        BOTTOM_SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_WEST_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        TOP_CENTER_NORTH_SHAPE = Block.createCuboidShape(4.0D, 8.0D, 8.0D, 12.0D, 16.0D, 16.0D);
        TOP_CENTER_EAST_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 4.0D, 8.0D, 16.0D, 12.0D);
        TOP_CENTER_SOUTH_SHAPE = Block.createCuboidShape(4.0D, 8.0D, 0.0D, 12.0D, 16.0D, 8.0D);
        TOP_CENTER_WEST_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 4.0D, 16.0D, 16.0D, 12.0D);
        MIDDLE_CENTER_TOP_SHAPE = Block.createCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        MIDDLE_CENTER_NORTH_SHAPE = Block.createCuboidShape(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D);
        MIDDLE_CENTER_EAST_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D);
        MIDDLE_CENTER_SOUTH_SHAPE = Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D);
        MIDDLE_CENTER_WEST_SHAPE = Block.createCuboidShape(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
        MIDDLE_CENTER_BOTTOM_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
        BOTTOM_CENTER_NORTH_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 8.0D, 12.0D, 8.0D, 16.0D);
        BOTTOM_CENTER_EAST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 4.0D, 8.0D, 8.0D, 12.0D);
        BOTTOM_CENTER_SOUTH_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 8.0D, 8.0D);
        BOTTOM_CENTER_WEST_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 4.0D, 16.0D, 8.0D, 12.0D);
    }
}