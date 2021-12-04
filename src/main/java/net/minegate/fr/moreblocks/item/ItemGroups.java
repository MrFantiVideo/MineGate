package net.minegate.fr.moreblocks.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minegate.fr.moreblocks.block.Blocks;

import static net.minegate.fr.moreblocks.MoreBlocks.NameClient;

public class ItemGroups
{
    /**
     * Inventory of Building blocks MineGate blocks.
     **/

    public static final ItemGroup GROUP_BUILDINGBLOCKS = FabricItemGroupBuilder.create(new Identifier(NameClient.toLowerCase(), "buildingblocks")).icon(() -> new ItemStack(Blocks.BLACK_TILES)).build();

    /**
     * Inventory of Decorative blocks MineGate blocks.
     **/

    public static final ItemGroup GROUP_DECORATIONS = FabricItemGroupBuilder.create(new Identifier(NameClient.toLowerCase(), "decorations")).icon(() -> new ItemStack(Items.OAK_WOOD_CHAIR)).appendItems(stacks ->
    {
        stacks.add(new ItemStack(Blocks.OAK_WOOD_CHAIR));
        stacks.add(new ItemStack(Blocks.SPRUCE_WOOD_CHAIR));
        stacks.add(new ItemStack(Blocks.BIRCH_WOOD_CHAIR));
        stacks.add(new ItemStack(Blocks.JUNGLE_WOOD_CHAIR));
        stacks.add(new ItemStack(Blocks.ACACIA_WOOD_CHAIR));
        stacks.add(new ItemStack(Blocks.DARK_OAK_WOOD_CHAIR));
        stacks.add(new ItemStack(Blocks.CRIMSON_WOOD_CHAIR));
        stacks.add(new ItemStack(Blocks.WARPED_WOOD_CHAIR));
        stacks.add(new ItemStack(Blocks.OAK_WOOD_BENCH));
        stacks.add(new ItemStack(Blocks.SPRUCE_WOOD_BENCH));
        stacks.add(new ItemStack(Blocks.BIRCH_WOOD_BENCH));
        stacks.add(new ItemStack(Blocks.JUNGLE_WOOD_BENCH));
        stacks.add(new ItemStack(Blocks.ACACIA_WOOD_BENCH));
        stacks.add(new ItemStack(Blocks.DARK_OAK_WOOD_BENCH));
        stacks.add(new ItemStack(Blocks.CRIMSON_WOOD_BENCH));
        stacks.add(new ItemStack(Blocks.WARPED_WOOD_BENCH));
        stacks.add(new ItemStack(Blocks.OAK_WOOD_STOOL));
        stacks.add(new ItemStack(Blocks.SPRUCE_WOOD_STOOL));
        stacks.add(new ItemStack(Blocks.BIRCH_WOOD_STOOL));
        stacks.add(new ItemStack(Blocks.JUNGLE_WOOD_STOOL));
        stacks.add(new ItemStack(Blocks.ACACIA_WOOD_STOOL));
        stacks.add(new ItemStack(Blocks.DARK_OAK_WOOD_STOOL));
        stacks.add(new ItemStack(Blocks.CRIMSON_WOOD_STOOL));
        stacks.add(new ItemStack(Blocks.WARPED_WOOD_STOOL));
        stacks.add(new ItemStack(Blocks.OAK_WOOD_STOOL_HIGH));
        stacks.add(new ItemStack(Blocks.SPRUCE_WOOD_STOOL_HIGH));
        stacks.add(new ItemStack(Blocks.BIRCH_WOOD_STOOL_HIGH));
        stacks.add(new ItemStack(Blocks.JUNGLE_WOOD_STOOL_HIGH));
        stacks.add(new ItemStack(Blocks.ACACIA_WOOD_STOOL_HIGH));
        stacks.add(new ItemStack(Blocks.DARK_OAK_WOOD_STOOL_HIGH));
        stacks.add(new ItemStack(Blocks.CRIMSON_WOOD_STOOL_HIGH));
        stacks.add(new ItemStack(Blocks.WARPED_WOOD_STOOL_HIGH));
        stacks.add(new ItemStack(Blocks.OAK_WOOD_TABLE));
        stacks.add(new ItemStack(Blocks.SPRUCE_WOOD_TABLE));
        stacks.add(new ItemStack(Blocks.BIRCH_WOOD_TABLE));
        stacks.add(new ItemStack(Blocks.JUNGLE_WOOD_TABLE));
        stacks.add(new ItemStack(Blocks.ACACIA_WOOD_TABLE));
        stacks.add(new ItemStack(Blocks.DARK_OAK_WOOD_TABLE));
        stacks.add(new ItemStack(Blocks.CRIMSON_WOOD_TABLE));
        stacks.add(new ItemStack(Blocks.WARPED_WOOD_TABLE));
        stacks.add(new ItemStack(Blocks.OAK_WOOD_TABLE_HIGH));
        stacks.add(new ItemStack(Blocks.SPRUCE_WOOD_TABLE_HIGH));
        stacks.add(new ItemStack(Blocks.BIRCH_WOOD_TABLE_HIGH));
        stacks.add(new ItemStack(Blocks.JUNGLE_WOOD_TABLE_HIGH));
        stacks.add(new ItemStack(Blocks.ACACIA_WOOD_TABLE_HIGH));
        stacks.add(new ItemStack(Blocks.DARK_OAK_WOOD_TABLE_HIGH));
        stacks.add(new ItemStack(Blocks.CRIMSON_WOOD_TABLE_HIGH));
        stacks.add(new ItemStack(Blocks.WARPED_WOOD_TABLE_HIGH));
        stacks.add(new ItemStack(Blocks.OAK_WOOD_TABLE_SMALL));
        stacks.add(new ItemStack(Blocks.SPRUCE_WOOD_TABLE_SMALL));
        stacks.add(new ItemStack(Blocks.BIRCH_WOOD_TABLE_SMALL));
        stacks.add(new ItemStack(Blocks.JUNGLE_WOOD_TABLE_SMALL));
        stacks.add(new ItemStack(Blocks.ACACIA_WOOD_TABLE_SMALL));
        stacks.add(new ItemStack(Blocks.DARK_OAK_WOOD_TABLE_SMALL));
        stacks.add(new ItemStack(Blocks.CRIMSON_WOOD_TABLE_SMALL));
        stacks.add(new ItemStack(Blocks.WARPED_WOOD_TABLE_SMALL));
        stacks.add(new ItemStack(Blocks.OAK_BOOKSHELF));
        stacks.add(new ItemStack(Blocks.SPRUCE_BOOKSHELF));
        stacks.add(new ItemStack(Blocks.BIRCH_BOOKSHELF));
        stacks.add(new ItemStack(Blocks.JUNGLE_BOOKSHELF));
        stacks.add(new ItemStack(Blocks.ACACIA_BOOKSHELF));
        stacks.add(new ItemStack(Blocks.DARK_OAK_BOOKSHELF));
        stacks.add(new ItemStack(Blocks.CRIMSON_BOOKSHELF));
        stacks.add(new ItemStack(Blocks.WARPED_BOOKSHELF));
        stacks.add(new ItemStack(net.minecraft.block.Blocks.LADDER));
        stacks.add(new ItemStack(Blocks.SPRUCE_LADDER));
        stacks.add(new ItemStack(Blocks.BIRCH_LADDER));
        stacks.add(new ItemStack(Blocks.JUNGLE_LADDER));
        stacks.add(new ItemStack(Blocks.ACACIA_LADDER));
        stacks.add(new ItemStack(Blocks.DARK_OAK_LADDER));
        stacks.add(new ItemStack(Blocks.CRIMSON_LADDER));
        stacks.add(new ItemStack(Blocks.WARPED_LADDER));
    }).build();

