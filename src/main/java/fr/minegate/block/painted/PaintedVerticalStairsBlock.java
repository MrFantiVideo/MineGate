package fr.minegate.block.painted;

import fr.minegate.block.PaintedBlock;
import fr.minegate.block.QuarterBlock;
import fr.minegate.block.VerticalSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class PaintedVerticalStairsBlock extends PaintedBlock implements Waterloggable
{
    public static final DirectionProperty FACING;
    public static final BooleanProperty   WATERLOGGED;

    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape WEST_SHAPE;

    public PaintedVerticalStairsBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }


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

        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();


        if (x >= 0.5)
        {
            if (z >= 0.5)
            {
                return blockState.with(FACING, Direction.SOUTH);
            }
            return blockState.with(FACING, Direction.EAST);
        }
        else
        {
            if (z >= 0.5)
            {
                return blockState.with(FACING, Direction.WEST);
            }
            return blockState.with(FACING, Direction.NORTH);
        }
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, net.minecraft.world.BlockView view, BlockPos pos, ShapeContext ctx)
    {
        Direction dir = state.get(FACING);
        switch (dir)
        {
            case EAST:
                return EAST_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return NORTH_SHAPE;
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

    static
    {
        FACING = Properties.HORIZONTAL_FACING;
        WATERLOGGED = Properties.WATERLOGGED;
        NORTH_SHAPE = VoxelShapes.union(PaintedVerticalSlabBlock.NORTH_SHAPE, PaintedQuarterBlock.Y_WEST_SHAPE);
        EAST_SHAPE = VoxelShapes.union(PaintedVerticalSlabBlock.EAST_SHAPE, PaintedQuarterBlock.Y_NORTH_SHAPE);
        SOUTH_SHAPE = VoxelShapes.union(PaintedVerticalSlabBlock.SOUTH_SHAPE, PaintedQuarterBlock.Y_EAST_SHAPE);
        WEST_SHAPE = VoxelShapes.union(PaintedVerticalSlabBlock.WEST_SHAPE, PaintedQuarterBlock.Y_SOUTH_SHAPE);
    }
}