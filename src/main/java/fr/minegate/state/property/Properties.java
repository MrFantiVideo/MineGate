package fr.minegate.state.property;

import fr.minegate.block.enums.QuarterType;
import fr.minegate.block.enums.VerticalSlabType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public class Properties
{
    public static final BooleanProperty                TOP_NORTH    = BooleanProperty.of("top_north");
    public static final BooleanProperty                TOP_EAST     = BooleanProperty.of("top_east");
    public static final BooleanProperty                TOP_SOUTH    = BooleanProperty.of("top_south");
    public static final BooleanProperty                TOP_WEST     = BooleanProperty.of("top_west");
    public static final BooleanProperty                BOTTOM_NORTH = BooleanProperty.of("bottom_north");
    public static final BooleanProperty                BOTTOM_EAST  = BooleanProperty.of("bottom_east");
    public static final BooleanProperty                BOTTOM_SOUTH = BooleanProperty.of("bottom_south");
    public static final BooleanProperty                BOTTOM_WEST  = BooleanProperty.of("bottom_west");
    public static final BooleanProperty                CANDLE  = BooleanProperty.of("candle");
    public static final EnumProperty<VerticalSlabType> VERTICAL_SLAB_TYPE;
    public static final EnumProperty<QuarterType>      QUARTER_TYPE;

    static
    {
        VERTICAL_SLAB_TYPE = EnumProperty.of("type", VerticalSlabType.class);
        QUARTER_TYPE = EnumProperty.of("type", QuarterType.class);
    }
}
