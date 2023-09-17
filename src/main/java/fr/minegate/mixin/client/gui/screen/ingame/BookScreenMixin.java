package fr.minegate.mixin.client.gui.screen.ingame;

import fr.minegate.item.PaintedBookItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BookScreen.class)
public class BookScreenMixin
{
    @Inject(method = "render", at = @At(value = "RETURN"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        PaintedBookItem.setColorBookScreen(context);
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
            PaintedBookItem.setBookScreen(stack);
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
            PaintedBookItem.setBookScreen(stack);
        }
    }
}