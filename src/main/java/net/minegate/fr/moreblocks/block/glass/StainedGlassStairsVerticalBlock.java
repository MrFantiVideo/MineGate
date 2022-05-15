package net.minegate.fr.moreblocks.block.glass;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.StairsVerticalBlock;

public class StainedGlassStairsVerticalBlock extends StairsVerticalBlock implements Stainable
{
    private final DyeColor color;

    /**
     * Creation of a stained-glass stairs vertical block.
     *
     * @param settings Block settings.
     **/

    public StainedGlassStairsVerticalBlock(DyeColor color, Settings settings)
    {
        super(settings);
        this.color = color;
    }

    /**
     * Get color with DyeColor.
     **/

    public DyeColor getColor()
    {
        return this.color;
    }

    /**
     * Set the block is translucent.
     **/

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos)
    {
        return true;
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