package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minegate.fr.moreblocks.block.enums.SlabVerticalType;

public class SlabVerticalBlock extends Block implements Waterloggable
{
    public static final EnumProperty<SlabVerticalType> TYPE;
    public static final BooleanProperty                WATERLOGGED;
    public static final DirectionProperty              FACING;
    static final        VoxelShape                     VOXEL_SHAPE_NORTH;
    static final        VoxelShape                     VOXEL_SHAPE_SOUTH;
    static final        VoxelShape                     VOXEL_SHAPE_WEST;
    static final        VoxelShape                     VOXEL_SHAPE_EAST;

    public SlabVerticalBlock(Settings blockSettings)
    {
        super(blockSettings);
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabVerticalType.SINGLE).with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    public boolean hasSidedTransparency(BlockState state)
    {
        return state.get(TYPE) != SlabVerticalType.DOUBLE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE, FACING, WATERLOGGED);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        SlabVerticalType slabType_1 = state.get(TYPE);
        Direction facing = state.get(FACING);
        if (slabType_1 == SlabVerticalType.DOUBLE)
        {
            return VoxelShapes.fullCube();
        }
        else
        {
            switch (facing)
            {
                case SOUTH:
                    return VOXEL_SHAPE_SOUTH;
                case EAST:
                    return VOXEL_SHAPE_EAST;
                case WEST:
                    return VOXEL_SHAPE_WEST;
                default:
                    return VOXEL_SHAPE_NORTH;
            }
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        BlockPos blockPos_1 = ctx.getBlockPos();
        BlockState blockState_1 = ctx.getWorld().getBlockState(blockPos_1);
        if (blockState_1.getBlock() == this)
        {
            return blockState_1.with(TYPE, SlabVerticalType.DOUBLE).with(FACING, blockState_1.get(FACING)).with(WATERLOGGED, false);
        }
        else
        {
            FluidState fluidState_1 = ctx.getWorld().getFluidState(blockPos_1);
            Direction playerHorizontalFacing = ctx.getPlayerFacing();
            Direction facing = ctx.getSide();
            double xPos = ctx.getHitPos().getX() - blockPos_1.getX();
            double zPos = ctx.getHitPos().getZ() - blockPos_1.getZ();
            Direction direction_1 = playerHorizontalFacing.getOpposite();
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
            return this.getDefaultState().with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER).with(FACING, direction_1);
        }
    }

    public boolean canReplace(BlockState state, ItemPlacementContext ctx)
    {
        ItemStack itemStack_1 = ctx.getStack();
        SlabVerticalType slabType_1 = state.get(TYPE);
        Direction facing = state.get(FACING);
        if (slabType_1 != SlabVerticalType.DOUBLE && itemStack_1.getItem() == this.asItem())
        {
            if (ctx.canReplaceExisting())
            {
                boolean boolean_1;
                switch (facing)
                {
                    case EAST:
                        boolean_1 = ctx.getHitPos().getX() - (double) ctx.getBlockPos().getX() > 0.5D;
                        break;
                    case WEST:
                        boolean_1 = ctx.getHitPos().getX() - (double) ctx.getBlockPos().getX() < 0.5D;
                        break;
                    case SOUTH:
                        boolean_1 = ctx.getHitPos().getZ() - (double) ctx.getBlockPos().getZ() > 0.5D;
                        break;
                    default:
                        boolean_1 = ctx.getHitPos().getZ() - (double) ctx.getBlockPos().getZ() < 0.5D;
                }
                Direction direction_1 = ctx.getSide();
                return direction_1 == facing || boolean_1 && direction_1.getAxis().isVertical();
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState)
    {
        return state.get(TYPE) != SlabVerticalType.DOUBLE && Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid)
    {
        return state.get(TYPE) != SlabVerticalType.DOUBLE && Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom)
    {
        if (state.get(WATERLOGGED))
        {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type)
    {
        switch (type)
        {
            case LAND:
                return false;
            case WATER:
                return world.getFluidState(pos).isIn(FluidTags.WATER);
            case AIR:
                return false;
            default:
                return false;
        }
    }

    static
    {
        TYPE = net.minegate.fr.moreblocks.state.Properties.SLAB_VERTICAL_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        FACING = Properties.HORIZONTAL_FACING;
        VOXEL_SHAPE_NORTH = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        VOXEL_SHAPE_SOUTH = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        VOXEL_SHAPE_EAST = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
        VOXEL_SHAPE_WEST = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}
