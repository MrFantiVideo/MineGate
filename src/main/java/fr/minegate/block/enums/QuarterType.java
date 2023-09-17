package fr.minegate.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum QuarterType implements StringIdentifiable
{
    DEFAULT("default"),
    MIDDLE("middle");

    private final String name;

    QuarterType(String name)
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
