package net.minegate.fr.moreblocks.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MyceliumSlabBlock extends SpreadableSlabBlock
{
    public MyceliumSlabBlock(Settings settings)
    {
        super(settings);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
    {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextInt(10) == 0)
        {
            double posY = 1.1D;
            if (state.getBlock() instanceof SpreadableSlabBlock)
            {
                if (state.get(TYPE) == SlabType.BOTTOM)
                {
                    posY = 0.6D;
                }
            }
            world.addParticle(ParticleTypes.MYCELIUM, (double) pos.getX() + random.nextDouble(), (double) pos.getY() + posY, (double) pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }
}