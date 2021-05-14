package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.entity.ISitBlock;
import net.minegate.fr.moreblocks.block.entity.SitEntity;
import net.minegate.fr.moreblocks.block.entity.SitManager;

import java.util.stream.Stream;

public class ChairBlock extends HorizontalFacingBlock implements ISitBlock, Waterloggable
{
    public static final  BooleanProperty   WATERLOGGED = Properties.WATERLOGGED;
    private static final DirectionProperty FACING      = HorizontalFacingBlock.FACING;
    private static final VoxelShape        VOXEL_SHAPE_NORTH;
    private static final VoxelShape        VOXEL_SHAPE_SOUTH;
    private static final VoxelShape        VOXEL_SHAPE_WEST;
    private static final VoxelShape        VOXEL_SHAPE_EAST;

    public ChairBlock(Settings blockSettings)
    {
        super(blockSettings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        stateManager.add(Properties.HORIZONTAL_FACING, WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext placementContext)
    {
        FluidState iFluidState = placementContext.getWorld().getFluidState(placementContext.getBlockPos());
        return getDefaultState().with(FACING, placementContext.getPlayerFacing().getOpposite()).with(WATERLOGGED, iFluidState.getFluid() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void fillStateContainer(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        Direction dir = state.get(FACING);
        switch (dir)
        {
            case NORTH:
                return VOXEL_SHAPE_NORTH;
            case SOUTH:
                return VOXEL_SHAPE_SOUTH;
            case EAST:
                return VOXEL_SHAPE_EAST;
            case WEST:
                return VOXEL_SHAPE_WEST;
            default:
                return VoxelShapes.fullCube();
        }
    }

    static
    {
        VOXEL_SHAPE_NORTH = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(3, 0, 11, 5, 6, 13),
                Block.createCuboidShape(3, 8, 11, 5, 16, 13),
                Block.createCuboidShape(11, 0, 11, 13, 6, 13),
                Block.createCuboidShape(11, 8, 11, 13, 16, 13),
                Block.createCuboidShape(5, 13, 12, 11, 14, 13),
                Block.createCuboidShape(5, 10, 12, 11, 11, 13),
                Block.createCuboidShape(3, 0, 3, 5, 6, 5),
                Block.createCuboidShape(11, 0, 3, 13, 6, 5)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        VOXEL_SHAPE_SOUTH = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(11, 0, 3, 13, 6, 5),
                Block.createCuboidShape(11, 8, 3, 13, 16, 5),
                Block.createCuboidShape(3, 0, 3, 5, 6, 5),
                Block.createCuboidShape(3, 8, 3, 5, 16, 5),
                Block.createCuboidShape(5, 13, 3, 11, 14, 4),
                Block.createCuboidShape(5, 10, 3, 11, 11, 4),
                Block.createCuboidShape(11, 0, 11, 13, 6, 13),
                Block.createCuboidShape(3, 0, 11, 5, 6, 13)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        VOXEL_SHAPE_WEST = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(11, 0, 11, 13, 6, 13),
                Block.createCuboidShape(11, 8, 11, 13, 16, 13),
                Block.createCuboidShape(11, 0, 3, 13, 6, 5),
                Block.createCuboidShape(11, 8, 3, 13, 16, 5),
                Block.createCuboidShape(12, 13, 5, 13, 14, 11),
                Block.createCuboidShape(12, 10, 5, 13, 11, 11),
                Block.createCuboidShape(3, 0, 11, 5, 6, 13),
                Block.createCuboidShape(3, 0, 3, 5, 6, 5)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        VOXEL_SHAPE_EAST = Stream.of(
                Block.createCuboidShape(3, 6, 3, 13, 8, 13),
                Block.createCuboidShape(3, 0, 3, 5, 6, 5),
                Block.createCuboidShape(3, 8, 3, 5, 16, 5),
                Block.createCuboidShape(3, 0, 11, 5, 6, 13),
                Block.createCuboidShape(3, 8, 11, 5, 16, 13),
                Block.createCuboidShape(3, 13, 5, 4, 14, 11),
                Block.createCuboidShape(3, 10, 5, 4, 11, 11),
                Block.createCuboidShape(11, 0, 3, 13, 6, 5),
                Block.createCuboidShape(11, 0, 11, 13, 6, 13)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public Vec3d getSitPosition()
    {
        return new Vec3d(0.5D, 0.25D, 0.5D);
    }
}