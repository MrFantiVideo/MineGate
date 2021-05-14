package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowerFeature;
import net.minegate.fr.moreblocks.block.enums.FernType;

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
                // PROBLEM WITH FLOATING OR DEEP GRASS. (to fix...)
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