package fr.minegate.block.entity;

import fr.minegate.client.color.block.BlockColors;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PaintedBlockEntity extends BlockEntity
{
    /**
     * The default color value used when no specific color is assigned.
     * This value is represented in hexadecimal format and corresponds to the missing texture with pink color.
     */

    private final static int DEFAULT_COLOR = 0xF300F3;

    /**
     * The NBT key for storing color data within a compound nbt.
     * This constant labels the string key used to identify the NBT list containing colors.
     */

    private final static String NBT_COLORS = "Colors";

    /**
     * Array storing all colors in decimal with a specific index.
     */

    private final int[] colors;

    /**
     * Creation of a painted block entity with a single / multiple color.
     *
     * @param pos   The position of the block on which the entity will be.
     * @param state Block properties.
     */

    public PaintedBlockEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntityType.PAINTED, pos, state);
        int tintIndex = BlockColors.getColorsSizeForBlock(state.getBlock());
        this.colors = new int[tintIndex];
    }

    /**
     * Allows you to save a color on a specific layer in the entity block.
     *
     * @param tintIndex Number of the texture layer that will take the color.
     * @param color     Color number in decimal.
     */

    private void setColor(int tintIndex, int color)
    {
        if (isValidTintIndex(tintIndex) && isValidColor(color))
        {
            this.colors[tintIndex] = color;
            markDirty();
        }
    }

    /**
     * Checks if the tint index number is valid, meaning it corresponds to an existing texture layer.
     *
     * @param tintIndex Number of the texture layer that will take the color.
     *
     * @return true if the tint index is valid, otherwise false.
     */

    private boolean isValidTintIndex(int tintIndex)
    {
        return tintIndex >= 0 && tintIndex < colors.length;
    }

    /**
     * Checks if the color value is valid, ensuring it falls within the valid range for colors.
     *
     * @param color Color number in decimal.
     *
     * @return true if the color is valid, otherwise false.
     */

    private boolean isValidColor(int color)
    {
        return color >= 0x000000 && color <= 0xFFFFFF;
    }

    /**
     * Get the requested color from the color index.
     *
     * @param tintIndex Number of the texture layer that will take the color.
     *
     * @return The decimal color number stored in the index.
     */

    public int getColor(int tintIndex)
    {
        return isValidTintIndex(tintIndex) ? this.colors[tintIndex] : DEFAULT_COLOR;
    }

    /**
     * Retrieves the number of texture layers that store colors in this block entity.
     *
     * @return The count of texture layers with stored colors.
     */

    private int getColors()
    {
        return colors.length;
    }

    /**
     * Retrieves color components as floating-point values from the specified texture layer.
     *
     * @param color Color number in decimal.
     *
     * @return An array of floating-point values representing the red, green, and blue color components.
     *
     * @see fr.minegate.mixin.block.entity.BeaconBlockEntityMixin
     */

    public static float[] getColorsComponents(int color)
    {
        float red = (float) ((color >> 16) & 0xFF) / 255.0f;
        float green = (float) ((color >> 8) & 0xFF) / 255.0f;
        float blue = (float) (color & 0xFF) / 255.0f;
        return new float[]{red, green, blue};
    }

    /**
     * Reads color data from the NBT tag to set the colors of the block.
     *
     * @param nbt The NBT tag containing color information.
     */

    @Override
    public void readNbt(NbtCompound nbt)
    {
        super.readNbt(nbt);
        NbtList colorsNbt = nbt.getList(NBT_COLORS, NbtElement.COMPOUND_TYPE);
        if (!colorsNbt.isEmpty())
        {
            for (int tintIndex = 0; tintIndex < colors.length; tintIndex++)
            {
                NbtCompound colorNbt = colorsNbt.getCompound(tintIndex);
                int color = colorNbt.getInt(String.valueOf(tintIndex));
                this.colors[tintIndex] = color;
                markDirty();
            }
        }
    }

    /**
     * Writes color data to the NBT tag to save the colors of the block.
     *
     * @param nbt The NBT tag to which color information is being written.
     */

    @Override
    protected void writeNbt(NbtCompound nbt)
    {
        super.writeNbt(nbt);
        NbtList colorsNbt = nbt.getList(NBT_COLORS, NbtElement.COMPOUND_TYPE);
        for (int tintIndex = 0; tintIndex < colors.length; tintIndex++)
        {
            NbtCompound colorNbt = new NbtCompound();
            int color = this.colors[tintIndex];
            colorNbt.putInt(String.valueOf(tintIndex), color);
            colorsNbt.add(colorNbt);
        }
        nbt.put(NBT_COLORS, colorsNbt);
    }

    /**
     * Creates a network update packet to synchronize the block entity's data between the server and clients.
     *
     * @return The network update packet for this block entity.
     */

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket()
    {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    /**
     * Initializes the NBT data with color when the chunk is loaded.
     *
     * @return The NBT data containing initial color information.
     */

    @Override
    public NbtCompound toInitialChunkDataNbt()
    {
        return createNbt();
    }

    public static int getColorLayersCountAt(BlockRenderView world, BlockPos pos)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null)
        {
            if (blockEntity instanceof PaintedBlockEntity)
            {
                return ((PaintedBlockEntity) blockEntity).getColors();
            }
        }
        return 0;
    }

    public static void setColorBlockAt(World world, BlockPos pos, ItemStack stack)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null)
        {
            if (blockEntity instanceof PaintedBlockEntity)
            {
                NbtCompound nbt = stack.getNbt();
                if (nbt != null)
                {
                    NbtList colorsNbt = nbt.getList(NBT_COLORS, NbtElement.COMPOUND_TYPE);
                    for (int tintIndex = 0; tintIndex < colorsNbt.size(); tintIndex++)
                    {
                        NbtCompound colorNbt = colorsNbt.getCompound(tintIndex);
                        int color = colorNbt.getInt(String.valueOf(tintIndex));
                        ((PaintedBlockEntity) blockEntity).setColor(tintIndex, color);
                    }
                }
            }
        }
    }

    /**
     * Retrieves the color information for rendering a block at the specified position in the world.
     * This method considers block entities and their colors to determine the final color.
     *
     * @param world     The BlockRenderView representing the world in which the block is located.
     * @param pos       The BlockPos indicating the position of the block being rendered.
     * @param tintIndex Number of the texture layer that will take the color.
     *
     * @return The color value to be used for rendering the block.
     */

    public static int getColorBlockAt(BlockRenderView world, BlockPos pos, int tintIndex)
    {
        if (world != null && pos != null)
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null)
            {
                if (blockEntity instanceof PaintedBlockEntity)
                {
                    return ((PaintedBlockEntity) blockEntity).getColor(tintIndex);
                }
            }
            return getColorFromNearbyAndSecondaryBlocks(world, pos);
        }
        return DEFAULT_COLOR;
    }

    /**
     * Permet de récupérer une couleur d'un paintedblockentity à proximité (voisin) quand elle est null à un endroit.
     */

    public static int getColorFromNearbyAndSecondaryBlocks(BlockRenderView world, BlockPos pos)
    {
        for (Direction direction : Direction.values())
        {
            BlockPos offsetPos = pos.offset(direction);
            BlockEntity nearbyBlockEntity = world.getBlockEntity(offsetPos);
            if (nearbyBlockEntity instanceof PaintedBlockEntity)
            {
                return ((PaintedBlockEntity) nearbyBlockEntity).getColor(0);
            }
            else
            {
                for (Direction direction2 : Direction.values())
                {
                    BlockPos offsetPos2 = offsetPos.offset(direction2);
                    BlockEntity nearbyBlockEntity2 = world.getBlockEntity(offsetPos2);
                    if (nearbyBlockEntity2 instanceof PaintedBlockEntity)
                    {
                        return ((PaintedBlockEntity) nearbyBlockEntity2).getColor(0);
                    }
                }
            }
        }
        return DEFAULT_COLOR;
    }

    public static void setColorItem(ItemStack stack, int tintIndex, int color)
    {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt != null)
        {
            NbtList colorsNbt = nbt.getList(NBT_COLORS, NbtElement.COMPOUND_TYPE);
            if (!colorsNbt.isEmpty())
            {
                NbtCompound colorNbt = colorsNbt.getCompound(tintIndex);
                colorNbt.putInt(String.valueOf(tintIndex), color);
                colorsNbt.add(colorNbt);
                nbt.put(NBT_COLORS, colorsNbt);
            }
        }
    }

    public static int getColorItem(ItemStack stack, int tintIndex)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null)
        {
            NbtList colorsNbt = nbt.getList(NBT_COLORS, NbtElement.COMPOUND_TYPE);
            if (!colorsNbt.isEmpty())
            {
                NbtCompound colorNbt = colorsNbt.getCompound(tintIndex);
                return colorNbt.getInt(String.valueOf(tintIndex));
            }
        }
        return DEFAULT_COLOR;
    }

    /**
     * Update the color rendering on the block.
     */

    @Override
    public void markDirty()
    {
        if (world != null)
        {
            if (world.isClient())
            {
                MinecraftClient.getInstance().worldRenderer.scheduleBlockRenders(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
            }
            else if (world instanceof ServerWorld)
            {
                ((ServerWorld) world).getChunkManager().markForUpdate(pos);
            }
            super.markDirty();
        }
    }

    public static void addColorTooltip(ItemStack stack, List<Text> tooltip)
    {
        List<Integer> colors = getColors(stack);
        for (int color : colors)
        {
            String colorHex = String.format("%06X", (0xFFFFFF & color));
            MutableText colorTooltip = Text.translatable("#" + colorHex).styled(style -> style.withColor(TextColor.fromRgb(color)));
            tooltip.add(colorTooltip);
        }
    }

    public static List<Integer> getColors(ItemStack stack)
    {
        List<Integer> colors = new ArrayList<>();
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        NbtList colorsTag = nbtCompound.getList("Colors", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < colorsTag.size(); i++)
        {
            NbtCompound colorTag = colorsTag.getCompound(i);
            int color = colorTag.getInt(String.valueOf(i));
            colors.add(color);
        }
        return colors;
    }

    public static void addColorToNBT(ItemStack stack, List<Integer> colors)
    {
        NbtCompound tag = stack.getNbt();
        NbtList colorsTag = new NbtList();
        for (int i = 0; i < colors.size(); i++)
        {
            NbtCompound colorCompound = new NbtCompound();
            colorCompound.putInt(String.valueOf(i), colors.get(i));
            colorsTag.add(colorCompound);
        }
        tag.put("Colors", colorsTag);
    }

    public static int getColor(ItemStack stack)
    {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        NbtList colorsTag = nbtCompound.getList("Colors", NbtElement.COMPOUND_TYPE);
        if (!colorsTag.isEmpty())
        {
            NbtCompound colorTag = colorsTag.getCompound(0);
            return colorTag.getInt("0");
        }
        return DEFAULT_COLOR;
    }
}
