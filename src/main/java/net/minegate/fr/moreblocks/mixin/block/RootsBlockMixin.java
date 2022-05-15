package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.block.PlantableSlabBlock;
import net.minegate.fr.moreblocks.block.SnowySlabBlock;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RootsBlock.class)
public class RootsBlockMixin extends PlantBlock
{
    private static final EnumProperty<SlabType> TYPE;
    private static final VoxelShape             ROOTS_SHAPE;

    protected RootsBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "getOutlineShape", cancellable = true)
    private void getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir)
    {
        if (DefaultConfig.get("useMixins"))
        {
            Block block = world.getBlockState(pos.down()).getBlock();
            BlockState blockState = world.getBlockState(pos.down());

            if (block instanceof PlantableSlabBlock || block instanceof SnowySlabBlock)
            {
                if (blockState.equals(blockState.with(TYPE, SlabType.BOTTOM)))
                {
                    cir.setReturnValue(ROOTS_SHAPE);
                }
            }
        }
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
        ROOTS_SHAPE = Block.createCuboidShape(2.0D, -8.0D, 2.0D, 14.0D, 5.0D, 14.0D);
    }
}