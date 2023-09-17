package fr.minegate.item;

import fr.minegate.MineGate;
import fr.minegate.block.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.OperatorOnlyBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items
{
    public static Item STONE_VERTICAL_STAIRS;
    public static Item STONE_VERTICAL_SLAB;
    public static Item STONE_QUARTER;
    public static Item STONE_EIGHTH;

    public static Item OAK_VERTICAL_STAIRS;
    public static Item OAK_VERTICAL_SLAB;
    public static Item OAK_QUARTER;
    public static Item OAK_EIGHTH;

    public static Item IRON_BLOCK_VERTICAL_STAIRS;
    public static Item IRON_BLOCK_STAIRS;
    public static Item IRON_BLOCK_VERTICAL_SLAB;
    public static Item IRON_BLOCK_SLAB;
    public static Item IRON_BLOCK_QUARTER;
    public static Item IRON_BLOCK_EIGHTH;


    public static Item PAINTED_IRON_BLOCK;
    public static Item PAINTED_IRON_BLOCK_VERTICAL_STAIRS;
    public static Item PAINTED_IRON_BLOCK_STAIRS;
    public static Item PAINTED_IRON_BLOCK_VERTICAL_SLAB;
    public static Item PAINTED_IRON_BLOCK_SLAB;
    public static Item PAINTED_IRON_BLOCK_QUARTER;
    public static Item PAINTED_IRON_BLOCK_EIGHTH;






    /**
     * List of MineGate operator utilities.
     */

    public static Item PORTAL;

    /**
     * List of MineGate painted blocks.
     */

    public static Item PAINTED_BOOK;
    public static Item WRITABLE_PAINTED_BOOK;
    public static Item WRITTEN_PAINTED_BOOK;

    public static Item PAINTING_PALETTE;
    public static Item EMPTY_PAINTING_PALETTE;
    public static Item PAINT_BRUSH;
    public static Item EYEDROPPER;
    public static Item EMPTY_EYEDROPPER;


    public static Item PAINTED_IRON_BARS;
    public static Item PAINTED_IRON_WALL;
    public static Item PAINTED_IRON_FENCE;
    public static Item PAINTED_IRON_FENCE_GATE;
    public static Item PAINTED_IRON_DOOR;
    public static Item PAINTED_IRON_TRAPDOOR;
    public static Item PAINTED_IRON_PRESSURE_PLATE;
    public static Item PAINTED_IRON_BUTTON;

    public static Item PAINTED_OAK_PLANKS;
    public static Item PAINTED_OAK_VERTICAL_STAIRS;
    public static Item PAINTED_OAK_STAIRS;
    public static Item PAINTED_OAK_VERTICAL_SLAB;
    public static Item PAINTED_OAK_SLAB;
    public static Item PAINTED_OAK_QUARTER;
    public static Item PAINTED_OAK_EIGHTH;


    public static Item PAINTED_WOOL;
    public static Item PAINTED_CARPET;
    public static Item PAINTED_TERRACOTTA;
    public static Item PAINTED_CONCRETE;
    public static Item PAINTED_CONCRETE_POWDER;
    public static Item PAINTED_STAINED_GLASS;
    public static Item PAINTED_STAINED_GLASS_PANE;
    public static Item PAINTED_SHULKER_BOX;
    public static Item PAINTED_BED;
    public static Item PAINTED_CANDLE;
    public static Item PAINTED_BANNER;


    public static Item PAINTED_OAK_LANTERN;
    public static Item PAINTED_OAK_LANTERN_ROD;

    public static Item CHRISTMAS_HAT;
    public static Item CLASSY_HAT;

    /**
     * Initialization.
     */

    public static void init()
    {
        MineGate.log("Loading of the different types of items.");
        MinecraftBlocks();
        PaintedBlocks();
        PaintedItems();
        MineGateOperatorUtilities();
    }

    /**
     * Registration of MineGate items.
     *
     * @param id   String id of the Block.
     * @param item Item from the list of items.
     */

    private static Item MGItemRegister(String id, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(MineGate.name.toLowerCase(), id), item);
    }

    /**
     * Registration of MineGate items.
     *
     * @param id   String id of the Block.
     * @param item Item from the list of items.
     */

    private static Item MGBlockRegister(String id, BlockItem item)
    {
        return Registry.register(Registries.ITEM, new Identifier(MineGate.name.toLowerCase(), id), item);
    }

    /**
     * List of MineGate operator utilities items.
     */

    static void MinecraftBlocks()
    {
        OAK_VERTICAL_STAIRS = MGBlockRegister("oak_vertical_stairs", new BlockItem(Blocks.OAK_VERTICAL_STAIRS, new FabricItemSettings()));
        OAK_VERTICAL_SLAB = MGBlockRegister("oak_vertical_slab", new BlockItem(Blocks.OAK_VERTICAL_SLAB, new FabricItemSettings()));
        OAK_QUARTER = MGBlockRegister("oak_quarter", new BlockItem(Blocks.OAK_QUARTER, new FabricItemSettings()));
        OAK_EIGHTH = MGBlockRegister("oak_eighth", new BlockItem(Blocks.OAK_EIGHTH, new FabricItemSettings()));

        STONE_VERTICAL_STAIRS = MGBlockRegister("stone_vertical_stairs", new BlockItem(Blocks.STONE_VERTICAL_STAIRS, new FabricItemSettings()));
        STONE_VERTICAL_SLAB = MGBlockRegister("stone_vertical_slab", new BlockItem(Blocks.STONE_VERTICAL_SLAB, new FabricItemSettings()));
        STONE_QUARTER = MGBlockRegister("stone_quarter", new BlockItem(Blocks.STONE_QUARTER, new FabricItemSettings()));
        STONE_EIGHTH = MGBlockRegister("stone_eighth", new BlockItem(Blocks.STONE_EIGHTH, new FabricItemSettings()));

        IRON_BLOCK_VERTICAL_STAIRS = MGBlockRegister("iron_block_vertical_stairs", new BlockItem(Blocks.IRON_BLOCK_VERTICAL_STAIRS, new FabricItemSettings()));
        IRON_BLOCK_STAIRS = MGBlockRegister("iron_block_stairs", new BlockItem(Blocks.IRON_BLOCK_STAIRS, new FabricItemSettings()));
        IRON_BLOCK_VERTICAL_SLAB = MGBlockRegister("iron_block_vertical_slab", new BlockItem(Blocks.IRON_BLOCK_VERTICAL_SLAB, new FabricItemSettings()));
        IRON_BLOCK_SLAB = MGBlockRegister("iron_block_slab", new BlockItem(Blocks.IRON_BLOCK_SLAB, new FabricItemSettings()));
        IRON_BLOCK_QUARTER = MGBlockRegister("iron_block_quarter", new BlockItem(Blocks.IRON_BLOCK_QUARTER, new FabricItemSettings()));
        IRON_BLOCK_EIGHTH = MGBlockRegister("iron_block_eighth", new BlockItem(Blocks.IRON_BLOCK_EIGHTH, new FabricItemSettings()));
    }

    static void MineGateOperatorUtilities()
    {
        PORTAL = MGBlockRegister("portal", new OperatorOnlyBlockItem(Blocks.PORTAL, new FabricItemSettings()));
    }

    static void PaintedBlocks()
    {
        PAINTED_BOOK = MGItemRegister("painted_book", new PaintedBookItem(new FabricItemSettings().maxCount(1)));
        WRITABLE_PAINTED_BOOK = MGItemRegister("writable_painted_book", new PaintedWritableBookItem(new FabricItemSettings().maxCount(1)));
        WRITTEN_PAINTED_BOOK = MGItemRegister("written_painted_book", new PaintedWrittenBookItem(new FabricItemSettings().maxCount(1)));

        PAINTING_PALETTE = MGItemRegister("painting_palette", new PaintingPaletteItem(new FabricItemSettings().maxCount(1)));
        EMPTY_PAINTING_PALETTE = MGItemRegister("empty_painting_palette", new Item(new FabricItemSettings().maxCount(16)));
        PAINT_BRUSH = MGItemRegister("paint_brush", new PaintBrushItem(new FabricItemSettings().maxCount(1).maxDamage(128)));
        EYEDROPPER = MGItemRegister("eyedropper", new PaintBrushItem(new FabricItemSettings().maxCount(1)));
        EMPTY_EYEDROPPER = MGItemRegister("empty_eyedropper", new Item(new FabricItemSettings().maxCount(1)));



        PAINTED_IRON_BLOCK = MGBlockRegister("painted_iron_block", new PaintedBlockItem(Blocks.PAINTED_IRON_BLOCK, new FabricItemSettings()));
        PAINTED_IRON_BLOCK_VERTICAL_STAIRS = MGBlockRegister("painted_iron_block_vertical_stairs", new PaintedBlockItem(Blocks.PAINTED_IRON_BLOCK_VERTICAL_STAIRS, new FabricItemSettings()));
        PAINTED_IRON_BLOCK_STAIRS = MGBlockRegister("painted_iron_block_stairs", new PaintedBlockItem(Blocks.PAINTED_IRON_BLOCK_STAIRS, new FabricItemSettings()));
        PAINTED_IRON_BLOCK_VERTICAL_SLAB = MGBlockRegister("painted_iron_block_vertical_slab", new PaintedBlockItem(Blocks.PAINTED_IRON_BLOCK_VERTICAL_SLAB, new FabricItemSettings()));
        PAINTED_IRON_BLOCK_SLAB = MGBlockRegister("painted_iron_block_slab", new PaintedBlockItem(Blocks.PAINTED_IRON_BLOCK_SLAB, new FabricItemSettings()));
        PAINTED_IRON_BLOCK_QUARTER = MGBlockRegister("painted_iron_block_quarter", new PaintedBlockItem(Blocks.PAINTED_IRON_BLOCK_QUARTER, new FabricItemSettings()));
        PAINTED_IRON_BLOCK_EIGHTH = MGBlockRegister("painted_iron_block_eighth", new PaintedBlockItem(Blocks.PAINTED_IRON_BLOCK_EIGHTH, new FabricItemSettings()));



        PAINTED_IRON_BARS = MGBlockRegister("painted_iron_bars", new PaintedBlockItem(Blocks.PAINTED_IRON_BARS, new FabricItemSettings()));
        PAINTED_IRON_WALL = MGBlockRegister("painted_iron_wall", new PaintedBlockItem(Blocks.PAINTED_IRON_WALL, new FabricItemSettings()));
        PAINTED_IRON_FENCE = MGBlockRegister("painted_iron_fence", new PaintedBlockItem(Blocks.PAINTED_IRON_FENCE, new FabricItemSettings()));
        PAINTED_IRON_FENCE_GATE = MGBlockRegister("painted_iron_fence_gate", new PaintedBlockItem(Blocks.PAINTED_IRON_FENCE_GATE, new FabricItemSettings()));
        PAINTED_IRON_DOOR = MGBlockRegister("painted_iron_door", new PaintedBlockItem(Blocks.PAINTED_IRON_DOOR, new FabricItemSettings()));
        PAINTED_IRON_TRAPDOOR = MGBlockRegister("painted_iron_trapdoor", new PaintedBlockItem(Blocks.PAINTED_IRON_TRAPDOOR, new FabricItemSettings()));
        PAINTED_IRON_PRESSURE_PLATE = MGBlockRegister("painted_iron_pressure_plate", new PaintedBlockItem(Blocks.PAINTED_IRON_PRESSURE_PLATE, new FabricItemSettings()));
        PAINTED_IRON_BUTTON = MGBlockRegister("painted_iron_button", new PaintedBlockItem(Blocks.PAINTED_IRON_BUTTON, new FabricItemSettings()));

        PAINTED_OAK_PLANKS = MGBlockRegister("painted_oak_planks", new PaintedBlockItem(Blocks.PAINTED_OAK_PLANKS, new FabricItemSettings()));
        PAINTED_OAK_VERTICAL_STAIRS = MGBlockRegister("painted_oak_vertical_stairs", new PaintedBlockItem(Blocks.PAINTED_OAK_VERTICAL_STAIRS, new FabricItemSettings()));
        PAINTED_OAK_STAIRS = MGBlockRegister("painted_oak_stairs", new PaintedBlockItem(Blocks.PAINTED_OAK_STAIRS, new FabricItemSettings()));
        PAINTED_OAK_VERTICAL_SLAB = MGBlockRegister("painted_oak_vertical_slab", new PaintedBlockItem(Blocks.PAINTED_OAK_VERTICAL_SLAB, new FabricItemSettings()));
        PAINTED_OAK_SLAB = MGBlockRegister("painted_oak_slab", new PaintedBlockItem(Blocks.PAINTED_OAK_SLAB, new FabricItemSettings()));
        PAINTED_OAK_QUARTER = MGBlockRegister("painted_oak_quarter", new PaintedBlockItem(Blocks.PAINTED_OAK_QUARTER, new FabricItemSettings()));
        PAINTED_OAK_EIGHTH = MGBlockRegister("painted_oak_eighth", new PaintedBlockItem(Blocks.PAINTED_OAK_EIGHTH, new FabricItemSettings()));

        PAINTED_OAK_LANTERN = MGBlockRegister("painted_oak_lantern", new PaintedBlockItem(Blocks.PAINTED_OAK_LANTERN, new FabricItemSettings()));
        PAINTED_OAK_LANTERN_ROD = MGBlockRegister("painted_oak_lantern_rod", new PaintedBlockItem(Blocks.PAINTED_OAK_LANTERN_ROD, new FabricItemSettings()));

        PAINTED_WOOL = MGBlockRegister("painted_wool", new PaintedBlockItem(Blocks.PAINTED_WOOL, new FabricItemSettings()));
        PAINTED_CARPET = MGBlockRegister("painted_carpet", new PaintedBlockItem(Blocks.PAINTED_CARPET, new FabricItemSettings()));
        PAINTED_TERRACOTTA = MGBlockRegister("painted_terracotta", new PaintedBlockItem(Blocks.PAINTED_TERRACOTTA, new FabricItemSettings()));
        PAINTED_CONCRETE = MGBlockRegister("painted_concrete", new PaintedBlockItem(Blocks.PAINTED_CONCRETE, new FabricItemSettings()));
        PAINTED_CONCRETE_POWDER = MGBlockRegister("painted_concrete_powder", new PaintedBlockItem(Blocks.PAINTED_CONCRETE_POWDER, new FabricItemSettings()));
        PAINTED_STAINED_GLASS = MGBlockRegister("painted_stained_glass", new PaintedBlockItem(Blocks.PAINTED_STAINED_GLASS, new FabricItemSettings()));
        PAINTED_STAINED_GLASS_PANE = MGBlockRegister("painted_stained_glass_pane", new PaintedBlockItem(Blocks.PAINTED_STAINED_GLASS_PANE, new FabricItemSettings()));
        //PAINTED_SHULKER_BOX = MGBlockRegister("painted_shulker_box", new PaintedBlockItem(Blocks.PAINTED_SHULKER_BOX, new FabricItemSettings()));
        //PAINTED_BED = MGBlockRegister("painted_bed", new PaintedBlockItem(Blocks.PAINTED_BED, new FabricItemSettings()));
        PAINTED_CANDLE = MGBlockRegister("painted_candle", new PaintedBlockItem(Blocks.PAINTED_CANDLE, new FabricItemSettings()));
        //PAINTED_BANNER = MGBlockRegister("painted_banner", new PaintedBlockItem(Blocks.PAINTED_BANNER, new FabricItemSettings()));
    }

    static void PaintedItems()
    {
        CHRISTMAS_HAT = MGItemRegister("christmas_hat", new HatItem(new FabricItemSettings().maxCount(1)));
        CLASSY_HAT = MGItemRegister("classy_hat", new HatItem(new FabricItemSettings().maxCount(1)));
    }
}
