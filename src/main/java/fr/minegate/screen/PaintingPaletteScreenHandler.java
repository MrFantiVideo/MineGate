package fr.minegate.screen;

import fr.minegate.client.gui.screen.ingame.PaintingPaletteScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class PaintingPaletteScreenHandler extends ScreenHandler
{
    protected final ScreenHandlerContext    context;
    protected final PlayerEntity            player;
    protected final Inventory               input;
    protected       CraftingResultInventory output;
    public          String                  color;


    public PaintingPaletteScreenHandler(int syncId, PlayerInventory playerInventory)
    {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public PaintingPaletteScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context)
    {
        super(MineGateScreenHandlerType.PAINTING_PALETTE_SCREEN, syncId);
        this.context = context;
        this.player = playerInventory.player;
        this.input = new CraftingInventory(this, 1, 1);
        this.output = new CraftingResultInventory();
        this.color = "fff";

        this.addSlot(new Slot(this.input, 0, 12, 20)
        {
            public boolean canInsert(ItemStack stack)
            {
                if (stack.getItem().equals(fr.minegate.item.Items.PAINT_BRUSH) || stack.getItem().equals(Items.BRUSH))
                {
                    return true;
                }
                return false;
            }
        });
        this.addSlot(new Slot(this.output, 1, 149, 20)
        {
            public boolean canInsert(ItemStack stack)
            {
                return false;
            }
        });

        int j;
        for (j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, j * 18 + 51));
            }
        }
        for (j = 0; j < 9; ++j)
        {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 109));
        }
    }

    String getColor()
    {
        if (PaintingPaletteScreen.getColor() != null)
        {
            color = PaintingPaletteScreen.getColor();
        }
        return color;
    }

    @Override
    public void sendContentUpdates()
    {
        super.sendContentUpdates();
        updateResult();
    }

    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player)
    {
        super.onSlotClick(slotIndex, button, actionType, player);

        // Si l'action a été effectuée sur le slot output (indice 1) et c'était un clic de ramassage (PICKUP) ou de ramassage rapide (QUICK_MOVE)
        if (slotIndex == 1 && (actionType == SlotActionType.PICKUP || actionType == SlotActionType.QUICK_MOVE))
        {
            this.input.setStack(0, ItemStack.EMPTY);
            this.output.setStack(1, ItemStack.EMPTY);
        }
        updateResult();
    }

    public void updateResult()
    {
        ItemStack inputStack = this.input.getStack(0);

        if (inputStack.isEmpty())
        {
            this.output.setStack(1, ItemStack.EMPTY);
        }
        else
        {
            ItemStack outputStack = inputStack.copy();
            if (outputStack.getItem().equals(Items.BRUSH))
            {
                outputStack = new ItemStack(fr.minegate.item.Items.PAINT_BRUSH);
            }
            if (PaintingPaletteScreen.getColor().isBlank())
            {
                this.output.setStack(1, ItemStack.EMPTY);
            }
            else
            {
                NbtCompound tag = new NbtCompound();
                NbtList colorsTag = new NbtList();
                NbtCompound colorCompound = new NbtCompound();
                colorCompound.putInt("0", Integer.parseInt(getColor(), 16));
                colorsTag.add(colorCompound);
                tag.put("Colors", colorsTag);
                outputStack.setNbt(tag);
                this.output.setStack(1, outputStack);
            }
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot)
    {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot clickedSlot = this.slots.get(slot);

        if (clickedSlot != null && clickedSlot.hasStack())
        {
            ItemStack clickedStack = clickedSlot.getStack();
            itemStack = clickedStack.copy();

            if (slot == 1)
            {
                if (!this.insertItem(clickedStack, 2, 38, true))
                {
                    return ItemStack.EMPTY;
                }

                clickedSlot.insertStack(itemStack);
            }
            else if (slot == 0)
            {
                if (!this.insertItem(clickedStack, 2, 38, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (clickedStack.getItem().equals(fr.minegate.item.Items.PAINT_BRUSH) || clickedStack.getItem().equals(Items.BRUSH))
            {
                if (!this.insertItem(clickedStack, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                if (!this.insertItem(clickedStack, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            if (clickedStack.isEmpty())
            {
                clickedSlot.setStack(ItemStack.EMPTY);
            }
            else
            {
                clickedSlot.markDirty();
            }
            if (clickedStack.getCount() == itemStack.getCount())
            {
                return ItemStack.EMPTY;
            }
            clickedSlot.onTakeItem(player, clickedStack);
        }
        return itemStack;
    }

    @Override
    public void onClosed(PlayerEntity player)
    {
        super.onClosed(player);

        ItemStack inputItem = this.input.getStack(0);

        if (!inputItem.isEmpty())
        {
            if (!player.getInventory().insertStack(inputItem))
            {
                player.dropItem(inputItem, false);
            }
        }
    }

    public boolean canUse(PlayerEntity player)
    {
        return true;
    }
}
