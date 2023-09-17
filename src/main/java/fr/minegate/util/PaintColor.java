package fr.minegate.util;

public enum PaintColor
{
    WHITE(0, "white", 16383998, true),
    LIGHT_BLUISH_GRAY(1, "light_bluish_gray", 11515335, false),
    LIGHT_GRAY(2, "light_gray", 10329495, true),
    GRAY(3, "gray", 4673362, true),
    BLACK(4, "black", 1908001, true),
    DARK_BROWN(5, "dark_brown", 2627861, false),
    LIGHT_BROWN(6, "light_brown", 5257007, false),
    LIGHT_GRAYISH_BROWN(7, "light_grayish_brown", 7035482, false),
    BROWN(8, "brown", 8606770, true),
    DARK_RED(9, "dark_red", 6950421, false),
    RED(10, "red", 11546150, true),
    LIGHT_RED(11, "light_red", 16744818, false),
    LIGHT_NOUGAT(12, "light_nougat", 16698544, false),
    DARK_ORANGE(13, "dark_orange", 11683863, false),
    ORANGE(14, "orange", 16351261, true),
    DARK_YELLOW(15, "dark_yellow", 14522414, false),
    YELLOW(16, "yellow", 16701501, true),
    LIGHT_YELLOW(17, "light_yellow", 16768164, false),
    LIGHT_OLIVER(18, "light_olive", 14414677, false),
    OLIVE(19, "olive", 11250003, false),
    DARK_OLIVE(20, "dark_olive", 7763263, false),
    GREEN(21, "green", 6192150, true),
    LIME(22, "lime", 8439583, true),
    LIGHT_LIME(23, "light_lime", 9559948, false),
    LIGHT_GREEN(24, "light_green", 1100593, false),
    DARK_GREEN(25, "dark_green", 37437, false),
    DARK_CYAN(26, "dark_cyan", 3036483, false),
    CYAN(27, "cyan", 1481884, true),
    LIGHT_BLUE(28, "light_blue", 3847130, true),
    AZURE(29, "azure", 12379612, false),
    LIGHT_MARINE(30, "light_marine", 8564184, false),
    MARINE(31, "marine", 2373463, false),
    OCEAN(32, "ocean", 22438, false),
    BLUE(33, "blue", 3949738, true),
    LILAC(34, "lilac", 7889614, false),
    DARK_PURPLE(35, "dark_purple", 6235779, false),
    PURPLE(36, "purple", 8991416, true),
    MAGENTA(37, "magenta", 13061821, true),
    LIGHT_PURPLE(38, "light_purple", 11481493, false),
    DARK_MAGENTA(39, "dark_magenta", 12001910, false),
    DARK_PINK(40, "dark_pink", 15686579, false),
    PINK(41, "pink", 15961002, true),
    LIGHT_PINK(42, "light_pink", 16235738, false);

    private final int     id;
    private final String  name;
    private final int     color;
    private final boolean vanilla;

    private PaintColor(int id, String name, int color, boolean vanilla)
    {
        this.id = id;
        this.name = name;
        this.color = color;
        this.vanilla = vanilla;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public int getColor()
    {
        return this.color;
    }

    public boolean isVanilla()
    {
        return this.vanilla;
    }

    public static float[] decimalToRGBA(int colorDecimal)
    {
        if (colorDecimal >= 0 && colorDecimal <= 0xFFFFFF)
        {
            float[] colorComponents = new float[4];
            colorComponents[0] = ((colorDecimal >> 16) & 0xFF) / 255.0f; // Red
            colorComponents[1] = ((colorDecimal >> 8) & 0xFF) / 255.0f;  // Green
            colorComponents[2] = (colorDecimal & 0xFF) / 255.0f;         // Blue
            colorComponents[3] = 1.0f; // Alpha value

            return colorComponents;
        }
        else
        {
            return null; // Invalid decimal value
        }
    }
}
