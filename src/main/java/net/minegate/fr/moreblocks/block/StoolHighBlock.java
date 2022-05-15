package net.minegate.fr.moreblocks.block;

import net.minecraft.block.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.stream.Stream;

public class StoolHighBlock extends StoolBlock
{
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape EAST_SHAPE;

    /**
     * Creation of a stool high block.
     *
     * @param settings Block settings.
     **/

    public StoolHighBlock(Settings settings)
    {
        super(settings);
    }

    /**
     * Custom outlines of the block.
     **/

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        Direction dir = state.get(FACING);
        return switch (dir)
                {
                    case NORTH -> NORTH_SHAPE;
                    case SOUTH -> SOUTH_SHAPE;
                    case EAST -> EAST_SHAPE;
                    case WEST -> WEST_SHAPE;
                    default -> VoxelShapes.fullCube();
                };
    }

    /**
     * Position of player when sitting.
     **/

    @Override
    public Vec3d getSitPosition()
    {
        return new Vec3d(0.5D, 0.55D, 0.5D);
    }

    static
    {
        NORTH_SHAPE = Stream.of(
                Block.createCuboidShape(3, 10, 3, 13, 12, 13),
                Block.createCuboidShape(4, 0, 4, 6, 10, 6),
                Block.createCuboidShape(10, 0, 4, 12, 10, 6),
                Block.createCuboidShape(10, 0, 10, 12, 10, 12),
                Block.createCuboidShape(4, 0, 10, 6, 10, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        SOUTH_SHAPE = Stream.of(
                Block.createCuboidShape(3, 10, 3, 13, 12, 13),
                Block.createCuboidShape(4, 0, 4, 6, 10, 6),
                Block.createCuboidShape(10, 0, 4, 12, 10, 6),
                Block.createCuboidShape(10, 0, 10, 12, 10, 12),
                Block.createCuboidShape(4, 0, 10, 6, 10, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        WEST_SHAPE = Stream.of(
                Block.createCuboidShape(3, 10, 3, 13, 12, 13),
                Block.createCuboidShape(4, 0, 4, 6, 10, 6),
                Block.createCuboidShape(10, 0, 4, 12, 10, 6),
                Block.createCuboidShape(10, 0, 10, 12, 10, 12),
                Block.createCuboidShape(4, 0, 10, 6, 10, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        EAST_SHAPE = Stream.of(
                Block.createCuboidShape(3, 10, 3, 13, 12, 13),
                Block.createCuboidShape(4, 0, 4, 6, 10, 6),
                Block.createCuboidShape(10, 0, 4, 12, 10, 6),
                Block.createCuboidShape(10, 0, 10, 12, 10, 12),
                Block.createCuboidShape(4, 0, 10, 6, 10, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}