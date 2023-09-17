package fr.minegate.item;

import fr.minegate.screen.PaintingPaletteScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PaintingPaletteItem extends Item
{
    private static final Text TITLE = Text.translatable("container.painting_palette");

    public PaintingPaletteItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        if (world.isClient)
        {
            return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
        }
        else
        {
            player.openHandledScreen(this.createScreenHandlerFactory(world, player.getBlockPos()));
        }
        return new TypedActionResult<>(ActionResult.CONSUME, player.getStackInHand(hand));
    }

    public NamedScreenHandlerFactory createScreenHandlerFactory(World world, BlockPos pos)
    {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
        {
            return new PaintingPaletteScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos));
        }, TITLE);
    }
}
