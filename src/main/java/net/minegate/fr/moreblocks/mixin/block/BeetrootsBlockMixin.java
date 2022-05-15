package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.PlantableSlabBlock;
import net.minegate.fr.moreblocks.block.SnowySlabBlock;
import net.minegate.fr.moreblocks.block.enums.FernType;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BeetrootsBlock.class)
public class BeetrootsBlockMixin extends CropBlock
{
    private static final EnumProperty<SlabType> TYPE;
    private static final EnumProperty<FernType> FERN_TYPE;
    private static final VoxelShape[]           AGE_TO_BOTTOM_SHAPE;

    public BeetrootsBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "getOutlineShape", cancellable = true)
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
                    cir.setReturnValue(AGE_TO_BOTTOM_SHAPE[state.get(BeetrootsBlock.AGE)]);
                }
            }
        }
    }

    /**
     * Definition of block properties.
     **/

    @Inject(at = @At("HEAD"), method = "appendProperties")
    public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci)
    {
        if (DefaultConfig.get("useMixins"))
        {
            builder.add(FERN_TYPE);
        }
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
        FERN_TYPE = net.minegate.fr.moreblocks.state.Properties.FERN_TYPE;
        AGE_TO_BOTTOM_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, -6.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, -4.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, -2.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 0.0D, 16.0D)};
    }
}