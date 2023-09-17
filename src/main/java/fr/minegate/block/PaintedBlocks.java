package fr.minegate.block;

import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class PaintedBlocks
{
    private static final Map<Block, Block> equivalences = new HashMap<>();

    public static void init()
    {
        Block[] woolBlocks = {
                net.minecraft.block.Blocks.BLACK_WOOL,
                net.minecraft.block.Blocks.BLUE_WOOL,
                net.minecraft.block.Blocks.BROWN_WOOL,
                net.minecraft.block.Blocks.CYAN_WOOL,
                net.minecraft.block.Blocks.GRAY_WOOL,
                net.minecraft.block.Blocks.GREEN_WOOL,
                net.minecraft.block.Blocks.LIGHT_BLUE_WOOL,
                net.minecraft.block.Blocks.LIGHT_GRAY_WOOL,
                net.minecraft.block.Blocks.LIME_WOOL,
                net.minecraft.block.Blocks.MAGENTA_WOOL,
                net.minecraft.block.Blocks.ORANGE_WOOL,
                net.minecraft.block.Blocks.PINK_WOOL,
                net.minecraft.block.Blocks.PURPLE_WOOL,
                net.minecraft.block.Blocks.RED_WOOL,
                net.minecraft.block.Blocks.WHITE_WOOL,
                net.minecraft.block.Blocks.YELLOW_WOOL
        };

        for (Block woolBlock : woolBlocks) {
            equivalences.put(woolBlock, Blocks.PAINTED_WOOL);
        }

        Block[] woolcarpetBlocks = {
                net.minecraft.block.Blocks.BLACK_CARPET,
                net.minecraft.block.Blocks.BLUE_CARPET,
                net.minecraft.block.Blocks.BROWN_CARPET,
                net.minecraft.block.Blocks.CYAN_CARPET,
                net.minecraft.block.Blocks.GRAY_CARPET,
                net.minecraft.block.Blocks.GREEN_CARPET,
                net.minecraft.block.Blocks.LIGHT_BLUE_CARPET,
                net.minecraft.block.Blocks.LIGHT_GRAY_CARPET,
                net.minecraft.block.Blocks.LIME_CARPET,
                net.minecraft.block.Blocks.MAGENTA_CARPET,
                net.minecraft.block.Blocks.ORANGE_CARPET,
                net.minecraft.block.Blocks.PINK_CARPET,
                net.minecraft.block.Blocks.PURPLE_CARPET,
                net.minecraft.block.Blocks.RED_CARPET,
                net.minecraft.block.Blocks.WHITE_CARPET,
                net.minecraft.block.Blocks.YELLOW_CARPET
        };

        for (Block woolcarpetBlock : woolcarpetBlocks) {
            equivalences.put(woolcarpetBlock, Blocks.PAINTED_CARPET);
        }

        Block[] terracottaBlocks = {
                net.minecraft.block.Blocks.BLACK_TERRACOTTA,
                net.minecraft.block.Blocks.BLUE_TERRACOTTA,
                net.minecraft.block.Blocks.BROWN_TERRACOTTA,
                net.minecraft.block.Blocks.CYAN_TERRACOTTA,
                net.minecraft.block.Blocks.GRAY_TERRACOTTA,
                net.minecraft.block.Blocks.GREEN_TERRACOTTA,
                net.minecraft.block.Blocks.LIGHT_BLUE_TERRACOTTA,
                net.minecraft.block.Blocks.LIGHT_GRAY_TERRACOTTA,
                net.minecraft.block.Blocks.LIME_TERRACOTTA,
                net.minecraft.block.Blocks.MAGENTA_TERRACOTTA,
                net.minecraft.block.Blocks.ORANGE_TERRACOTTA,
                net.minecraft.block.Blocks.PINK_TERRACOTTA,
                net.minecraft.block.Blocks.PURPLE_TERRACOTTA,
                net.minecraft.block.Blocks.RED_TERRACOTTA,
                net.minecraft.block.Blocks.WHITE_TERRACOTTA,
                net.minecraft.block.Blocks.YELLOW_TERRACOTTA
        };

        for (Block terracottaBlock : terracottaBlocks) {
            equivalences.put(terracottaBlock, Blocks.PAINTED_TERRACOTTA);
        }

        Block[] stainedglassBlocks = {
                net.minecraft.block.Blocks.BLACK_STAINED_GLASS,
                net.minecraft.block.Blocks.BLUE_STAINED_GLASS,
                net.minecraft.block.Blocks.BROWN_STAINED_GLASS,
                net.minecraft.block.Blocks.CYAN_STAINED_GLASS,
                net.minecraft.block.Blocks.GRAY_STAINED_GLASS,
                net.minecraft.block.Blocks.GREEN_STAINED_GLASS,
                net.minecraft.block.Blocks.LIGHT_BLUE_STAINED_GLASS,
                net.minecraft.block.Blocks.LIGHT_GRAY_STAINED_GLASS,
                net.minecraft.block.Blocks.LIME_STAINED_GLASS,
                net.minecraft.block.Blocks.MAGENTA_STAINED_GLASS,
                net.minecraft.block.Blocks.ORANGE_STAINED_GLASS,
                net.minecraft.block.Blocks.PINK_STAINED_GLASS,
                net.minecraft.block.Blocks.PURPLE_STAINED_GLASS,
                net.minecraft.block.Blocks.RED_STAINED_GLASS,
                net.minecraft.block.Blocks.WHITE_STAINED_GLASS,
                net.minecraft.block.Blocks.YELLOW_STAINED_GLASS
        };

        for (Block stainedglassBlock : stainedglassBlocks) {
            equivalences.put(stainedglassBlock, Blocks.PAINTED_STAINED_GLASS);
        }

        equivalences.put(net.minecraft.block.Blocks.OAK_PLANKS, Blocks.PAINTED_OAK_PLANKS);
        equivalences.put(Blocks.OAK_VERTICAL_STAIRS, Blocks.PAINTED_OAK_VERTICAL_STAIRS);
        equivalences.put(net.minecraft.block.Blocks.OAK_STAIRS, Blocks.PAINTED_OAK_STAIRS);
        equivalences.put(Blocks.OAK_VERTICAL_SLAB, Blocks.PAINTED_OAK_VERTICAL_SLAB);
        equivalences.put(net.minecraft.block.Blocks.OAK_SLAB, Blocks.PAINTED_OAK_SLAB);
        equivalences.put(Blocks.OAK_QUARTER, Blocks.PAINTED_OAK_QUARTER);
        equivalences.put(Blocks.OAK_EIGHTH, Blocks.PAINTED_OAK_EIGHTH);

        equivalences.put(net.minecraft.block.Blocks.IRON_BLOCK, Blocks.PAINTED_IRON_BLOCK);
        equivalences.put(Blocks.IRON_BLOCK_VERTICAL_STAIRS, Blocks.PAINTED_IRON_BLOCK_VERTICAL_STAIRS);
        equivalences.put(Blocks.IRON_BLOCK_STAIRS, Blocks.PAINTED_IRON_BLOCK_STAIRS);
        equivalences.put(Blocks.IRON_BLOCK_VERTICAL_SLAB, Blocks.PAINTED_IRON_BLOCK_VERTICAL_SLAB);
        equivalences.put(Blocks.IRON_BLOCK_SLAB, Blocks.PAINTED_IRON_BLOCK_SLAB);
        equivalences.put(Blocks.IRON_BLOCK_QUARTER, Blocks.PAINTED_IRON_BLOCK_QUARTER);
        equivalences.put(Blocks.IRON_BLOCK_EIGHTH, Blocks.PAINTED_IRON_BLOCK_EIGHTH);

        equivalences.put(net.minecraft.block.Blocks.IRON_BARS, Blocks.PAINTED_IRON_BARS);
        equivalences.put(net.minecraft.block.Blocks.IRON_DOOR, Blocks.PAINTED_IRON_DOOR);
        equivalences.put(net.minecraft.block.Blocks.IRON_TRAPDOOR, Blocks.PAINTED_IRON_TRAPDOOR);
        equivalences.put(net.minecraft.block.Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.PAINTED_IRON_PRESSURE_PLATE);
    }

    public static Block get(Block block)
    {
        return equivalences.get(block);
    }
}
