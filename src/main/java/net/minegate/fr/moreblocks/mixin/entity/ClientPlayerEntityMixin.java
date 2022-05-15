package net.minegate.fr.moreblocks.mixin.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WritableBookItem;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.util.Hand;
import net.minegate.fr.moreblocks.block.BookshelfBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin
{
    @Final
    @Shadow
    protected MinecraftClient client;

    /**
     * Opens the writable or written book screen with hand.
     **/

    @Inject(method = "useBook", at = @At("RETURN"))
    private void useBook(ItemStack book, Hand hand, CallbackInfo ci)
    {
        BookshelfBlock.setBookScreen(book.getItem());

        if (book.getItem() instanceof WritableBookItem)
        {
            this.client.setScreen(new BookEditScreen(this.client.player, book, hand));
        }
        if (book.getItem() instanceof WrittenBookItem)
        {
            this.client.setScreen(new BookScreen(new BookScreen.WrittenBookContents(book)));
        }
    }
}