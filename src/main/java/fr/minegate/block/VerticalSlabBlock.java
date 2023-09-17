package fr.minegate.block;

import fr.minegate.block.enums.VerticalSlabType;
import fr.minegate.state.property.Properties;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class VerticalSlabBlock extends Block implements Waterloggable
{
    public static final    EnumProperty<VerticalSlabType> TYPE;
    public static final    BooleanProperty                WATERLOGGED;
    protected static final VoxelShape                     NORTH_SHAPE;
    protected static final VoxelShape                     EAST_SHAPE;
    protected static final VoxelShape                     SOUTH_SHAPE;
    protected static final VoxelShape                     WEST_SHAPE;


    /**
     * Creation of a vertical slab block.
     *
     * @param settings Block settings.
     */

    public VerticalSlabBlock(AbstractBlock.Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(TYPE, VerticalSlabType.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Definition of block properties.
     */

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE, WATERLOGGED);
    }

    /**
     * Custom outlines of the block.
     */

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        VerticalSlabType type = state.get(TYPE);
        return switch (type)
                {
                    case NORTH -> NORTH_SHAPE;
                    case EAST -> EAST_SHAPE;
                    case SOUTH -> SOUTH_SHAPE;
                    case WEST -> WEST_SHAPE;
                    default -> VoxelShapes.fullCube();
                };
    }

    /**
     * Consideration of placement position.
     */

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);

        double x = ctx.getHitPos().getX() - blockPos.getX();
        double z = ctx.getHitPos().getZ() - blockPos.getZ();

        if (!blockState.isOf(this))
        {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            blockState = this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
        else if (blockState != blockState.with(TYPE, VerticalSlabType.DOUBLE))
        {
            return blockState.with(TYPE, VerticalSlabType.DOUBLE);
        }

        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();

        if (direction == Direction.EAST || direction == Direction.WEST)
        {
            if (x >= 0.5 && x <= 1)
            {
                return blockState.with(TYPE, VerticalSlabType.EAST);
            }
            return blockState.with(TYPE, VerticalSlabType.WEST);
        }
        else
        {
            if (z >= 0.5 && x <= 1)
            {
                return blockState.with(TYPE, VerticalSlabType.SOUTH);
            }
            return blockState.with(TYPE, VerticalSlabType.NORTH);
        }
    }

    /**
     * Adds the possibility to change the block already placed by adding the same block.
     */

    public boolean canReplace(BlockState state, ItemPlacementContext context)
    {
        ItemStack itemStack = context.getStack();
        VerticalSlabType verticalSlabType = state.get(TYPE);

        if (verticalSlabType != VerticalSlabType.DOUBLE && itemStack.isOf(this.asItem()))
        {
            if (context.canReplaceExisting())
            {
                boolean EAST = context.getHitPos().x - context.getBlockPos().getX() > 0.5;
                boolean SOUTH = context.getHitPos().z - context.getBlockPos().getZ() > 0.5;

                Direction direction = context.getSide();

                switch (verticalSlabType)
                {
                    case NORTH:
                        return direction == Direction.SOUTH || SOUTH && direction.getAxis().isVertical();
                    case EAST:
                        return direction == Direction.WEST || !EAST && direction.getAxis().isVertical();
                    case SOUTH:
                        return direction == Direction.NORTH || !SOUTH && direction.getAxis().isVertical();
                    case WEST:
                        return direction == Direction.EAST || EAST && direction.getAxis().isVertical();
                    case DOUBLE:
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state)
    {
        return state.get(TYPE) != VerticalSlabType.DOUBLE;
    }

    /**
     * Allows you to make the block waterlogged.
     */

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    /**
     * Allows you to remove the water once the block is full.
     */

    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid)
    {
        return state.get(TYPE) != VerticalSlabType.DOUBLE && Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
    }

    static
    {
        TYPE = Properties.VERTICAL_SLAB_TYPE;
        WATERLOGGED = net.minecraft.state.property.Properties.WATERLOGGED;
        NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        EAST_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        WEST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    }
}
