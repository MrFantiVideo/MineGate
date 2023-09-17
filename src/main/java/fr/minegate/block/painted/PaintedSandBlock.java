package fr.minegate.block.painted;

import fr.minegate.block.painted.PaintedFallingBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class PaintedSandBlock extends PaintedFallingBlock
{
    private final int color;

    public PaintedSandBlock(int color, AbstractBlock.Settings settings)
    {
        super(settings);
        this.color = color;
    }

    public int getColor(BlockState state, BlockView world, BlockPos pos)
    {
        return this.color;
    }
}
