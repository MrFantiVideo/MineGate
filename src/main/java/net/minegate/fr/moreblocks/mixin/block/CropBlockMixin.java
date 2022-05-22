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
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public class CropBlockMixin extends Block
{
    private static final EnumProperty<SlabType> TYPE;
    private static final VoxelShape[]           AGE_TO_BOTTOM_SHAPE;

    public CropBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
    protected void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(floor.isOf(Blocks.FARMLAND) || floor.isOf(net.minegate.fr.moreblocks.block.Blocks.FARMLAND_SLAB));
    }

    @ModifyArg(method = "getAvailableMoisture", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    private static Block getAvailableMoisture(Block block)
    {
        if (block.equals(net.minegate.fr.moreblocks.block.Blocks.FARMLAND_SLAB))
        {
            return block;
        }
        return Blocks.FARMLAND;
    }

    @Inject(at = @At("HEAD"), method = "getOutlineShape", cancellable = true)
    private void getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (block instanceof PlantableSlabBlock || block instanceof SnowySlabBlock)
        {
            if (blockState.equals(blockState.with(TYPE, SlabType.BOTTOM)))
            {
                cir.setReturnValue(AGE_TO_BOTTOM_SHAPE[state.get(CropBlock.AGE)]);
            }
        }
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
        AGE_TO_BOTTOM_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, -6.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, -4.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, -2.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 0.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 2.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 6.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D)};
    }
}