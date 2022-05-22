package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minegate.fr.moreblocks.block.SpreadableSlabBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SpreadableBlock.class)
public class SpreadableBlockMixin extends SnowyBlock
{
    public SpreadableBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "randomTick")
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci)
    {
        if (!SpreadableSlabBlock.canSurvive(state, world, pos))
        {
            SpreadableSlabBlock.setToDirt(world, pos);
        }
        else
        {
            SpreadableSlabBlock.spreadableTick(state, world, pos, random);
        }
    }
}