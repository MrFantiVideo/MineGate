package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minegate.fr.moreblocks.block.enums.FernType;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SeaPickleBlock.class)
public class SeaPickleBlockMixin
{
    private static final EnumProperty<FernType> FERN_TYPE;

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
        FERN_TYPE = net.minegate.fr.moreblocks.state.Properties.FERN_TYPE;
    }
}