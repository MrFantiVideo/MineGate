package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.PlantableSlabBlock;
import net.minegate.fr.moreblocks.block.SnowySlabBlock;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FungusBlock.class)
public class FungusBlockMixin extends PlantBlock
{
    private static final EnumProperty<SlabType> TYPE;
    private static final VoxelShape             FUNGUS_SHAPE;

    protected FungusBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "getOutlineShape", cancellable = true)
    private void getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (block instanceof PlantableSlabBlock || block instanceof SnowySlabBlock)
        {
            if (blockState.equals(blockState.with(TYPE, SlabType.BOTTOM)))
            {
                cir.setReturnValue(FUNGUS_SHAPE);
            }
        }
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
        FUNGUS_SHAPE = Block.createCuboidShape(4.0D, -8.0D, 4.0D, 12.0D, 1.0D, 12.0D);
    }
}