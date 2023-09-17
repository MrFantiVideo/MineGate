package fr.minegate.mixin.client.gui.screen.ingame;

import fr.minegate.item.PaintedBookItem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookEditScreen.class)
public class BookEditScreenMixin
{
    /**
     * Display of the background according to the color of the book.
     **/

    @Inject(method = "render", at = @At(value = "RETURN"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        PaintedBookItem.setColorBookScreen(context);
    }
}