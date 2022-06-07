package net.minegate.fr.moreblocks.potion;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minegate.fr.moreblocks.MoreBlocks;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import net.minegate.fr.moreblocks.mixin.recipe.BrewingRecipeRegistryMixin;

public class Potions
{
    /**
     * Initialization.
     **/

    public static void init()
    {
        if (DefaultConfig.sizeChange)
        {
            SHRINK_EFFECT = EffectRegister("shrink", 11594216);
            GROW_EFFECT = EffectRegister("grow", 16237267);
            BrewingRecipeRegistryMixin.registerPotionRecipe(net.minecraft.potion.Potions.WATER, Items.LILY_OF_THE_VALLEY, SHRINK_EFFECT.getPotion());
            BrewingRecipeRegistryMixin.registerPotionRecipe(net.minecraft.potion.Potions.WATER, Items.WITHER_ROSE, GROW_EFFECT.getPotion());
            MoreBlocks.ConsoleClient("Loading of the different types of potions.");
        }
    }

    /**
     * List of new potions.
     **/

    public static PotionUtil SHRINK_EFFECT;
    public static PotionUtil GROW_EFFECT;

    /**
     * Registration of effect for potions.
     *
     * @param name  String ID of the Potion.
     * @param color Color of the Potion.
     **/

    private static PotionUtil EffectRegister(String name, int color)
    {
        StatusEffect effect = Registry.register(Registry.STATUS_EFFECT, new Identifier(MoreBlocks.NameClient.toLowerCase(), name), new PotionUtil.CustomStatusEffect(StatusEffectCategory.NEUTRAL, color));
        Potion potion = Registry.register(Registry.POTION, new Identifier(MoreBlocks.NameClient.toLowerCase(), name), new Potion(MoreBlocks.NameClient.toLowerCase() + '.' + name, new StatusEffectInstance(effect, 3600, 1)));
        Potion strongPotion = Registry.register(Registry.POTION, new Identifier(MoreBlocks.NameClient.toLowerCase(), "strong_" + name), new Potion(MoreBlocks.NameClient.toLowerCase() + '.' + name, new StatusEffectInstance(effect, 1800, 1)));
        Potion longPotion = Registry.register(Registry.POTION, new Identifier(MoreBlocks.NameClient.toLowerCase(), "long_" + name), new Potion(MoreBlocks.NameClient.toLowerCase() + '.' + name, new StatusEffectInstance(effect, 9600, 1)));
        return new PotionUtil(effect, potion, longPotion, strongPotion);
    }
}