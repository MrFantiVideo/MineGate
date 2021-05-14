package net.minegate.fr.moreblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class QuarterStainedBlock extends QuarterBlock implements Stainable
{
    private final DyeColor color;

    public QuarterStainedBlock(DyeColor color, Settings blockSettings)
    {
        super(blockSettings);
        this.color = color;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float getAmbientOcclusionLightLevel(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1)
    {
        return 1.0F;
    }

    @Override
    public boolean isTranslucent(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1)
    {
        return true;
    }

    public DyeColor getColor()
    {
        return this.color;
    }
}
