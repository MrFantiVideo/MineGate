package net.minegate.fr.moreblocks.recipe;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public abstract class WoodRecipe implements Recipe<Inventory> {
    protected final Ingredient input;
    protected final ItemStack output;
    private final   RecipeType<?>                            type;
    private final   net.minecraft.recipe.RecipeSerializer<?> serializer;
    protected final Identifier                               id;
    protected final String group;

    public WoodRecipe(RecipeType<?> type, net.minecraft.recipe.RecipeSerializer<?> serializer, Identifier id, String group, Ingredient input, ItemStack output) {
        this.type = type;
        this.serializer = serializer;
        this.id = id;
        this.group = group;
        this.input = input;
        this.output = output;
    }

    public RecipeType<?> getType() {
        return this.type;
    }

    public net.minecraft.recipe.RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    public Identifier getId() {
        return this.id;
    }

    @Environment(EnvType.CLIENT)
    public String getGroup() {
        return this.group;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public DefaultedList<Ingredient> getPreviewInputs() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.input);
        return defaultedList;
    }

    @Environment(EnvType.CLIENT)
    public boolean fits(int width, int height) {
        return true;
    }

    public ItemStack craft(Inventory inv) {
        return this.output.copy();
    }

    public static class Serializer<T extends WoodRecipe> implements RecipeSerializer<T>
    {
        final WoodRecipe.Serializer.RecipeFactory<T> recipeFactory;

        protected Serializer(WoodRecipe.Serializer.RecipeFactory<T> recipeFactory) {
            this.recipeFactory = recipeFactory;
        }

        public T read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            Ingredient ingredient2;
            if (JsonHelper.hasArray(jsonObject, "ingredient")) {
                ingredient2 = Ingredient.fromJson(JsonHelper.getArray(jsonObject, "ingredient"));
            } else {
                ingredient2 = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "ingredient"));
            }

            String string2 = JsonHelper.getString(jsonObject, "result");
            int i = JsonHelper.getInt(jsonObject, "count");
            ItemStack itemStack = new ItemStack((ItemConvertible)Registry.ITEM.get(new Identifier(string2)), i);
            return this.recipeFactory.create(identifier, string, ingredient2, itemStack);
        }

        public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString(32767);
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            ItemStack itemStack = packetByteBuf.readItemStack();
            return this.recipeFactory.create(identifier, string, ingredient, itemStack);
        }

        public void write(PacketByteBuf packetByteBuf, T woodRecipe) {
            packetByteBuf.writeString(woodRecipe.group);
            woodRecipe.input.write(packetByteBuf);
            packetByteBuf.writeItemStack(woodRecipe.output);
        }

        interface RecipeFactory<T extends WoodRecipe> {
            T create(Identifier identifier, String string, Ingredient ingredient, ItemStack itemStack);
        }
    }
}