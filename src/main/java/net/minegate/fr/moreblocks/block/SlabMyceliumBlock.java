package net.minegate.fr.moreblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minegate.fr.moreblocks.MoreBlocksClient;

import java.util.Random;

public class SlabMyceliumBlock extends SlabBlock implements Waterloggable
{
    public static final    EnumProperty<SlabType> TYPE;
    public static final    BooleanProperty        WATERLOGGED;
    protected static final VoxelShape             BOTTOM_SHAPE;
    protected static final VoxelShape             TOP_SHAPE;
    public final           Block                  baseBlock;
    public static final    BooleanProperty        SNOWY;

    public SlabMyceliumBlock(Settings settings, Block baseBlock)
    {
        super(settings);

        this.baseBlock = baseBlock;

        this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, false)).with(SNOWY, false));
    }

    public boolean hasSidedTransparency(BlockState state)
    {
        return state.get(TYPE) != SlabType.DOUBLE;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        SlabType slabType = (SlabType) state.get(TYPE);
        switch (slabType)
        {
            case DOUBLE:
                return VoxelShapes.fullCube();
            case TOP:
                return TOP_SHAPE;
            default:
                return BOTTOM_SHAPE;
        }
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context)
    {
        ItemStack itemStack = context.getStack();
        SlabType slabType = (SlabType) state.get(TYPE);
        if (slabType != SlabType.DOUBLE && itemStack.getItem() == this.asItem())
        {
            if (context.canReplaceExisting())
            {
                boolean bl = context.getHitPos().y - (double) context.getBlockPos().getY() > 0.5D;
                Direction direction = context.getSide();
                if (slabType == SlabType.BOTTOM)
                {
                    return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
                }
                else
                {
                    return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
                }
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
        return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
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

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState blockState, World world, BlockPos blockPos, Random random)
    {
        this.baseBlock.randomDisplayTick(blockState, world, blockPos, random);
    }

    @Override
    public void scheduledTick(BlockState spreader, ServerWorld world, BlockPos pos, Random random)
    {
        if (!canSurvive(spreader, world, pos)) SlabGrassBlock.setToDirt(world, pos);

        else SlabGrassBlock.spreadableTick(spreader, world, pos, random);
    }

    public static boolean canSurvive(BlockState state, WorldView world, BlockPos pos)
    {
        BlockPos posUp = pos.up();
        BlockState topBlock = world.getBlockState(posUp);

        if (topBlock.getBlock() == net.minecraft.block.Blocks.SNOW && (Integer) topBlock.get(SnowBlock.LAYERS) == 1)
            return true;

        else if (state.getBlock() instanceof SlabSpreadableBlock && !topBlock.getMaterial().isSolid() && state.get(TYPE) == SlabType.TOP)
            return true;

        else
        {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, topBlock, posUp, Direction.UP, topBlock.getOpacity(world, posUp));

            return i < world.getMaxLightLevel();
        }
    }

    public static boolean canSpread(BlockState state, WorldView world, BlockPos pos)
    {
        return canSurvive(state, world, pos) && !world.getFluidState(pos.up()).getFluid().isIn(FluidTags.WATER) && !(state.getBlock() instanceof SlabSpreadableBlock && state.get(WATERLOGGED) && state.get(TYPE) == SlabType.BOTTOM);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom)
    {
        return direction != Direction.UP ? super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom) : (BlockState) state.with(SNOWY, newState.isOf(net.minecraft.block.Blocks.SNOW_BLOCK) || newState.isOf(net.minecraft.block.Blocks.SNOW));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        Block topBlock = ctx.getWorld().getBlockState(ctx.getBlockPos().up()).getBlock();

        return (BlockState) (!this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? pushEntitiesUpBeforeBlockChange(this.getDefaultState(), net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB.getDefaultState(), ctx.getWorld(), ctx.getBlockPos()) : super.getPlacementState(ctx)).with(SNOWY, topBlock == net.minecraft.block.Blocks.SNOW_BLOCK || topBlock == net.minecraft.block.Blocks.SNOW);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE, WATERLOGGED, SNOWY);
    }

    static
    {
        SNOWY = Properties.SNOWY;
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}