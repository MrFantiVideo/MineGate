package net.minegate.fr.moreblocks.block;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class SlabSpreadableBlock extends SlabPlantableBlock
{
    public final        Block                           baseBlock;
    public static final EnumProperty<SlabType>          TYPE;
    public static final BooleanProperty                 WATERLOGGED;
    public static final BooleanProperty                 SNOWY;

    public SlabSpreadableBlock(Settings settings, Block baseBlock)
    {
        super(settings);
        this.baseBlock = baseBlock;
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false).with(SNOWY, false));
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

        if (topBlock.getBlock() == Blocks.SNOW && (Integer) topBlock.get(SnowBlock.LAYERS) == 1) return true;

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
        return direction != Direction.UP ? super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom) : (BlockState) state.with(SNOWY, newState.isOf(Blocks.SNOW_BLOCK) || newState.isOf(Blocks.SNOW));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        Block topBlock = ctx.getWorld().getBlockState(ctx.getBlockPos().up()).getBlock();

        return (BlockState) (!this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? pushEntitiesUpBeforeBlockChange(this.getDefaultState(), net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB.getDefaultState(), ctx.getWorld(), ctx.getBlockPos()) : super.getPlacementState(ctx)).with(SNOWY, topBlock == Blocks.SNOW_BLOCK || topBlock == Blocks.SNOW);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE, SNOWY, WATERLOGGED);
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        SNOWY = Properties.SNOWY;
    }
}