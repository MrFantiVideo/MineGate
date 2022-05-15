package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
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

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin extends PlantBlock
{
    private static final IntProperty            AGE;
    private static final EnumProperty<SlabType> TYPE;
    private static final EnumProperty<FernType> FERN_TYPE;
    private static final VoxelShape             SLAB_CUBE_SHAPE;
    private static final VoxelShape             SLAB_SMALL_SHAPE;
    private static final VoxelShape             SLAB_LARGE_SHAPE;
    private static final VoxelShape             SMALL_SHAPE;
    private static final VoxelShape             LARGE_SHAPE;

    protected SweetBerryBushBlockMixin(Settings settings)
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

            if (state.get(AGE) == 0)
            {
                if (block instanceof PlantableSlabBlock || block instanceof SnowySlabBlock)
                {
                    if (blockState.equals(blockState.with(TYPE, SlabType.BOTTOM)))
                    {
                        cir.setReturnValue(SLAB_SMALL_SHAPE);
                    }
                    else
                    {
                        cir.setReturnValue(SMALL_SHAPE);
                    }
                }
            }
            else
            {
                if (block instanceof PlantableSlabBlock || block instanceof SnowySlabBlock)
                {
                    if (blockState.equals(blockState.with(TYPE, SlabType.BOTTOM)))
                    {
                        if (state.get(AGE) < 3)
                        {
                            cir.setReturnValue(SLAB_LARGE_SHAPE);
                        }
                        else if (state.get(AGE) == 3)
                        {
                            cir.setReturnValue(SLAB_CUBE_SHAPE);
                        }
                    }
                    else
                    {
                        cir.setReturnValue(state.get(AGE) < 3 ? LARGE_SHAPE : super.getOutlineShape(state, world, pos, context));
                    }
                }
            }
        }
    }

    /**
     * Definition of block properties.
     **/

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci)
    {
        if (DefaultConfig.get("useMixins"))
        {
            builder.add(FERN_TYPE);
        }
    }

    static
    {
        AGE = Properties.AGE_3;
        FERN_TYPE = net.minegate.fr.moreblocks.state.Properties.FERN_TYPE;
        TYPE = Properties.SLAB_TYPE;
        SLAB_SMALL_SHAPE = Block.createCuboidShape(3.0D, -8.0D, 3.0D, 13.0D, 0.0D, 13.0D);
        SLAB_LARGE_SHAPE = Block.createCuboidShape(1.0D, -8.0D, 1.0D, 15.0D, 8.0D, 15.0D);
        SLAB_CUBE_SHAPE = Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        SMALL_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
        LARGE_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    }
}