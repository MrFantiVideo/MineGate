package fr.minegate.item;

import fr.minegate.MineGate;
import fr.minegate.block.Blocks;
import fr.minegate.util.PaintColor;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroups
{
    public static final RegistryKey<ItemGroup> PAINTED_BLOCK = register("painted_blocks");

    /**
     * Inventory of MineGate operator utilities blocks.
     */

    private static RegistryKey<ItemGroup> register(String id)
    {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MineGate.name.toLowerCase(), id));
    }


    public static void init()
    {
        Registry.register(Registries.ITEM_GROUP, PAINTED_BLOCK, ItemGroup.create(ItemGroup.Row.TOP, 0)
                .displayName(Text.translatable("itemGroup.minegate.paintedBlocks"))
                .icon(() -> new ItemStack(Items.PAINTING_PALETTE))
                .entries((displayContext, entries) ->
                {
                    for (PaintColor paintColor : PaintColor.values())
                    {
                        //if (paintColor.isVanilla())
                        //{
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_BLOCK), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_BLOCK_VERTICAL_STAIRS), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_BLOCK_STAIRS), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_BLOCK_VERTICAL_SLAB), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_BLOCK_SLAB), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_BLOCK_QUARTER), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_BLOCK_EIGHTH), paintColor.getColor()));


                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_BARS), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_WALL), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_FENCE), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_FENCE_GATE), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_DOOR), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_TRAPDOOR), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_PRESSURE_PLATE), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_IRON_BUTTON), paintColor.getColor()));

                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_OAK_PLANKS), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_OAK_VERTICAL_STAIRS), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_OAK_STAIRS), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_OAK_VERTICAL_SLAB), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_OAK_SLAB), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_OAK_QUARTER), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_OAK_EIGHTH), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_OAK_LANTERN), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_OAK_LANTERN_ROD), paintColor.getColor()));

                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_WOOL), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_CARPET), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_TERRACOTTA), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_CONCRETE), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_CONCRETE_POWDER), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_STAINED_GLASS), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_STAINED_GLASS_PANE), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Blocks.PAINTED_CANDLE), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Items.PAINTED_BOOK), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Items.WRITABLE_PAINTED_BOOK), paintColor.getColor()));

                            entries.add(PaintedBlockNbt(new ItemStack(Items.CHRISTMAS_HAT), paintColor.getColor()));
                            entries.add(PaintedBlockNbt(new ItemStack(Items.CLASSY_HAT), paintColor.getColor()));

                            entries.add(PaintedBlockNbt(new ItemStack(Items.PAINT_BRUSH), paintColor.getColor()));
                        //}
                    }
                }).build());

        ItemGroupEvents.modifyEntriesEvent(net.minecraft.item.ItemGroups.BUILDING_BLOCKS).register((content) ->
        {
            content.addBefore(net.minecraft.block.Blocks.OAK_STAIRS, Blocks.OAK_VERTICAL_STAIRS);
            content.addBefore(net.minecraft.block.Blocks.OAK_SLAB, Blocks.OAK_VERTICAL_SLAB);
            content.addAfter(net.minecraft.block.Blocks.OAK_SLAB, Blocks.OAK_EIGHTH);
            content.addAfter(net.minecraft.block.Blocks.OAK_SLAB, Blocks.OAK_QUARTER);

            content.addBefore(net.minecraft.block.Blocks.STONE_STAIRS, Blocks.STONE_VERTICAL_STAIRS);
            content.addBefore(net.minecraft.block.Blocks.STONE_SLAB, Blocks.STONE_VERTICAL_SLAB);
            content.addAfter(net.minecraft.block.Blocks.STONE_SLAB, Blocks.STONE_EIGHTH);
            content.addAfter(net.minecraft.block.Blocks.STONE_SLAB, Blocks.STONE_QUARTER);

            content.addAfter(net.minecraft.block.Blocks.IRON_BLOCK, Blocks.IRON_BLOCK_EIGHTH);
            content.addAfter(net.minecraft.block.Blocks.IRON_BLOCK, Blocks.IRON_BLOCK_QUARTER);
            content.addAfter(net.minecraft.block.Blocks.IRON_BLOCK, Blocks.IRON_BLOCK_SLAB);
            content.addAfter(net.minecraft.block.Blocks.IRON_BLOCK, Blocks.IRON_BLOCK_VERTICAL_SLAB);
            content.addAfter(net.minecraft.block.Blocks.IRON_BLOCK, Blocks.IRON_BLOCK_STAIRS);
            content.addAfter(net.minecraft.block.Blocks.IRON_BLOCK, Blocks.IRON_BLOCK_VERTICAL_STAIRS);
        });

        ItemGroupEvents.modifyEntriesEvent(net.minecraft.item.ItemGroups.TOOLS).register((content) ->
        {
            content.addAfter(net.minecraft.item.Items.BRUSH, Items.PAINTING_PALETTE);
        });

        ItemGroupEvents.modifyEntriesEvent(net.minecraft.item.ItemGroups.INGREDIENTS).register((content) ->
        {
            content.addBefore(net.minecraft.item.Items.PAPER, Items.EMPTY_PAINTING_PALETTE);
            content.addAfter(Items.EMPTY_PAINTING_PALETTE, Items.EMPTY_EYEDROPPER);
        });

        ItemGroupEvents.modifyEntriesEvent(net.minecraft.item.ItemGroups.OPERATOR).register((content) ->
        {
            content.add(new ItemStack(Blocks.PORTAL));
        });
    }

    public static ItemStack PaintedBlockNbt(ItemStack itemStack, int color)
    {
        NbtCompound nbt = new NbtCompound();
        NbtList colorsTag = new NbtList();
        NbtCompound colorTag = new NbtCompound();
        colorTag.putInt("0", color);
        if(itemStack.getItem() == Blocks.PAINTED_OAK_LANTERN.asItem())
        {
            colorTag.putInt("1", 13691625);
            colorsTag.add(colorTag);
            colorTag.putInt("2", 16776429);
            colorsTag.add(colorTag);
        }
        colorsTag.add(colorTag);
        nbt.put("Colors", colorsTag);
        itemStack.setNbt(nbt);
        return itemStack;
    }
}
