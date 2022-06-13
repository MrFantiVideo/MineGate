package net.minegate.fr.moreblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum FlowerType implements StringIdentifiable
{
    EMPTY("empty"),
    OAK_SAPLING("oak_sapling"),
    SPRUCE_SAPLING("spruce_sapling"),
    BIRCH_SAPLING("birch_sapling"),
    JUNGLE_SAPLING("jungle_sapling"),
    ACACIA_SAPLING("acacia_sapling"),
    DARK_OAK_SAPLING("dark_oak_sapling"),
    MANGROVE_PROPAGULE("mangrove_propagule"),
    FERN("fern"),
    DANDELION("dandelion"),
    POPPY("poppy"),
    BLUE_ORCHID("blue_orchid"),
    ALLIUM("allium"),
    AZURE_BLUET("azure_bluet"),
    RED_TULIP("red_tulip"),
    ORANGE_TULIP("orange_tulip"),
    WHITE_TULIP("white_tulip"),
    PINK_TULIP("pink_tulip"),
    OXEYE_DAISY("oxeye_daisy"),
    CORNFLOWER("cornflower"),
    LILY_OF_THE_VALLEY("lily_of_the_valley"),
    WITHER_ROSE("wither_rose"),
    RED_MUSHROOM("red_mushroom"),
    BROWN_MUSHROOM("brown_mushroom"),
    DEAD_BUSH("dead_bush"),
    CACTUS("cactus"),
    BAMBOO("bamboo"),
    CRIMSON_FUNGUS("crimson_fungus"),
    WARPED_FUNGUS("warped_fungus"),
    CRIMSON_ROOTS("crimson_roots"),
    WARPED_ROOTS("warped_roots"),
    AZALEA_BUSH("azalea_bush"),
    FLOWERING_AZALEA_BUSH("flowering_azalea_bush"),
    BLUE_TWEEDIA("blue_tweedia"),
    ROSE("rose");

    private final String name;

    /**
     * Enumerations of the different positions for VaseBlock.
     **/

    FlowerType(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }

    public String asString()
    {
        return this.name;
    }
}