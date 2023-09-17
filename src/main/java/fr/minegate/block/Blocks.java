package fr.minegate.block;

import fr.minegate.MineGate;
import fr.minegate.block.painted.PaintedCandleBlock;
import fr.minegate.block.painted.PaintedPaneBlock;
import fr.minegate.block.painted.PaintedSandBlock;
import fr.minegate.block.painted.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class Blocks
{
    /**
     * List of MineGate building blocks.
     */

    public static Block OAK_VERTICAL_STAIRS;
    public static Block OAK_VERTICAL_SLAB;
    public static Block OAK_QUARTER;
    public static Block OAK_EIGHTH;

    public static Block STONE_VERTICAL_STAIRS;
    public static Block STONE_VERTICAL_SLAB;
    public static Block STONE_QUARTER;
    public static Block STONE_EIGHTH;

    public static Block IRON_BLOCK_VERTICAL_STAIRS;
    public static Block IRON_BLOCK_STAIRS;
    public static Block IRON_BLOCK_VERTICAL_SLAB;
    public static Block IRON_BLOCK_SLAB;
    public static Block IRON_BLOCK_QUARTER;
    public static Block IRON_BLOCK_EIGHTH;


    public static Block PAINTED_IRON_BLOCK_VERTICAL_STAIRS;
    public static Block PAINTED_IRON_BLOCK_STAIRS;
    public static Block PAINTED_IRON_BLOCK_VERTICAL_SLAB;
    public static Block PAINTED_IRON_BLOCK_SLAB;
    public static Block PAINTED_IRON_BLOCK_QUARTER;
    public static Block PAINTED_IRON_BLOCK_EIGHTH;


    public static Block PAINTED_OAK_VERTICAL_STAIRS;
    public static Block PAINTED_OAK_STAIRS;
    public static Block PAINTED_OAK_VERTICAL_SLAB;
    public static Block PAINTED_OAK_SLAB;
    public static Block PAINTED_OAK_QUARTER;
    public static Block PAINTED_OAK_EIGHTH;

    /**
     * List of MineGate operator utilities.
     */

    public static Block OAK_CHAIR;

    /**
     * List of MineGate operator utilities.
     */

    public static Block PORTAL;

    /**
     * List of MineGate entity block type.
     */

    public static Block[] PAINTED_BLOCKS = new Block[0];
    public static Block[] SIT_BLOCKS;

    /**
     * List of MineGate painted blocks.
     */

    public static Block PAINTED_IRON_BLOCK;
    public static Block PAINTED_IRON_BARS;
    public static Block PAINTED_IRON_WALL;
    public static Block PAINTED_IRON_FENCE;
    public static Block PAINTED_IRON_FENCE_GATE;
    public static Block PAINTED_IRON_DOOR;
    public static Block PAINTED_IRON_TRAPDOOR;
    public static Block PAINTED_IRON_PRESSURE_PLATE;
    public static Block PAINTED_IRON_BUTTON;


    public static Block PAINTED_OAK_PLANKS;
    public static Block PAINTED_WOOL;
    public static Block PAINTED_CARPET;
    public static Block PAINTED_TERRACOTTA;
    public static Block PAINTED_CONCRETE;
    public static Block PAINTED_CONCRETE_POWDER;
    public static Block PAINTED_STAINED_GLASS;
    public static Block PAINTED_STAINED_GLASS_PANE;
    public static Block PAINTED_SHULKER_BOX;
    public static Block PAINTED_BED;
    public static Block PAINTED_CANDLE;
    public static Block PAINTED_BANNER;

    public static Block PAINTED_OAK_LANTERN;
    public static Block PAINTED_OAK_LANTERN_ROD;


    /**
     * Initialization.
     */

    public static void init()
    {
        MineGate.log("Loading of the different types of blocks.");
        BuildingBlocks();
        PaintedBlocks();
        FunctionalBlocks();
        OperatorUtilities();
        MineGate.log("Loading of the different types of blocks completed.");
    }

    /**
     * Registration of MineGate blocks.
     *
     * @param id    String id of the Block.
     * @param block Block from the list of blocks.
     */

    private static Block MineGateBlockRegister(String id, Block block)
    {
        return Registry.register(Registries.BLOCK, new Identifier(MineGate.name.toLowerCase(), id), block);
    }

    /**
     * Registration of Painted blocks.
     *
     * @param id    String ID of the Block.
     * @param block Block from the list of blocks.
     **/

    private static Block PaintedBlockRegister(String id, Block block)
    {
        addToPaintedBlocks(block);
        return MineGateBlockRegister(id, block);
    }

    private static void addToPaintedBlocks(Block block)
    {
        Block[] newArray = Arrays.copyOf(PAINTED_BLOCKS, PAINTED_BLOCKS.length + 1);
        newArray[PAINTED_BLOCKS.length] = block;
        PAINTED_BLOCKS = newArray;
    }

    /**
     * Registration of Sit blocks.
     *
     * @param id    String ID of the Block.
     * @param block Block from the list of blocks.
     **/

    private static Block SitBlockRegister(String id, Block block)
    {
        SIT_BLOCKS = new Block[]{block};
        return MineGateBlockRegister(id, block);
    }

    static void BuildingBlocks()
    {
        OAK_VERTICAL_STAIRS = MineGateBlockRegister("oak_vertical_stairs", new VerticalStairsBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
        OAK_VERTICAL_SLAB = MineGateBlockRegister("oak_vertical_slab", new VerticalSlabBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
        OAK_QUARTER = MineGateBlockRegister("oak_quarter", new QuarterBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
        OAK_EIGHTH = MineGateBlockRegister("oak_eighth", new EighthBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));

        STONE_VERTICAL_STAIRS = MineGateBlockRegister("stone_vertical_stairs", new VerticalStairsBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.STONE)));
        STONE_VERTICAL_SLAB = MineGateBlockRegister("stone_vertical_slab", new VerticalSlabBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.STONE)));
        STONE_QUARTER = MineGateBlockRegister("stone_quarter", new QuarterBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.STONE)));
        STONE_EIGHTH = MineGateBlockRegister("stone_eighth", new EighthBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.STONE)));

        IRON_BLOCK_VERTICAL_STAIRS = MineGateBlockRegister("iron_block_vertical_stairs", new VerticalStairsBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        IRON_BLOCK_STAIRS = MineGateBlockRegister("iron_block_stairs", new StairsBlock(net.minecraft.block.Blocks.IRON_BLOCK.getDefaultState(), FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        IRON_BLOCK_VERTICAL_SLAB = MineGateBlockRegister("iron_block_vertical_slab", new VerticalSlabBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        IRON_BLOCK_SLAB = MineGateBlockRegister("iron_block_slab", new SlabBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        IRON_BLOCK_QUARTER = MineGateBlockRegister("iron_block_quarter", new QuarterBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        IRON_BLOCK_EIGHTH = MineGateBlockRegister("iron_block_eighth", new EighthBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
    }

    static void FunctionalBlocks()
    {
        //OAK_CHAIR = SitBlockRegister("oak_chair", new ChairBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
    }

    static void OperatorUtilities()
    {
        PORTAL = PaintedBlockRegister("portal", new PortalBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.NETHER_PORTAL)));
    }

    static void PaintedBlocks()
    {
        PAINTED_IRON_BLOCK = PaintedBlockRegister("painted_iron_block", new PaintedBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_BLOCK_VERTICAL_STAIRS = PaintedBlockRegister("painted_iron_block_vertical_stairs", new PaintedVerticalStairsBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_BLOCK_STAIRS = PaintedBlockRegister("painted_iron_block_stairs", new PaintedStairsBlock(net.minecraft.block.Blocks.IRON_BLOCK.getDefaultState(), FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_BLOCK_VERTICAL_SLAB = PaintedBlockRegister("painted_iron_block_vertical_slab", new PaintedVerticalSlabBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_BLOCK_SLAB = PaintedBlockRegister("painted_iron_block_slab", new PaintedSlabBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_BLOCK_QUARTER = PaintedBlockRegister("painted_iron_block_quarter", new PaintedQuarterBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_BLOCK_EIGHTH = PaintedBlockRegister("painted_iron_block_eighth", new PaintedEighthBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));

        PAINTED_IRON_BARS  = PaintedBlockRegister("painted_iron_bars", new PaintedPaneBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_WALL = PaintedBlockRegister("painted_iron_wall", new PaintedWallBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_FENCE = PaintedBlockRegister("painted_iron_fence", new PaintedFenceBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_FENCE_GATE = PaintedBlockRegister("painted_iron_fence_gate", new PaintedIronFenceGateBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK)));
        PAINTED_IRON_DOOR = PaintedBlockRegister("painted_iron_door", new PaintedDoorBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK), BlockSetType.IRON));
        PAINTED_IRON_TRAPDOOR = PaintedBlockRegister("painted_iron_trapdoor", new PaintedTrapdoorBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_TRAPDOOR), BlockSetType.IRON));
        PAINTED_IRON_PRESSURE_PLATE = PaintedBlockRegister("painted_iron_pressure_plate", new PaintedIronPressurePlateBlock(150, FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_TRAPDOOR), BlockSetType.IRON));
        PAINTED_IRON_BUTTON = PaintedBlockRegister("painted_iron_button", new PaintedButtonBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK), BlockSetType.IRON, 20, false));

        PAINTED_OAK_PLANKS = PaintedBlockRegister("painted_oak_planks", new PaintedBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
        PAINTED_OAK_VERTICAL_STAIRS = PaintedBlockRegister("painted_oak_vertical_stairs", new PaintedVerticalStairsBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
        PAINTED_OAK_STAIRS = PaintedBlockRegister("painted_oak_stairs", new PaintedStairsBlock(net.minecraft.block.Blocks.OAK_PLANKS.getDefaultState(), FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
        PAINTED_OAK_VERTICAL_SLAB = PaintedBlockRegister("painted_oak_vertical_slab", new PaintedVerticalSlabBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
        PAINTED_OAK_SLAB = PaintedBlockRegister("painted_oak_slab", new PaintedSlabBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
        PAINTED_OAK_QUARTER = PaintedBlockRegister("painted_oak_quarter", new PaintedQuarterBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));
        PAINTED_OAK_EIGHTH = PaintedBlockRegister("painted_oak_eighth", new PaintedEighthBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_PLANKS)));

        PAINTED_OAK_LANTERN = PaintedBlockRegister("painted_oak_lantern", new PaintedLanternBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_WOOD)));
        PAINTED_OAK_LANTERN_ROD = PaintedBlockRegister("painted_oak_lantern_rod", new PaintedLanternRodBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.OAK_WOOD)));

        PAINTED_WOOL = PaintedBlockRegister("painted_wool", new PaintedBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_WOOL)));
        PAINTED_CARPET = PaintedBlockRegister("painted_carpet", new PaintedCarpetBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_CARPET)));
        PAINTED_TERRACOTTA = PaintedBlockRegister("painted_terracotta", new PaintedBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_TERRACOTTA)));
        PAINTED_CONCRETE = PaintedBlockRegister("painted_concrete", new PaintedBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_CONCRETE)));
        PAINTED_CONCRETE_POWDER = PaintedBlockRegister("painted_concrete_powder", new PaintedSandBlock(14406560, FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_CONCRETE_POWDER)));
        PAINTED_STAINED_GLASS = PaintedBlockRegister("painted_stained_glass", new PaintedStainedGlassBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_STAINED_GLASS)));
        PAINTED_STAINED_GLASS_PANE = PaintedBlockRegister("painted_stained_glass_pane", new PaintedStainedGlassPaneBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_STAINED_GLASS_PANE)));
        //PAINTED_SHULKER_BOX = PaintedBlockRegister("painted_shulker_box", new PaintedShulkerBoxBlock(DyeColor.BLUE, FabricBlockSettings.copy(net.minecraft.block.Blocks.SHULKER_BOX)));
        //PAINTED_BED = PaintedBlockRegister("painted_bed", new PaintedBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_BED)));
        PAINTED_CANDLE = PaintedBlockRegister("painted_candle", new PaintedCandleBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_CANDLE)));
        //PAINTED_BANNER = PaintedBlockRegister("painted_banner", new PaintedBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.WHITE_BANNER)));
    }
}
