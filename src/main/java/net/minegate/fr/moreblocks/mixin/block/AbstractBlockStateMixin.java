package net.minegate.fr.moreblocks.mixin.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.State;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.PlantableSlabBlock;
import net.minegate.fr.moreblocks.block.SnowySlabBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin extends State<Block, BlockState>
{
    @Final
    @Shadow
    private AbstractBlock.OffsetType offsetType;

    private static final  EnumProperty<SlabType>   TYPE;

    protected AbstractBlockStateMixin(Block owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<BlockState> codec)
    {
        super(owner, entries, codec);
    }

    @Inject(at = @At("RETURN"), method = "getModelOffset", cancellable = true)
    public void getModelOffset(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir)
    {
        Block block = this.getBlock();

        if (block instanceof PlantBlock)
        {
            Block blockDown = world.getBlockState(pos.down()).getBlock();

            if (blockDown instanceof PlantableSlabBlock || blockDown instanceof SnowySlabBlock)
            {
                BlockState blockStateDown = world.getBlockState(pos.down());

                if (blockStateDown.equals(blockStateDown.with(TYPE, SlabType.BOTTOM)))
                {
                    long l = MathHelper.hashCode(pos.getX(), 0, pos.getZ());
                    float f = block.getMaxHorizontalModelOffset();
                    double d = MathHelper.clamp(((double) ((float) (l & 15L) / 15.0F) - 0.5D) * 0.5D, (-f), f);
                    double e = this.offsetType == AbstractBlock.OffsetType.XYZ ? ((double)((float)(l >> 4 & 15L) / 15.0F) - 1.0) * (double)block.getVerticalModelOffsetMultiplier() : 0.0;
                    double g = MathHelper.clamp(((double) ((float) (l >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D, (-f), f);

                    if (block instanceof FlowerBlock || block instanceof FernBlock)
                    {
                        cir.setReturnValue(new Vec3d(d, e - 0.5D, g));
                    }
                    else
                    {
                        cir.setReturnValue(new Vec3d(0.D, e - 0.5D, 0.D));
                    }
                }
            }
            else if (blockDown instanceof TallPlantBlock)
            {
                Block blockDownDown = world.getBlockState(pos.down().down()).getBlock();

                if (blockDownDown instanceof PlantableSlabBlock || blockDownDown instanceof SnowySlabBlock)
                {
                    BlockState blockStateDownDown = world.getBlockState(pos.down().down());

                    if (blockStateDownDown.equals(blockStateDownDown.with(TYPE, SlabType.BOTTOM)))
                    {
                        long l = MathHelper.hashCode(pos.getX(), 0, pos.getZ());
                        double e = this.offsetType == AbstractBlock.OffsetType.XYZ ? ((double)((float)(l >> 4 & 15L) / 15.0F) - 1.0) * (double)block.getVerticalModelOffsetMultiplier() : 0.0;
                        cir.setReturnValue(new Vec3d(0.D, e - 0.5D, 0.D));
                    }
                }
            }
        }
    }

    public Block getBlock()
    {
        return this.owner;
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
    }
}