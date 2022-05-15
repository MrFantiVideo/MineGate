package net.minegate.fr.moreblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum SlabVerticalType implements StringIdentifiable
{
    SINGLE("single"),
    DOUBLE("double");

    private final String name;

    /**
     * Enumerations of the different positions for SlabVerticalBlock.
     **/

    SlabVerticalType(String name)
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