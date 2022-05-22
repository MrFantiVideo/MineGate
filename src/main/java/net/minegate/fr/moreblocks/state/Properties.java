package net.minegate.fr.moreblocks.state;

import net.minecraft.state.property.EnumProperty;
import net.minegate.fr.moreblocks.block.enums.*;

public class Properties
{
    public static final EnumProperty<BenchLegsType>       BENCHLEGS_TYPE;
    public static final EnumProperty<ColorsType>          COLORS_TYPE;
    public static final EnumProperty<EighthType>          EIGHTH_TYPE;
    public static final EnumProperty<FlowerType>          FLOWER_TYPE;
    public static final EnumProperty<QuarterVerticalType> QUARTER_VERTICAL_TYPE;
    public static final EnumProperty<SlabVerticalType>    SLAB_VERTICAL_TYPE;

    static
    {
        BENCHLEGS_TYPE = EnumProperty.of("type", BenchLegsType.class);
        COLORS_TYPE = EnumProperty.of("type", ColorsType.class);
        EIGHTH_TYPE = EnumProperty.of("type", EighthType.class);
        FLOWER_TYPE = EnumProperty.of("type", FlowerType.class);
        QUARTER_VERTICAL_TYPE = EnumProperty.of("type", QuarterVerticalType.class);
        SLAB_VERTICAL_TYPE = EnumProperty.of("type", SlabVerticalType.class);
    }
}