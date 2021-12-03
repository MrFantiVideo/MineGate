package net.minegate.fr.moreblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.stream.Stream;

public class TableSmallBlock extends HorizontalConnectingBlock
{
    public static final  BooleanProperty NORTH;
    public static final  BooleanProperty EAST;
    public static final  BooleanProperty SOUTH;
    public static final  BooleanProperty WEST;
    private static final VoxelShape      VOXEL_SHAPE_TABLE;

    protected TableSmallBlock(Settings settings)
    {
        super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
    }

    @Override
    protected VoxelShape[] createShapes(float radius1, float radius2, float height1, float offset2, float height2)
    {
        float f = 8.0F - radius1;
        float g = 8.0F + radius1;
        float h = 8.0F - radius2;
        float i = 8.0F + radius2;
        VoxelShape voxelShape = Block.createCuboidShape(0, 6.0D, 0, 16, 8, 16);
        VoxelShape quarterShape1 = Block.createCuboidShape(1, 0, 13, 3, 6, 15);
        VoxelShape quarterShape2 = Block.createCuboidShape(13, 0, 13, 15, 6, 15);
        VoxelShape quarterShape3 = Block.createCuboidShape(13, 0, 1, 15, 6, 3);
        VoxelShape quarterShape4 = Block.createCuboidShape(1, 0, 1, 3, 6, 3);
        VoxelShape[] voxelShapes = new VoxelShape[]{VOXEL_SHAPE_TABLE, VoxelShapes.union(quarterShape3, quarterShape4), VoxelShapes.union(quarterShape3, quarterShape2), quarterShape3, VoxelShapes.union(quarterShape1, quarterShape2), voxelShape, quarterShape2, voxelShape, VoxelShapes.union(quarterShape1, quarterShape4), quarterShape4, voxelShape, voxelShape, quarterShape1, voxelShape, voxelShape, voxelShape};

        for (int j = 0; j < 16; ++j)
        {
            voxelShapes[j] = VoxelShapes.union(voxelShape, voxelShapes[j]);
        }

        return voxelShapes;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.south();
        BlockPos blockPos4 = blockPos.west();
        BlockPos blockPos5 = blockPos.east();
        BlockState blockState = blockView.getBlockState(blockPos2);
        BlockState blockState2 = blockView.getBlockState(blockPos3);
        BlockState blockState3 = blockView.getBlockState(blockPos4);
        BlockState blockState4 = blockView.getBlockState(blockPos5);
        return this.getDefaultState().with(NORTH, this.connectsTo(blockState, blockState.isSideSolidFullSquare(blockView, blockPos2, Direction.SOUTH))).with(SOUTH, this.connectsTo(blockState2, blockState2.isSideSolidFullSquare(blockView, blockPos3, Direction.NORTH))).with(WEST, this.connectsTo(blockState3, blockState3.isSideSolidFullSquare(blockView, blockPos4, Direction.EAST))).with(EAST, this.connectsTo(blockState4, blockState4.isSideSolidFullSquare(blockView, blockPos5, Direction.WEST))).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction)
    {
        if (stateFrom.isOf(this))
        {
            if (!direction.getAxis().isHorizontal())
            {
                return true;
            }

            if ((Boolean) state.get((Property) FACING_PROPERTIES.get(direction)) && (Boolean) stateFrom.get((Property) FACING_PROPERTIES.get(direction.getOpposite())))
            {
                return true;
            }
        }

        return super.isSideInvisible(state, stateFrom, direction);
    }

    public final boolean connectsTo(BlockState state, boolean bl)
    {
        Block block = state.getBlock();
        return !cannotConnect(state) && bl || block instanceof TableSmallBlock || state.isIn(BlockTags.WALLS);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(new Property[]{NORTH, EAST, WEST, SOUTH, WATERLOGGED});
    }

    static
    {
        NORTH = ConnectingBlock.NORTH;
        EAST = ConnectingBlock.EAST;
        SOUTH = ConnectingBlock.SOUTH;
        WEST = ConnectingBlock.WEST;
        VOXEL_SHAPE_TABLE = Stream.of(
                Block.createCuboidShape(0, 6, 0, 16, 8, 16),
                Block.createCuboidShape(1, 0, 1, 3, 6, 3),
                Block.createCuboidShape(13, 0, 1, 15, 6, 3),
                Block.createCuboidShape(13, 0, 13, 15, 6, 15),
                Block.createCuboidShape(1, 0, 13, 3, 6, 15)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}