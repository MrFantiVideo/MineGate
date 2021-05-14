package net.minegate.fr.moreblocks.block;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.CropBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.block.Block;
import net.minegate.fr.moreblocks.MoreBlocksClient;

public class SlabFarmlandBlock extends SlabGrassPathBlock
{
    public static final EnumProperty<SlabType> TYPE;
    public static final BooleanProperty        WATERLOGGED;
    public static final IntProperty            MOISTURE;

    protected SlabFarmlandBlock(Settings settings)
    {
        super(settings);

        this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, false)).with(MOISTURE, 0));
    }

    // public BlockState getPlacementState(ItemPlacementContext ctx){
    // 	return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? Main.DIRT_SLAB.getDefaultState() : super.getPlacementState(ctx);
    // }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (!state.canPlaceAt(world, pos)) MoreBlocksClient.setToDirt(world, pos);

        else
        {
            int moisture = (Integer) state.get(MOISTURE);

            if (!isWaterNearby(world, pos) && !world.hasRain(pos.up()))
            {
                if (moisture > 0)
                {
                    MoreBlocksClient.dirtParticles(world, pos, 1);

                    world.setBlockState(pos, (BlockState) state.with(TYPE, state.get(TYPE)).with(WATERLOGGED, state.get(WATERLOGGED)).with(MOISTURE, moisture - 1), 2);
                }

                else if (!hasCrop(world, pos)) MoreBlocksClient.setToDirt(world, pos);
            }

            else if (moisture < 7)
            {
                world.spawnParticles(ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 3, 0.25, 0.06, 0.25, 0.1);

                world.setBlockState(pos, (BlockState) state.with(TYPE, state.get(TYPE)).with(WATERLOGGED, state.get(WATERLOGGED)).with(MOISTURE, 7), 2);
            }
        }
    }

    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance)
    {
        if (!world.isClient && world.random.nextFloat() < distance - 0.5F && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F)
        {
            MoreBlocksClient.setToDirt(world, pos);
        }

        super.onLandedUpon(world, pos, entity, distance);
    }

    private static boolean hasCrop(BlockView world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.up()).getBlock();

        return block instanceof CropBlock || block instanceof StemBlock || block instanceof AttachedStemBlock;
    }

    private static boolean isWaterNearby(WorldView world, BlockPos pos)
    {
        if (world.getBlockState(pos).get(WATERLOGGED)) return true;

        Iterator var2 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();

        BlockPos blockPos;
        do
        {
            if (!var2.hasNext()) return false;

            blockPos = (BlockPos) var2.next();
        } while (!world.getFluidState(blockPos).getFluid().isIn(FluidTags.WATER));

        return true;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE, WATERLOGGED, MOISTURE);
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        MOISTURE = Properties.MOISTURE;
    }
}