package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.PlantableSlabBlock;
import net.minegate.fr.moreblocks.block.SnowySlabBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin
{
    private static final EnumProperty<SlabType> TYPE;

    @Inject(at = @At("HEAD"), method = "getModelOffset", cancellable = true)
    public void getModelOffset(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir)
    {
        Block block = world.getBlockState(pos).getBlock();
        Block blockDown = world.getBlockState(pos.down()).getBlock();
        Block blockDownDown = world.getBlockState(pos.down().down()).getBlock();
        BlockState blockStateDown = world.getBlockState(pos.down());
        BlockState blockStateDownDown = world.getBlockState(pos.down().down());
        AbstractBlock.OffsetType offsetType = block.getOffsetType();

        if (block instanceof PlantBlock)
        {
            long l = MathHelper.hashCode(pos.getX(), 0, pos.getZ());
            float f = block.getMaxHorizontalModelOffset();
            double d = MathHelper.clamp(((double) ((float) (l & 15L) / 15.0F) - 0.5D) * 0.5D, (double) (-f), (double) f);
            double e = offsetType == AbstractBlock.OffsetType.XYZ ? ((double) ((float) (l >> 4 & 15L) / 15.0F) - 1.0D) * (double) block.getVerticalModelOffsetMultiplier() : 0.0D;
            double g = MathHelper.clamp(((double) ((float) (l >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D, (double) (-f), (double) f);

            if (blockDown instanceof PlantableSlabBlock || blockDown instanceof SnowySlabBlock)
            {
                if (blockStateDown.equals(blockStateDown.with(TYPE, SlabType.BOTTOM)))
                {
                    if (block instanceof FlowerBlock)
                    {
                        cir.setReturnValue(new Vec3d(d, e - 0.5D, g));
                    }
                    else
                    {
                        cir.setReturnValue(new Vec3d(0.D, e - 0.5D, 0.D));
                    }
                }
            }
            if (blockDown instanceof TallPlantBlock)
            {
                if (blockDownDown instanceof PlantableSlabBlock || blockDownDown instanceof SnowySlabBlock)
                {
                    if (blockStateDownDown.equals(blockStateDownDown.with(TYPE, SlabType.BOTTOM)))
                    {
                        cir.setReturnValue(new Vec3d(0.D, e - 0.5D, 0.D));
                    }
                }
            }
        }
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
    }
}