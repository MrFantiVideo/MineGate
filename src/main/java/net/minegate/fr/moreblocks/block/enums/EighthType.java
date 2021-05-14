package net.minegate.fr.moreblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum EighthType implements StringIdentifiable
{
    TOP("top"),
    BOTTOM("bottom"),
    CENTER_X("x"),
    CENTER_Y("y"),
    CENTER_Z("z");

    private final String name;

    private EighthType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