    /**
     * Inventory of Various MineGate blocks and items.
     **/

    public static final ItemGroup GROUP_VARIOUS = FabricItemGroupBuilder.create(new Identifier(NameClient.toLowerCase(), "various")).icon(() -> new ItemStack(Items.BLACK_BOOK)).appendItems(stacks ->
    {
        stacks.add(new ItemStack(net.minecraft.item.Items.STICK));
        stacks.add(new ItemStack(Items.SPRUCE_STICK));
        stacks.add(new ItemStack(Items.BIRCH_STICK));
        stacks.add(new ItemStack(Items.JUNGLE_STICK));
        stacks.add(new ItemStack(Items.ACACIA_STICK));
        stacks.add(new ItemStack(Items.DARK_OAK_STICK));
        stacks.add(new ItemStack(Items.CRIMSON_STICK));
        stacks.add(new ItemStack(Items.WARPED_STICK));
        stacks.add(new ItemStack(Items.TILE));
        stacks.add(new ItemStack(Items.OBSIDIAN_INGOT));
        stacks.add(new ItemStack(Items.WHITE_BOOK));
        stacks.add(new ItemStack(Items.ORANGE_BOOK));
        stacks.add(new ItemStack(Items.MAGENTA_BOOK));
        stacks.add(new ItemStack(Items.LIGHT_BLUE_BOOK));
        stacks.add(new ItemStack(Items.YELLOW_BOOK));
        stacks.add(new ItemStack(Items.LIME_BOOK));
        stacks.add(new ItemStack(Items.PINK_BOOK));
        stacks.add(new ItemStack(Items.LIGHT_GRAY_BOOK));
        stacks.add(new ItemStack(Items.GRAY_BOOK));
        stacks.add(new ItemStack(Items.CYAN_BOOK));
        stacks.add(new ItemStack(Items.PURPLE_BOOK));
        stacks.add(new ItemStack(Items.BLUE_BOOK));
        stacks.add(new ItemStack(Items.BROWN_BOOK));
        stacks.add(new ItemStack(Items.GREEN_BOOK));
        stacks.add(new ItemStack(Items.RED_BOOK));
        stacks.add(new ItemStack(Items.BLACK_BOOK));
        stacks.add(new ItemStack(Items.STRAW_HAT));
        stacks.add(new ItemStack(Items.CHRISTMAS_HAT));
    }).build();
}
