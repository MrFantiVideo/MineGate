package net.minegate.fr.moreblocks.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.StonecutterScreenHandler;

public class WoodcutterScreenHandler extends StonecutterScreenHandler
{
    private final ScreenHandlerContext    context;
    private final CraftingResultInventory output;

    /**
     * Opens the stonecutter menu for the woodcutter.
     **/

    public WoodcutterScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context)
    {
        super(syncId, playerInventory);
        this.output = new CraftingResultInventory();
        this.context = context;
    }

    /**
     * Checks if the player can open the menu depending on the block.
     **/

    public boolean canUse(PlayerEntity player)
    {
        return canUse(this.context, player, net.minegate.fr.moreblocks.block.Blocks.WOODCUTTER);
    }

    /**
     * Don't delete the item if you leave the menu.
     */

    public void close(PlayerEntity player)
    {
        super.close(player);
        this.output.removeStack(1);
        this.context.run((world, pos) ->
        {
            this.dropInventory(player, this.input);
        });
    }
}