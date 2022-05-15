package net.minegate.fr.moreblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum QuarterVerticalType implements StringIdentifiable
{
    CENTER_X("x"),
    CENTER_Z("z"),
    BOTTOM("bottom");

    private final String name;

    /**
     * Enumerations of the different positions for QuarterVerticalBlock.
     **/

    QuarterVerticalType(String name)
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