package net.minegate.fr.moreblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum ColorsType implements StringIdentifiable
{
    EMPTY("empty"),
    DEFAULT("default"),
    BLACK("black"),
    BLUE("blue"),
    BROWN("brown"),
    CYAN("cyan"),
    GRAY("gray"),
    GREEN("green"),
    LIGHT_BLUE("light_blue"),
    LIGHT_GRAY("light_gray"),
    LIME("lime"),
    MAGENTA("magenta"),
    ORANGE("orange"),
    PINK("pink"),
    PURPLE("purple"),
    RED("red"),
    WHITE("white"),
    YELLOW("yellow");

    private final String name;

    /**
     * Enumerations of the different positions for BookshelfBlock & LanternBlock.
     **/

    ColorsType(String name)
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