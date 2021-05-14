package net.minegate.fr.moreblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum TableConnection implements StringIdentifiable
{
    SIDE("side"),
    NONE("none");

    private final String name;

    private TableConnection(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return this.asString();
    }

    public String asString()
    {
        return this.name;
    }

    public boolean isConnected()
    {
        return this != NONE;
    }
}
