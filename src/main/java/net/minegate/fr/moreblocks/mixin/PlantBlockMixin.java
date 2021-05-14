package net.minegate.fr.moreblocks.mixin;

import net.minecraft.block.Block;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minegate.fr.moreblocks.MoreBlocksClient;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.block.enums.FernType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(PlantBlock.class)
public class PlantBlockMixin extends Block
{
    private static final EnumProperty<FernType> FERN_TYPE;

    public PlantBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("TAIL"), method = "canPlantOnTop", cancellable = true)
    public void canPlantOnTop(BlockState state, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> info)
    {
        if ((MoreBlocksClient.isDirtType(state.getBlock()) || state.getBlock() == Blocks.GRASS_BLOCK_SLAB) && MoreBlocksClient.hasTopSlab(state))
            info.setReturnValue(true);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(FERN_TYPE);
    }

    static
    {
        FERN_TYPE = net.minegate.fr.moreblocks.state.Properties.FERN_TYPE;
    }
}