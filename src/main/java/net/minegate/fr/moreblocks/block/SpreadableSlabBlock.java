package net.minegate.fr.moreblocks.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.SideShapeType;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.block.SnowBlock;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class SpreadableSlabBlock extends SnowySlabBlock
{
    public SpreadableSlabBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public void scheduledTick(BlockState spreader, ServerWorld world, BlockPos pos, Random random)
    {
        if (!canSurvive(spreader, world, pos))
        {
            setToDirt(world, pos);
        }
        else
        {
            SpreadableSlabBlock.spreadableTick(spreader, world, pos, random);
        }
    }

    public static boolean canSurvive(BlockState state, WorldView world, BlockPos pos)
    {
        BlockPos posUp = pos.up();
        BlockState topBlock = world.getBlockState(posUp);

        if (topBlock.getBlock() == net.minecraft.block.Blocks.SNOW && (Integer) topBlock.get(SnowBlock.LAYERS) == 1)
            return true;

        else if (state.getBlock() instanceof SpreadableSlabBlock && !topBlock.isSideSolid(world, pos, Direction.DOWN, SideShapeType.FULL))
            return true;

        else
        {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, topBlock, posUp, Direction.UP, topBlock.getOpacity(world, posUp));

            return i < world.getMaxLightLevel();
        }
    }


    public static void setToDirt(World world, BlockPos pos)
    {
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof net.minecraft.block.SlabBlock)
        {
            world.setBlockState(pos, Blocks.DIRT_SLAB.getStateWithProperties(state));
        }
        else
        {
            world.setBlockState(pos, net.minecraft.block.Blocks.DIRT.getStateWithProperties(state));
        }
    }


    public static boolean canSpread(BlockState state, WorldView world, BlockPos pos)
    {
        return canSurvive(state, world, pos) && !world.getFluidState(pos.up()).getFluid().isIn(FluidTags.WATER);
    }

    public static void spreadableTick(BlockState spreader, ServerWorld world, BlockPos pos, Random random)
    {
        if (SpreadableSlabBlock.canSurvive(spreader, world, pos) && world.getLightLevel(pos.up()) >= 9)
        {
            for (int x = 0; x < 4; ++x)
            {
                BlockPos randBlockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                BlockState spreadee = world.getBlockState(randBlockPos);

                if (SpreadableSlabBlock.canSpread(spreader, world, randBlockPos))
                {
                    if ((spreader.getBlock() == Blocks.GRASS_BLOCK_SLAB || spreader.getBlock() == net.minecraft.block.Blocks.GRASS_BLOCK) && spreadee.getBlock() == net.minecraft.block.Blocks.DIRT)
                    {
                        world.setBlockState(randBlockPos, net.minecraft.block.Blocks.GRASS_BLOCK.getStateWithProperties(spreadee));
                    }

                    if ((spreader.getBlock() == Blocks.MYCELIUM_SLAB || spreader.getBlock() == net.minecraft.block.Blocks.MYCELIUM) && spreadee.getBlock() == net.minecraft.block.Blocks.DIRT)
                    {
                        world.setBlockState(randBlockPos, net.minecraft.block.Blocks.MYCELIUM.getStateWithProperties(spreadee));
                    }

                    if ((spreader.getBlock() == net.minecraft.block.Blocks.GRASS_BLOCK || spreader.getBlock() == Blocks.GRASS_BLOCK_SLAB) && spreadee.getBlock() == Blocks.DIRT_SLAB)
                    {
                        world.setBlockState(randBlockPos, Blocks.GRASS_BLOCK_SLAB.getStateWithProperties(spreadee));
                    }

                    if ((spreader.getBlock() == net.minecraft.block.Blocks.MYCELIUM || spreader.getBlock() == Blocks.MYCELIUM_SLAB) && spreadee.getBlock() == Blocks.DIRT_SLAB)
                    {
                        world.setBlockState(randBlockPos, Blocks.MYCELIUM_SLAB.getStateWithProperties(spreadee));
                    }
                }
            }
        }
    }
}