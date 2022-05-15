package net.minegate.fr.moreblocks.mixin.recipe;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeRegistryMixin
{
    /**
     * Allows you to create a new recipe in brewing stand for potions.
     **/

    @Invoker("registerPotionRecipe")
    static void registerPotionRecipe(Potion input, Item item, Potion output)
    {
        throw new UnsupportedOperationException();
    }
}