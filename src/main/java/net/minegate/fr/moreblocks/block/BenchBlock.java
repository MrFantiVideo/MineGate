package net.minegate.fr.moreblocks.block;

import net.minecraft.block.*;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minegate.fr.moreblocks.block.enums.BenchLegsType;
import net.minegate.fr.moreblocks.entity.ISitBlock;

import java.util.stream.Stream;

public class BenchBlock extends HorizontalFacingBlock implements ISitBlock, Waterloggable
{
    public static final    DirectionProperty           FACING;
    public static final    EnumProperty<BenchLegsType> TYPE;
    public static final    BooleanProperty             WATERLOGGED;
    protected static final VoxelShape                  X_TOP_SHAPE;
    protected static final VoxelShape                  Z_TOP_SHAPE;
    protected static final VoxelShape                  NORTH_LEGS_SHAPE;
    protected static final VoxelShape                  EAST_LEGS_SHAPE;
    protected static final VoxelShape                  SOUTH_LEGS_SHAPE;
    protected static final VoxelShape                  WEST_LEGS_SHAPE;

    /**
     * Creation of a bench block.
     *
     * @param settings Block settings.
     **/

    public BenchBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TYPE, BenchLegsType.Z_LEGS).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Consideration of placement position.
     **/

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        Direction facing = ctx.getPlayerFacing().getOpposite();
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        BlockState blockStateNorth = world.getBlockState(pos.north());
        BlockState blockStateEast = world.getBlockState(pos.east());
        BlockState blockStateSouth = world.getBlockState(pos.south());
        BlockState blockStateWest = world.getBlockState(pos.west());

        BlockState blockStateNorthNorth = world.getBlockState(pos.north().north());
        BlockState blockStateEastEast = world.getBlockState(pos.east().east());
        BlockState blockStateSouthSouth = world.getBlockState(pos.south().south());
        BlockState blockStateWestWest = world.getBlockState(pos.west().west());

        Block blockNorth = blockStateNorth.getBlock();
        Block blockEast = blockStateEast.getBlock();
        Block blockSouth = blockStateSouth.getBlock();
        Block blockWest = blockStateWest.getBlock();

        Block blockNorthNorth = blockStateNorthNorth.getBlock();
        Block blockEastEast = blockStateEastEast.getBlock();
        Block blockSouthSouth = blockStateSouthSouth.getBlock();
        Block blockWestWest = blockStateWestWest.getBlock();

        if (facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            if ((blockEast instanceof BenchBlock) && (blockStateEast == blockStateEast.with(FACING, facing))
                    && (blockWest instanceof BenchBlock) && (blockStateWest == blockStateWest.with(FACING, facing)))
            {
                if ((blockEastEast instanceof BenchBlock) && (blockStateEastEast == blockStateEastEast.with(FACING, facing)))
                {
                    world.setBlockState(pos.east(), blockStateEast.with(TYPE, BenchLegsType.EMPTY));
                }
                else
                {
                    world.setBlockState(pos.east(), blockStateEast.with(TYPE, BenchLegsType.EAST_LEGS));
                }
                if ((blockWestWest instanceof BenchBlock) && (blockStateWestWest == blockStateWestWest.with(FACING, facing)))
                {
                    world.setBlockState(pos.west(), blockStateWest.with(TYPE, BenchLegsType.EMPTY));
                }
                else
                {
                    world.setBlockState(pos.west(), blockStateWest.with(TYPE, BenchLegsType.WEST_LEGS));
                }
                return getDefaultState().with(FACING, facing).with(TYPE, BenchLegsType.EMPTY);
            }
            else if ((blockEast instanceof BenchBlock) && (blockStateEast == blockStateEast.with(FACING, facing)))
            {
                if ((blockEastEast instanceof BenchBlock) && (blockStateEastEast == blockStateEastEast.with(FACING, facing)))
                {
                    world.setBlockState(pos.east(), blockStateEast.with(TYPE, BenchLegsType.EMPTY));
                }
                else
                {
                    world.setBlockState(pos.east(), blockStateEast.with(TYPE, BenchLegsType.EAST_LEGS));
                }
                return getDefaultState().with(FACING, facing).with(TYPE, BenchLegsType.WEST_LEGS);
            }
            else if ((blockWest instanceof BenchBlock) && (blockStateWest == blockStateWest.with(FACING, facing)))
            {
                if ((blockWestWest instanceof BenchBlock) && (blockStateWestWest == blockStateWestWest.with(FACING, facing)))
                {
                    world.setBlockState(pos.west(), blockStateWest.with(TYPE, BenchLegsType.EMPTY));
                }
                else
                {
                    world.setBlockState(pos.west(), blockStateWest.with(TYPE, BenchLegsType.WEST_LEGS));
                }
                return getDefaultState().with(FACING, facing).with(TYPE, BenchLegsType.EAST_LEGS);
            }
            else
            {
                return getDefaultState().with(FACING, facing).with(TYPE, BenchLegsType.Z_LEGS);
            }
        }
        if (facing == Direction.EAST || facing == Direction.WEST)
        {
            if ((blockNorth instanceof BenchBlock) && (blockStateNorth == blockStateNorth.with(FACING, facing))
                    && (blockSouth instanceof BenchBlock) && (blockStateSouth == blockStateSouth.with(FACING, facing)))
            {
                if ((blockNorthNorth instanceof BenchBlock) && (blockStateNorthNorth == blockStateNorthNorth.with(FACING, facing)))
                {
                    world.setBlockState(pos.north(), blockStateNorth.with(TYPE, BenchLegsType.EMPTY));
                }
                else
                {
                    world.setBlockState(pos.north(), blockStateNorth.with(TYPE, BenchLegsType.NORTH_LEGS));
                }
                if ((blockSouthSouth instanceof BenchBlock) && (blockStateSouthSouth == blockStateSouthSouth.with(FACING, facing)))
                {
                    world.setBlockState(pos.south(), blockStateSouth.with(TYPE, BenchLegsType.EMPTY));
                }
                else
                {
                    world.setBlockState(pos.south(), blockStateSouth.with(TYPE, BenchLegsType.SOUTH_LEGS));
                }
                return getDefaultState().with(FACING, facing).with(TYPE, BenchLegsType.EMPTY);
            }
            else if ((blockNorth instanceof BenchBlock) && (blockStateNorth == blockStateNorth.with(FACING, facing)))
            {
                if ((blockNorthNorth instanceof BenchBlock) && (blockStateNorthNorth == blockStateNorthNorth.with(FACING, facing)))
                {
                    world.setBlockState(pos.north(), blockStateNorth.with(TYPE, BenchLegsType.EMPTY));
                }
                else
                {
                    world.setBlockState(pos.north(), blockStateNorth.with(TYPE, BenchLegsType.NORTH_LEGS));
                }
                return getDefaultState().with(FACING, facing).with(TYPE, BenchLegsType.SOUTH_LEGS);
            }
            else if ((blockSouth instanceof BenchBlock) && (blockStateSouth == blockStateSouth.with(FACING, facing)))
            {
                if ((blockSouthSouth instanceof BenchBlock) && (blockStateSouthSouth == blockStateSouthSouth.with(FACING, facing)))
                {
                    world.setBlockState(pos.south(), blockStateSouth.with(TYPE, BenchLegsType.EMPTY));
                }
                else
                {
                    world.setBlockState(pos.south(), blockStateSouth.with(TYPE, BenchLegsType.SOUTH_LEGS));
                }
                return getDefaultState().with(FACING, facing).with(TYPE, BenchLegsType.NORTH_LEGS);
            }
            else
            {
                return getDefaultState().with(FACING, facing).with(TYPE, BenchLegsType.X_LEGS);
            }
        }
        return getDefaultState();
    }

    /**
     * Consideration of placement position when you break the block.
     **/

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        Direction facing = world.getBlockState(pos).get(FACING);

        BlockState blockStateNorth = world.getBlockState(pos.north());
        BlockState blockStateEast = world.getBlockState(pos.east());
        BlockState blockStateSouth = world.getBlockState(pos.south());
        BlockState blockStateWest = world.getBlockState(pos.west());

        BlockState blockStateNorthNorth = world.getBlockState(pos.north().north());
        BlockState blockStateEastEast = world.getBlockState(pos.east().east());
        BlockState blockStateSouthSouth = world.getBlockState(pos.south().south());
        BlockState blockStateWestWest = world.getBlockState(pos.west().west());

        Block blockNorth = blockStateNorth.getBlock();
        Block blockEast = blockStateEast.getBlock();
        Block blockSouth = blockStateSouth.getBlock();
        Block blockWest = blockStateWest.getBlock();

        Block blockNorthNorth = blockStateNorthNorth.getBlock();
        Block blockEastEast = blockStateEastEast.getBlock();
        Block blockSouthSouth = blockStateSouthSouth.getBlock();
        Block blockWestWest = blockStateWestWest.getBlock();

        if (facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            if ((blockEast instanceof BenchBlock) && (blockStateEast == blockStateEast.with(FACING, facing))
                    && (blockWest instanceof BenchBlock) && (blockStateWest == blockStateWest.with(FACING, facing)))
            {
                if ((blockEastEast instanceof BenchBlock) && (blockStateEastEast == blockStateEastEast.with(FACING, facing)))
                {
                    world.setBlockState(pos.east(), blockStateEast.with(TYPE, BenchLegsType.WEST_LEGS));
                }
                else
                {
                    world.setBlockState(pos.east(), blockStateEast.with(TYPE, BenchLegsType.Z_LEGS));
                }
                if ((blockWestWest instanceof BenchBlock) && (blockStateWestWest == blockStateWestWest.with(FACING, facing)))
                {
                    world.setBlockState(pos.west(), blockStateWest.with(TYPE, BenchLegsType.EAST_LEGS));
                }
                else
                {
                    world.setBlockState(pos.west(), blockStateWest.with(TYPE, BenchLegsType.Z_LEGS));
                }
            }
            else if ((blockEast instanceof BenchBlock) && (blockStateEast == blockStateEast.with(FACING, facing)))
            {
                if ((blockEastEast instanceof BenchBlock) && (blockStateEastEast == blockStateEastEast.with(FACING, facing)))
                {
                    world.setBlockState(pos.east(), blockStateEast.with(TYPE, BenchLegsType.WEST_LEGS));
                }
                else
                {
                    world.setBlockState(pos.east(), blockStateEast.with(TYPE, BenchLegsType.Z_LEGS));
                }
            }
            else if ((blockWest instanceof BenchBlock) && (blockStateWest == blockStateWest.with(FACING, facing)))
            {
                if ((blockWestWest instanceof BenchBlock) && (blockStateWestWest == blockStateWestWest.with(FACING, facing)))
                {
                    world.setBlockState(pos.west(), blockStateWest.with(TYPE, BenchLegsType.EAST_LEGS));
                }
                else
                {
                    world.setBlockState(pos.west(), blockStateWest.with(TYPE, BenchLegsType.Z_LEGS));
                }
            }
        }
        if (facing == Direction.EAST || facing == Direction.WEST)
        {
            if ((blockNorth instanceof BenchBlock) && (blockStateNorth == blockStateNorth.with(FACING, facing))
                    && (blockSouth instanceof BenchBlock) && (blockStateSouth == blockStateSouth.with(FACING, facing)))
            {
                if ((blockNorthNorth instanceof BenchBlock) && (blockStateNorthNorth == blockStateNorthNorth.with(FACING, facing)))
                {
                    world.setBlockState(pos.north(), blockStateNorth.with(TYPE, BenchLegsType.SOUTH_LEGS));
                }
                else
                {
                    world.setBlockState(pos.north(), blockStateNorth.with(TYPE, BenchLegsType.X_LEGS));
                }
                if ((blockSouthSouth instanceof BenchBlock) && (blockStateSouthSouth == blockStateSouthSouth.with(FACING, facing)))
                {
                    world.setBlockState(pos.south(), blockStateSouth.with(TYPE, BenchLegsType.NORTH_LEGS));
                }
                else
                {
                    world.setBlockState(pos.south(), blockStateSouth.with(TYPE, BenchLegsType.X_LEGS));
                }
            }
            else if ((blockNorth instanceof BenchBlock) && (blockStateNorth == blockStateNorth.with(FACING, facing)))
            {
                if ((blockNorthNorth instanceof BenchBlock) && (blockStateNorthNorth == blockStateNorthNorth.with(FACING, facing)))
                {
                    world.setBlockState(pos.north(), blockStateNorth.with(TYPE, BenchLegsType.SOUTH_LEGS));
                }
                else
                {
                    world.setBlockState(pos.north(), blockStateNorth.with(TYPE, BenchLegsType.X_LEGS));
                }
            }
            else if ((blockSouth instanceof BenchBlock) && (blockStateSouth == blockStateSouth.with(FACING, facing)))
            {
                if ((blockSouthSouth instanceof BenchBlock) && (blockStateSouthSouth == blockStateSouthSouth.with(FACING, facing)))
                {
                    world.setBlockState(pos.south(), blockStateSouth.with(TYPE, BenchLegsType.NORTH_LEGS));
                }
                else
                {
                    world.setBlockState(pos.south(), blockStateSouth.with(TYPE, BenchLegsType.X_LEGS));
                }
            }
        }
        this.spawnBreakParticles(world, player, pos, state);
        world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
    }

    /**
     * Custom outlines of the block.
     **/

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        Direction direction = state.get(FACING);
        BenchLegsType type = state.get(TYPE);

        return switch (direction)
                {
                    case NORTH -> switch (type)
                            {
                                case EMPTY -> VoxelShapes.union(Z_TOP_SHAPE);
                                case EAST_LEGS -> VoxelShapes.union(Z_TOP_SHAPE, WEST_LEGS_SHAPE);
                                case WEST_LEGS -> VoxelShapes.union(Z_TOP_SHAPE, EAST_LEGS_SHAPE);
                                default -> VoxelShapes.union(Z_TOP_SHAPE, WEST_LEGS_SHAPE, EAST_LEGS_SHAPE);
                            };
                    case EAST -> switch (type)
                            {
                                case EMPTY -> VoxelShapes.union(X_TOP_SHAPE);
                                case NORTH_LEGS -> VoxelShapes.union(X_TOP_SHAPE, SOUTH_LEGS_SHAPE);
                                case SOUTH_LEGS -> VoxelShapes.union(X_TOP_SHAPE, NORTH_LEGS_SHAPE);
                                default -> VoxelShapes.union(X_TOP_SHAPE, SOUTH_LEGS_SHAPE, NORTH_LEGS_SHAPE);
                            };
                    case SOUTH -> switch (type)
                            {
                                case EMPTY -> VoxelShapes.union(Z_TOP_SHAPE);
                                case EAST_LEGS -> VoxelShapes.union(Z_TOP_SHAPE, WEST_LEGS_SHAPE);
                                case WEST_LEGS -> VoxelShapes.union(Z_TOP_SHAPE, EAST_LEGS_SHAPE);
                                default -> VoxelShapes.union(Z_TOP_SHAPE, EAST_LEGS_SHAPE, WEST_LEGS_SHAPE);
                            };
                    case WEST -> switch (type)
                            {
                                case EMPTY -> VoxelShapes.union(X_TOP_SHAPE);
                                case NORTH_LEGS -> VoxelShapes.union(X_TOP_SHAPE, SOUTH_LEGS_SHAPE);
                                case SOUTH_LEGS -> VoxelShapes.union(X_TOP_SHAPE, NORTH_LEGS_SHAPE);
                                default -> VoxelShapes.union(X_TOP_SHAPE, NORTH_LEGS_SHAPE, SOUTH_LEGS_SHAPE);
                            };
                    default -> VoxelShapes.fullCube();
                };
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
     * Position of player when sitting.
     **/

    @Override
    public Vec3d getSitPosition()
    {
        return new Vec3d(0.5D, 0.40D, 0.5D);
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
        TYPE = net.minegate.fr.moreblocks.state.Properties.BENCHLEGS_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        X_TOP_SHAPE = Block.createCuboidShape(3.0D, 8.0D, 0.0D, 13.0D, 10.0D, 16.0D);
        Z_TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 3.0D, 16.0D, 10.0D, 13.0D);
        NORTH_LEGS_SHAPE = Stream.of(
                Block.createCuboidShape(10.0D, 0.0D, 13.0D, 12.0D, 8.0D, 15.0D),
                Block.createCuboidShape(4.0D, 0.0D, 13.0D, 6.0D, 8.0D, 15.0D)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        EAST_LEGS_SHAPE = Stream.of(
                Block.createCuboidShape(1.0D, 0.0D, 4.0D, 3.0D, 8.0D, 6.0D),
                Block.createCuboidShape(1.0D, 0.0D, 10.0D, 3.0D, 8.0D, 12.0D)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        SOUTH_LEGS_SHAPE = Stream.of(
                Block.createCuboidShape(10.0D, 0.0D, 1.0D, 12.0D, 8.0D, 3.0D),
                Block.createCuboidShape(4.0D, 0.0D, 1.0D, 6.0D, 8.0D, 3.0D)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        WEST_LEGS_SHAPE = Stream.of(
                Block.createCuboidShape(13.0D, 0.0D, 4.0D, 15.0D, 8.0D, 6.0D),
                Block.createCuboidShape(13.0D, 0.0D, 10.0D, 15.0D, 8.0D, 12.0D)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
}