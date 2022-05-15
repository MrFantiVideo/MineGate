package net.minegate.fr.moreblocks.block.glass;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.block.QuarterBlock;

public class GlassQuarterBlock extends QuarterBlock
{
    /**
     * Creation of a glass quarter block.
     *
     * @param settings Block settings.
     **/

    public GlassQuarterBlock(Settings settings)
    {
        super(settings);
    }

    /**
     * Set the block is translucent.
     **/

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos)
    {
        return !state.getBlock().equals(Blocks.TINTED_GLASS_QUARTER);
    }

    /**
     * Set the ambient occlusion light level.
     **/

    @Override
    @Environment(EnvType.CLIENT)
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos)
    {
        return 1.0F;
    }
}