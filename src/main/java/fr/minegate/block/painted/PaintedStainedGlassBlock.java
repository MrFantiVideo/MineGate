package fr.minegate.block.painted;

import fr.minegate.MineGate;
import fr.minegate.block.PaintedBlock;
import fr.minegate.block.entity.PaintedBlockEntity;
import fr.minegate.util.PaintColor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Stainable;
import net.minecraft.client.MinecraftClient;

import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PaintedStainedGlassBlock extends PaintedBlock
{

    public PaintedStainedGlassBlock(AbstractBlock.Settings settings)
    {
        super(settings);
    }

}
