package net.minegate.fr.moreblocks.mixin;

import java.util.Random;

import net.minegate.fr.moreblocks.block.SlabGrassBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

@Mixin(GrassBlock.class)
public class GrassBlockMixin
{
    @Inject(at = @At("TAIL"), method = "grow", cancellable = true)
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo info)
    {
        SlabGrassBlock.growAll(world, random, pos, state);
    }
}