package fr.minegate.mixin.entity;

import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LecternBlockEntity.class)
public class LecternBlockEntityMixin
{
    @Shadow
    ItemStack book;

    /**
     * Checks when you open the LecternBlock menu if it is indeed a book.
     *
     * @author Mr.FantiVideo
     * @reason Overwrite forced because the injection crashes the game.
     **/

    @Overwrite
    public boolean hasBook()
    {
        return this.book.getItem() instanceof WritableBookItem || this.book.getItem() instanceof WrittenBookItem;
    }

    /**
     * Set if a color book has been opened for the first time. (Client)
     **/

    @ModifyArg(method = "resolveBook", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private Item resolveBook(Item book)
    {
        if (book instanceof WrittenBookItem)
        {
            return book;
        }
        return Items.WRITTEN_BOOK;
    }
}