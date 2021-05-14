package net.minegate.fr.moreblocks.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.Blocks;

public class WoodcuttingRecipe extends CuttingRecipe {
    public WoodcuttingRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
        super(RecipeType.STONECUTTING, (net.minecraft.recipe.RecipeSerializer<?>) RecipeSerializer.STONECUTTING, id, group, input, output);
    }

    public boolean matches(Inventory inv, World world) {
        return this.input.test(inv.getStack(0));
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getRecipeKindIcon() {
        return new ItemStack(Blocks.WOODCUTTER);
    }
}
