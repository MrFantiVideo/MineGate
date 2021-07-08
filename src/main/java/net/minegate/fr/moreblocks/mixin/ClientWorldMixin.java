package net.minegate.fr.moreblocks.mixin;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minegate.fr.moreblocks.block.enums.FernType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

@Mixin(ClientWorld.class)
@Environment(EnvType.CLIENT)
public abstract class ClientWorldMixin extends World
{
    private static final EnumProperty<SlabType> TYPE;
    private static final EnumProperty<FernType> FERN_TYPE;

    protected ClientWorldMixin(MutableWorldProperties mutableWorldProperties, RegistryKey<World> registryKey, RegistryKey<DimensionType> registryKey2, DimensionType dimensionType, Supplier<Profiler> profiler, boolean bl, boolean bl2, long l)
    {
        super(mutableWorldProperties, registryKey, dimensionType, profiler, bl, bl2, l);
    }

    @Shadow
    @Final
    private MinecraftClient client;

    //@Inject(method = "randomBlockDisplayTick", at = @At("RETURN"))
    //private void randomBlockDisplayTick(int centerX, int centerY, int centerZ, int radius, Random random, ClientWorld.BlockParticle blockParticle, Mutable pos, CallbackInfo ci)
    //{
    //    int i = centerX + this.random.nextInt(radius) - this.random.nextInt(radius);
    //    int j = centerY + this.random.nextInt(radius) - this.random.nextInt(radius);
    //    int k = centerZ + this.random.nextInt(radius) - this.random.nextInt(radius);
    //    pos.set(i, j, k);
    //    Block block = this.getBlockState(pos).getBlock();
    //    BlockState blockState = this.getBlockState(pos);
    //    BlockState state = this.getBlockState(pos.down());
    //    assert this.client.player != null;
    //
    //    if (block instanceof PlantBlock)
    //    {
    //        if (state.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
    //                state.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
    //                state.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
    //                state.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)))
    //        {
    //            this.setBlockState(pos, blockState.with(FERN_TYPE, FernType.PLANT));
    //        }
    //    }
    //}

    static
    {
        TYPE = Properties.SLAB_TYPE;
        FERN_TYPE = net.minegate.fr.moreblocks.state.Properties.FERN_TYPE;
    }
}