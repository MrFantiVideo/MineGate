package fr.minegate.client.color.block;

import fr.minegate.block.Blocks;
import fr.minegate.block.entity.PaintedBlockEntity;
import fr.minegate.item.Items;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BlockColors
{
    private static final Map<Block, Integer> blockToColorsSize = new HashMap<>();

    public static void init()
    {
        registerPaintBrushItemColour(Items.PAINT_BRUSH, 1);
        registerPaintBrushItemColour(Items.EYEDROPPER, 1);
        registerPaintBrushItemColour(Items.PAINTED_BOOK, 1);
        registerPaintBrushItemColour(Items.WRITABLE_PAINTED_BOOK, 1);
        registerPaintBrushItemColour(Items.WRITTEN_PAINTED_BOOK, 1);

        registerItemColour(Items.CHRISTMAS_HAT);
        registerItemColour(Items.CLASSY_HAT);

        registerBlockPaintedTintIndex(Blocks.PAINTED_OAK_LANTERN, 3);

        for (Block block : Blocks.PAINTED_BLOCKS)
        {
            registerPaintedBlockColour(block);
        }
    }

    private static void registerBlockPaintedTintIndex(Block paintedOakLantern, int tintIndex)
    {
        blockToColorsSize.put(paintedOakLantern, tintIndex);
    }

    public static void registerPaintedBlockColour(Block block)
    {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> PaintedBlockEntity.getColorBlockAt(world, pos, tintIndex), block);
        registerItemColour(new ItemStack(block).getItem());
    }

    public static void registerItemColour(Item block)
    {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
        {
            NbtCompound tag = stack.getNbt();
            if (tag == null)
            {
                return -1;
            }
            NbtList colorsTag = tag.getList("Colors", NbtType.COMPOUND);
            if (tintIndex < 0 || tintIndex >= colorsTag.size())
            {
                return -1;
            }
            NbtCompound colorTag = colorsTag.getCompound(tintIndex);
            return colorTag.getInt(String.valueOf(tintIndex));
        }, block);
    }


    public static void registerPaintBrushItemColour(Item block, int layer)
    {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
        {
            NbtCompound tag = stack.getNbt();
            if (tag == null)
            {
                return -1;
            }
            if (tintIndex == layer)
            {
                NbtList colorsTag = tag.getList("Colors", NbtType.COMPOUND);
                if (!colorsTag.isEmpty())
                {
                    NbtCompound colorTag = colorsTag.getCompound(0);
                    return colorTag.getInt(String.valueOf(layer - 1));
                }
            }
            return -1;
        }, block);
    }

    public static int getColorsSizeForBlock(Block block)
    {
        return blockToColorsSize.getOrDefault(block, 1);
    }
}
