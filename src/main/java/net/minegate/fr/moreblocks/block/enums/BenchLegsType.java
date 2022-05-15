package net.minegate.fr.moreblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum BenchLegsType implements StringIdentifiable
{
    EMPTY("empty"),
    X_LEGS("x_legs"),
    Z_LEGS("z_legs"),
    NORTH_LEGS("north_legs"),
    EAST_LEGS("east_legs"),
    SOUTH_LEGS("south_legs"),
    WEST_LEGS("west_legs");

    private final String name;

    /**
     * Enumerations of the different positions for BenchBlock legs.
     **/

    BenchLegsType(String name)
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