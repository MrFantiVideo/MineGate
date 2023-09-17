package fr.minegate.block.painted;

import fr.minegate.block.painted.PaintedPaneBlock;
import net.minecraft.block.*;
import net.minecraft.util.DyeColor;

public class PaintedStainedGlassPaneBlock extends PaintedPaneBlock implements Stainable
{
    public PaintedStainedGlassPaneBlock(AbstractBlock.Settings settings)
    {
        super(settings);
        this.setDefaultState((BlockState) ((BlockState) ((BlockState) ((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(WATERLOGGED, false));
    }

    @Override
    public DyeColor getColor()
    {
        return null;
    }
}