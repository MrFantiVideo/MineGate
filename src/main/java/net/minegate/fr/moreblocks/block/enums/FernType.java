package net.minegate.fr.moreblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum FernType implements StringIdentifiable
{
    DEFAULT("default"),
    PLANT("plant");

    private final String name;

    private FernType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
