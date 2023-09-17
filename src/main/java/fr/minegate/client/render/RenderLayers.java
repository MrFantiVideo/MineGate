package fr.minegate.client.render;

import fr.minegate.block.Blocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class RenderLayers
{
    /**
     * Initializes the different render layers for blocks.
     */

    public static void init()
    {
        RenderLayer renderLayer1 = RenderLayer.getCutoutMipped();
        register(Blocks.PAINTED_IRON_BARS, renderLayer1);
        register(Blocks.PAINTED_OAK_LANTERN, renderLayer1);
        RenderLayer renderLayer2 = RenderLayer.getCutout();
        register(Blocks.PAINTED_IRON_DOOR, renderLayer2);
        register(Blocks.PAINTED_IRON_TRAPDOOR, renderLayer2);
        RenderLayer renderLayer3 = RenderLayer.getTranslucent();
        register(Blocks.PORTAL, renderLayer3);
        register(Blocks.PAINTED_STAINED_GLASS, renderLayer3);
        register(Blocks.PAINTED_STAINED_GLASS_PANE, renderLayer3);
    }

    /**
     * Registers a block with a specific render layer.
     *
     * @param block       The block to be registered.
     * @param renderLayer The render layer for the block.
     */

    private static void register(Block block, RenderLayer renderLayer)
    {
        BlockRenderLayerMap.INSTANCE.putBlocks(renderLayer, block);
    }
}
