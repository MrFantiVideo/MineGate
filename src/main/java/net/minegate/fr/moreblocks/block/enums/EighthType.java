package net.minegate.fr.moreblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum EighthType implements StringIdentifiable
{
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom"),
    TOP_CENTER("top_center"),
    MIDDLE_CENTER_TOP("middle_center_top"),
    MIDDLE_CENTER("middle_center"),
    MIDDLE_CENTER_BOTTOM("middle_center_bottom"),
    BOTTOM_CENTER("bottom_center");

    private final String name;

    /**
     * Enumerations of the different positions for EighthBlock.
     **/

    EighthType(String name)
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