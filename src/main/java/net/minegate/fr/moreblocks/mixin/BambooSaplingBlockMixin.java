package net.minegate.fr.moreblocks.mixin;

import net.minecraft.block.BambooSaplingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minegate.fr.moreblocks.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BambooSaplingBlock.class)
public class BambooSaplingBlockMixin
{
    private static final EnumProperty<SlabType> TYPE;

    @Inject(at = @At("RETURN"), method = "canPlaceAt", cancellable = true)
    public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (world.getBlockState(pos.down()).isIn(BlockTags.BAMBOO_PLANTABLE_ON))
        {
            Block block = world.getBlockState(pos.down()).getBlock();
            BlockState blockState = world.getBlockState(pos.down());

            if (block.equals(Blocks.GRASS_BLOCK_SLAB) || block.equals(Blocks.DIRT_SLAB) || block.equals(Blocks.COARSE_DIRT_SLAB) || block.equals(Blocks.PODZOL_SLAB) || block.equals(Blocks.MYCELIUM_SLAB) || block.equals(Blocks.ROOTED_DIRT_SLAB) || block.equals(Blocks.SAND_SLAB) || block.equals(Blocks.RED_SAND_SLAB))
            {
                if (blockState.equals(block.getDefaultState().with(TYPE, SlabType.TOP)) || blockState.equals(block.getDefaultState().with(TYPE, SlabType.DOUBLE)))
                {
                    cir.setReturnValue(true);
                }
                else
                {
                    cir.setReturnValue(false);
                }
            }
        }
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
    }
}