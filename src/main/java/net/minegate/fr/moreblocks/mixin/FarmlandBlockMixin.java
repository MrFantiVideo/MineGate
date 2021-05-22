package net.minegate.fr.moreblocks.mixin;

import java.util.Random;

import net.minegate.fr.moreblocks.MoreBlocksClient;
import net.minegate.fr.moreblocks.block.SlabGrassBlock;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin
{
    @Inject(method = "scheduledTick", at = @At(value = "INVOKE", target = "net/minecraft/block/FarmlandBlock.setToDirt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"))
    private void onHydrate(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo callbackInfo)
    {
        SlabGrassBlock.waterParticles(world, pos, 2);
    }

    @Inject(method = "scheduledTick", at = @At(value = "INVOKE", target = "net/minecraft/block/FarmlandBlock.setToDirt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"))
    private void onDehydrate(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo callbackInfo)
    {
        SlabGrassBlock.dirtParticles(world, pos, 1);
    }

    @Inject(method = "scheduledTick", at = @At(value = "INVOKE", target = "net/minecraft/block/FarmlandBlock.setToDirt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"))
    private void onSetToDirt(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo callbackInfo)
    {
        SlabGrassBlock.dirtParticles(world, pos, 3);
    }
}