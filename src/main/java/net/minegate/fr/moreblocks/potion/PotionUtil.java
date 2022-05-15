package net.minegate.fr.moreblocks.potion;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.potion.Potion;

public class PotionUtil
{
    /**
     * Creation of Potions. (Changes planned next version.)
     **/

    public static class CustomStatusEffect extends StatusEffect
    {
        public CustomStatusEffect(StatusEffectCategory type, int color)
        {
            super(type, color);
        }
    }

    private StatusEffect statusEffect;
    private Potion       potion;
    private Potion       longPotion;
    private Potion       strongPotion;

    public PotionUtil(StatusEffect statusEffect, Potion potion, Potion longPotion, Potion strongPotion)
    {
        this.statusEffect = statusEffect;
        this.potion = potion;
        this.longPotion = longPotion;
        this.strongPotion = strongPotion;
    }

    public StatusEffect getStatusEffect()
    {
        return statusEffect;
    }

    public Potion getPotion()
    {
        return potion;
    }

    public Potion getLongPotion()
    {
        return longPotion;
    }

    public Potion getStrongPotion()
    {
        return strongPotion;
    }
}