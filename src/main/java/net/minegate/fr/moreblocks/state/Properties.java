package net.minegate.fr.moreblocks.state;

import net.minecraft.state.property.EnumProperty;
import net.minegate.fr.moreblocks.block.enums.*;

public class Properties
{
    public static final EnumProperty<BookshelfType>       BOOKSHELF_TYPE;
    public static final EnumProperty<EighthType>          EIGHTH_TYPE;
    public static final EnumProperty<FernType>            FERN_TYPE;
    public static final EnumProperty<QuarterVerticalType> QUARTER_VERTICAL_TYPE;
    public static final EnumProperty<SlabVerticalType>    SLAB_VERTICAL_TYPE;

    static
    {
        BOOKSHELF_TYPE = EnumProperty.of("type", BookshelfType.class);
        EIGHTH_TYPE = EnumProperty.of("type", EighthType.class);
        FERN_TYPE = EnumProperty.of("type", FernType.class);
        QUARTER_VERTICAL_TYPE = EnumProperty.of("type", QuarterVerticalType.class);
        SLAB_VERTICAL_TYPE = EnumProperty.of("type", SlabVerticalType.class);
    }
}
