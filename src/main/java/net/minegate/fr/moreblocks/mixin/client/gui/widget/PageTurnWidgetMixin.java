package net.minegate.fr.moreblocks.mixin.client.gui.widget;

import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.WritableBookItem;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.util.Identifier;
import net.minegate.fr.moreblocks.MoreBlocks;
import net.minegate.fr.moreblocks.block.BookshelfBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PageTurnWidget.class)
public class PageTurnWidgetMixin
{
    /**
     * Makes book arrows appear in color.
     **/

    @ModifyArg(method = "renderButton", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 0))
    private Identifier setShaderTexture(int number, Identifier book)
    {
        Item item = BookshelfBlock.getBookScreen();

        if (item instanceof WritableBookItem && !(item == Items.WRITTEN_BOOK || item == Items.WRITABLE_BOOK))
        {
            return new Identifier(MoreBlocks.NameClient.toLowerCase(), "textures/gui/" + item.toString().replace("writable_", "") + ".png");
        }
        else if (item instanceof WrittenBookItem && !(item == Items.WRITTEN_BOOK || item == Items.WRITABLE_BOOK))
        {
            return new Identifier(MoreBlocks.NameClient.toLowerCase(), "textures/gui/" + item.toString().replace("written_", "") + ".png");
        }
        return BookScreen.BOOK_TEXTURE;
    }
}