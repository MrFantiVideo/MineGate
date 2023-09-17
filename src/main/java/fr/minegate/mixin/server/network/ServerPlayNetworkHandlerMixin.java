package fr.minegate.mixin.server.network;

import fr.minegate.MineGate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WritableBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.server.filter.FilteredMessage;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
    private void updateBookContent(List<FilteredMessage> pages, int slotId, CallbackInfo ci)
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
    private void addBook(FilteredMessage title, List<FilteredMessage> pages, int slotId, CallbackInfo ci)
    {
        ItemStack itemStack = this.player.getInventory().getStack(slotId);
        if (itemStack.getItem() instanceof WritableBookItem)
        {
            Item item = Registries.ITEM.get(new Identifier(MineGate.name.toLowerCase(), itemStack.getItem().toString().replace("writable", "written")));
            ItemStack itemStack2 = new ItemStack(item);
            NbtCompound nbtCompound = itemStack.getNbt();
            if (nbtCompound != null)
            {
                itemStack2.setNbt(nbtCompound.copy());
            }

            itemStack2.setSubNbt("author", NbtString.of(this.player.getName().getString()));
            if (this.player.shouldFilterText())
            {
                itemStack2.setSubNbt("title", NbtString.of(title.getString()));
            }
            else
            {
                itemStack2.setSubNbt("filtered_title", NbtString.of(title.getString()));
                itemStack2.setSubNbt("title", NbtString.of(title.raw()));
            }

            this.setTextToBook(pages, (text) ->
            {
                return Text.Serializer.toJson(Text.literal(text));
            }, itemStack2);
            this.player.getInventory().setStack(slotId, itemStack2);
        }
    }

    /**
     * Required for addBook.
     **/

    private void setTextToBook(List<FilteredMessage> messages, UnaryOperator<String> postProcessor, ItemStack book)
    {
        NbtList nbtList = new NbtList();
        if (this.player.shouldFilterText())
        {
            Stream<NbtString> var10000 = messages.stream().map((message) ->
            {
                return NbtString.of(postProcessor.apply(message.getString()));
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
                FilteredMessage filteredMessage = messages.get(i);
                String string = filteredMessage.raw();
                nbtList.add(NbtString.of(postProcessor.apply(string)));
                if (filteredMessage.isFiltered())
                {
                    nbtCompound.putString(String.valueOf(i), postProcessor.apply(filteredMessage.getString()));
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