package net.minegate.fr.moreblocks.mixin.server.network;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WritableBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minegate.fr.moreblocks.MoreBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin
{
    @Shadow
    public ServerPlayerEntity player;

    /**
     * Updates book content by accepting color books.
     **/

    @Inject(method = "updateBookContent", at = @At("RETURN"))
    private void updateBookContent(List<TextStream.Message> pages, int slotId, CallbackInfo ci)
    {
        ItemStack itemStack = this.player.getInventory().getStack(slotId);
        if (itemStack.getItem() instanceof WritableBookItem)
        {
            this.setTextToBook(pages, UnaryOperator.identity(), itemStack);
        }
    }

    /**
     * Allows to transform the book writable to written depending on the color.
     **/

    @Inject(method = "addBook", at = @At("RETURN"))
    private void addBook(TextStream.Message title, List<TextStream.Message> pages, int slotId, CallbackInfo ci)
    {
        ItemStack itemStack = this.player.getInventory().getStack(slotId);
        if (itemStack.getItem() instanceof WritableBookItem)
        {
            Item item = Registry.ITEM.get(new Identifier(MoreBlocks.NameClient.toLowerCase(), itemStack.getItem().toString().replace("writable", "written")));
            ItemStack itemStack2 = new ItemStack(item);
            NbtCompound nbtCompound = itemStack.getNbt();
            if (nbtCompound != null)
            {
                itemStack2.setNbt(nbtCompound.copy());
            }

            itemStack2.setSubNbt("author", NbtString.of(this.player.getName().getString()));
            if (this.player.shouldFilterText())
            {
                itemStack2.setSubNbt("title", NbtString.of(title.getFiltered()));
            }
            else
            {
                itemStack2.setSubNbt("filtered_title", NbtString.of(title.getFiltered()));
                itemStack2.setSubNbt("title", NbtString.of(title.getRaw()));
            }

            this.setTextToBook(pages, (string) ->
            {
                return Text.Serializer.toJson(new LiteralText(string));
            }, itemStack2);
            this.player.getInventory().setStack(slotId, itemStack2);
        }
    }

    /**
     * Required for addBook.
     **/

    private void setTextToBook(List<TextStream.Message> messages, UnaryOperator<String> postProcessor, ItemStack book)
    {
        NbtList nbtList = new NbtList();
        if (this.player.shouldFilterText())
        {
            Stream<NbtString> var10000 = messages.stream().map((messagex) ->
            {
                return NbtString.of(postProcessor.apply(messagex.getFiltered()));
            });
            Objects.requireNonNull(nbtList);
            var10000.forEach(nbtList::add);
        }
        else
        {
            NbtCompound nbtCompound = new NbtCompound();
            int i = 0;

            for (int j = messages.size(); i < j; ++i)
            {
                TextStream.Message message = messages.get(i);
                String string = message.getRaw();
                nbtList.add(NbtString.of(postProcessor.apply(string)));
                String string2 = message.getFiltered();
                if (!string.equals(string2))
                {
                    nbtCompound.putString(String.valueOf(i), postProcessor.apply(string2));
                }
            }

            if (!nbtCompound.isEmpty())
            {
                book.setSubNbt("filtered_pages", nbtCompound);
            }
        }
        book.setSubNbt("pages", nbtList);
    }
}