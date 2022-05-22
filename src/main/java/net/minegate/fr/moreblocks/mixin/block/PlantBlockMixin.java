package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minegate.fr.moreblocks.block.PlantableSlabBlock;
import net.minegate.fr.moreblocks.block.SnowySlabBlock;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(PlantBlock.class)
public class PlantBlockMixin extends Block
{
    public PlantBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
    public void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(floor.getBlock() instanceof PlantableSlabBlock || floor.getBlock() instanceof SnowySlabBlock || floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND));
    }
}