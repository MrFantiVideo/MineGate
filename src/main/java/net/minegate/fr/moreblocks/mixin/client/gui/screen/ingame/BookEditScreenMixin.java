package net.minegate.fr.moreblocks.mixin.client.gui.screen.ingame;

import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import net.minegate.fr.moreblocks.MoreBlocks;
import net.minegate.fr.moreblocks.block.BookshelfBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BookEditScreen.class)
public class BookEditScreenMixin
{
    /**
     * Display of the background according to the color of the book.
     **/

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 0))
    private Identifier setShaderTexture(int number, Identifier book)
    {
        Item item = BookshelfBlock.getBookScreen();

        if (item == Items.WRITABLE_BOOK)
        {
            return BookScreen.BOOK_TEXTURE;
        }
        return new Identifier(MoreBlocks.NameClient.toLowerCase(), "textures/gui/" + item.toString().replace("writable_", "") + ".png");
    }
}