package fr.minegate.block.enums;


import net.minecraft.util.StringIdentifiable;

public enum VerticalSlabType implements StringIdentifiable
{
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west"),
    DOUBLE("double");

    private final String name;

    /**
     * Enumerations of the different positions for VerticalSlabBlock.
     **/

    VerticalSlabType(String name)
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