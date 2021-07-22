package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowerFeature;
import net.minegate.fr.moreblocks.block.enums.FernType;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;

import java.util.List;
import java.util.Random;

public class SlabGrassBlock extends SlabSpreadableBlock implements Fertilizable
{
    public SlabGrassBlock(Settings blockSettings)
    {
        super(blockSettings, net.minecraft.block.Blocks.GRASS_BLOCK);
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
    {
        return world.getBlockState(pos.up()).isAir();
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
    {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
    {
        growAll(world, random, pos, state);
    }

    public static void growAll(ServerWorld world, Random random, BlockPos pos, BlockState state)
    {
        if (DefaultConfig.get("useMixins"))
        {
            BlockPos blockPos = pos.up();
            BlockState blockState = net.minecraft.block.Blocks.GRASS.getDefaultState();

            label48:
            for (int i = 0; i < 128; ++i)
            {
                BlockPos blockPos2 = blockPos;


                for (int j = 0; j < i / 16; ++j)
                {
                    blockPos2 = blockPos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                    Block groundBlock = world.getBlockState(blockPos2.down()).getBlock();
                    BlockState blockStated = state.getBlock().getDefaultState();
                    BlockState blockStateTop = world.getBlockState(pos.up());

                    if ((groundBlock != Blocks.GRASS_BLOCK_SLAB && groundBlock != net.minecraft.block.Blocks.GRASS_BLOCK) || world.getBlockState(blockPos2).isFullCube(world, blockPos2))
                    {
                        continue label48;
                    }
                    if (blockStated.equals(Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(Properties.SLAB_TYPE, SlabType.BOTTOM)))
                    {
                        if (blockState.canPlaceAt(world, blockPos2))
                            world.setBlockState(blockPos2, blockStateTop.with(net.minegate.fr.moreblocks.state.Properties.FERN_TYPE, FernType.PLANT));
                    }
                    else if (blockStated.equals(Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(Properties.SLAB_TYPE, SlabType.TOP)))
                    {
                        if (blockState.canPlaceAt(world, blockPos2))
                            world.setBlockState(blockPos2, blockStateTop.with(net.minegate.fr.moreblocks.state.Properties.FERN_TYPE, FernType.DEFAULT));
                    }

                }

                BlockState blockState2 = world.getBlockState(blockPos2);

                if (blockState2.getBlock() == blockState.getBlock() && random.nextInt(10) == 0)
                    ((Fertilizable) blockState.getBlock()).grow(world, random, blockPos2, blockState2);

                if (blockState2.isAir())
                {
                    BlockState blockState4;
                    if (random.nextInt(8) == 0)
                    {
                        List<ConfiguredFeature<?, ?>> list = world.getBiome(blockPos2).getGenerationSettings().getFlowerFeatures();

                        if (list.isEmpty()) continue;

                        blockState4 = ((FlowerFeature) list.get(0).feature).getFlowerState(random, blockPos2, list.get(0).config);
                    }

                    else blockState4 = blockState;

                    if (blockState4.canPlaceAt(world, blockPos2)) world.setBlockState(blockPos2, blockState4, 3);
                }
            }
        }
    }

    public static boolean hasTopSlab(BlockState state)
    {
        Block block = state.getBlock();
        return (block instanceof SlabPlantableBlock) && (state.get(SlabPlantableBlock.TYPE) == SlabType.TOP || state.get(SlabPlantableBlock.TYPE) == SlabType.BOTTOM || state.get(SlabPlantableBlock.TYPE) == SlabType.DOUBLE);
    }

    public static boolean isDirtType(Block block)
    {
        return block == Blocks.COARSE_DIRT_SLAB || block == Blocks.DIRT_SLAB || block == Blocks.FARMLAND_SLAB || block == Blocks.PODZOL_SLAB;
    }

    public static void dirtParticles(World world, BlockPos pos, int count)
    {
        if (!world.isClient)
            ((ServerWorld) world).spawnParticles(ParticleTypes.MYCELIUM, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, MathHelper.nextInt(world.random, 1, count), 0.25, 0.02, 0.25, 0.1);
    }

    public static void waterParticles(World world, BlockPos pos, int count)
    {
        if (!world.isClient)
            ((ServerWorld) world).spawnParticles(ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, MathHelper.nextInt(world.random, 1, count), 0.25, 0.02, 0.25, 0.1);
    }

    public static void setToDirt(World world, BlockPos pos)
    {
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof net.minecraft.block.SlabBlock)
            world.setBlockState(pos, Blocks.DIRT_SLAB.getDefaultState().with(net.minecraft.block.SlabBlock.TYPE, state.get(net.minecraft.block.SlabBlock.TYPE)).with(net.minecraft.block.SlabBlock.WATERLOGGED, state.get(net.minecraft.block.SlabBlock.WATERLOGGED)));

        else world.setBlockState(pos, net.minecraft.block.Blocks.DIRT.getDefaultState());

        dirtParticles(world, pos, 3);
    }

    public static void spreadableTick(BlockState spreader, ServerWorld world, BlockPos pos, Random random)
    {
        if (SlabSpreadableBlock.canSurvive(spreader, world, pos) && world.getLightLevel(pos.up()) >= 9)
        {
            for (int x = 0; x < 4; ++x)
            {
                BlockPos randBlockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                BlockState spreadee = world.getBlockState(randBlockPos);

                if (SlabSpreadableBlock.canSpread(spreader, world, randBlockPos))
                {
                    if (spreader.getBlock() == Blocks.GRASS_BLOCK_SLAB && spreadee.getBlock() == net.minecraft.block.Blocks.DIRT)
                    {
                        world.setBlockState(randBlockPos, net.minecraft.block.Blocks.GRASS_BLOCK.getDefaultState());
                    }

                    else if (spreader.getBlock() == Blocks.MYCELIUM_SLAB && spreadee.getBlock() == net.minecraft.block.Blocks.DIRT)
                    {
                        world.setBlockState(randBlockPos, net.minecraft.block.Blocks.MYCELIUM.getDefaultState());
                    }

                    else if ((spreader.getBlock() == net.minecraft.block.Blocks.GRASS_BLOCK || spreader.getBlock() == Blocks.GRASS_BLOCK_SLAB) && spreadee.getBlock() == Blocks.DIRT_SLAB)
                    {
                        world.setBlockState(randBlockPos, Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(net.minecraft.block.SlabBlock.TYPE, spreadee.get(net.minecraft.block.SlabBlock.TYPE)).with(net.minecraft.block.SlabBlock.WATERLOGGED, spreadee.get(net.minecraft.block.SlabBlock.WATERLOGGED)));
                    }

                    else if ((spreader.getBlock() == net.minecraft.block.Blocks.MYCELIUM || spreader.getBlock() == Blocks.MYCELIUM_SLAB) && spreadee.getBlock() == Blocks.DIRT_SLAB)
                    {
                        world.setBlockState(randBlockPos, Blocks.MYCELIUM_SLAB.getDefaultState().with(net.minecraft.block.SlabBlock.TYPE, spreadee.get(net.minecraft.block.SlabBlock.TYPE)).with(net.minecraft.block.SlabBlock.WATERLOGGED, spreadee.get(SlabBlock.WATERLOGGED)));
                    }
                }
            }
        }
    }
}