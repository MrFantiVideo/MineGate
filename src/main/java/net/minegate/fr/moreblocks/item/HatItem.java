package net.minegate.fr.moreblocks.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HatItem extends Item implements Wearable
{
    /**
     * Creation of a hat item.
     *
     * @param settings Item settings.
     **/

    public HatItem(FabricItemSettings settings)
    {
        super(settings.equipmentSlot((itemStack) -> EquipmentSlot.HEAD));
    }

    /**
     * Allows you to put on the hat with a simple click.
     **/

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack itemStack = user.getStackInHand(hand);
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(itemStack);
        ItemStack itemStack2 = user.getEquippedStack(equipmentSlot);
        if (itemStack2.isEmpty())
        {
            user.equipStack(equipmentSlot, itemStack.copy());
            if (!world.isClient())
            {
                user.incrementStat(Stats.USED.getOrCreateStat(this));
            }
            itemStack.setCount(0);
            return TypedActionResult.success(itemStack, world.isClient());
        }
        else
        {
            return TypedActionResult.fail(itemStack);
        }
    }

    /**
     * Sound when equipping the hat.
     **/

    @Nullable
    public SoundEvent getEquipSound()
    {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }
}