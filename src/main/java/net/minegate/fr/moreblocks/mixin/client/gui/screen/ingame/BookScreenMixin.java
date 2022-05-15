package net.minegate.fr.moreblocks.mixin.client.gui.screen.ingame;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minegate.fr.moreblocks.MoreBlocks;
import net.minegate.fr.moreblocks.block.BookshelfBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BookScreen.class)
public class BookScreenMixin
{
    /**
     * Display of the background according to the color of the book.
     **/

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 0))
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

    @Environment(EnvType.CLIENT)
    @Mixin(BookScreen.Contents.class)
    public interface BookScreenContentsMixin
    {
        /**
         * Display book characters in the BookScreen interface.
         *
         * @author Mr.FantiVideo
         * @reason Overwrite forced because the injection causes errors.
         */

        @Overwrite
        static BookScreen.Contents create(ItemStack stack)
        {
            if (stack.getItem() instanceof WrittenBookItem)
            {
                return new BookScreen.WrittenBookContents(stack);
            }
            else
            {
                return stack.getItem() instanceof WritableBookItem ? new BookScreen.WritableBookContents(stack) : BookScreen.EMPTY_PROVIDER;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Mixin(BookScreen.WritableBookContents.class)
    public static class WritableBookContentsMixin
    {
        /**
         * Allows you to retrieve the book in the lectern block with WritableBookContents.
         **/

        @Inject(method = "getPages", at = @At("RETURN"))
        private static void get(ItemStack stack, CallbackInfoReturnable<List<String>> cir)
        {
            BookshelfBlock.setBookScreen(stack.getItem());
        }
    }

    @Environment(EnvType.CLIENT)
    @Mixin(BookScreen.WrittenBookContents.class)
    public static class WrittenBookContentsMixin
    {
        /**
         * Allows you to retrieve the book in the lectern block with WrittenBookContents.
         **/

        @Inject(method = "getPages", at = @At("RETURN"))
        private static void get(ItemStack stack, CallbackInfoReturnable<List<String>> cir)
        {
            BookshelfBlock.setBookScreen(stack.getItem());
        }
    }
}