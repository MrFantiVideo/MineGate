package fr.minegate.block.painted;

import fr.minegate.block.PaintedBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class PaintedSlabBlock extends PaintedBlock implements Waterloggable
{
    public static final    EnumProperty<SlabType> TYPE;
    public static final    BooleanProperty        WATERLOGGED;
    protected static final VoxelShape             BOTTOM_SHAPE;
    protected static final VoxelShape             TOP_SHAPE;

    public PaintedSlabBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, false));
    }

    public boolean hasSidedTransparency(BlockState state) {
        return state.get(TYPE) != SlabType.DOUBLE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{TYPE, WATERLOGGED});
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = (SlabType)state.get(TYPE);
        switch (slabType) {
            case DOUBLE:
                return VoxelShapes.fullCube();
            case TOP:
                return TOP_SHAPE;
            default:
                return BOTTOM_SHAPE;
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this)) {
            return (BlockState)((BlockState)blockState.with(TYPE, SlabType.DOUBLE)).with(WATERLOGGED, false);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = (BlockState)((BlockState)this.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            Direction direction = ctx.getSide();
            return direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double)blockPos.getY() > 0.5)) ? blockState2 : (BlockState)blockState2.with(TYPE, SlabType.TOP);
        }
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        SlabType slabType = (SlabType)state.get(TYPE);
        if (slabType != SlabType.DOUBLE && itemStack.isOf(this.asItem())) {
            if (context.canReplaceExisting()) {
                boolean bl = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5;
                Direction direction = context.getSide();
                if (slabType == SlabType.BOTTOM) {
                    return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
                } else {
                    return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return state.get(TYPE) != SlabType.DOUBLE ? Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState) : false;
    }

    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.get(TYPE) != SlabType.DOUBLE ? Waterloggable.super.canFillWithFluid(world, pos, state, fluid) : false;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        switch (type) {
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

    static {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
        TOP_SHAPE = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
    }
}
