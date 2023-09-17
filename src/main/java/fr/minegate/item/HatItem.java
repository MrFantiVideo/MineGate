package fr.minegate.item;

import fr.minegate.block.entity.PaintedBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HatItem extends Item implements Equipment
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

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        PaintedBlockEntity.addColorTooltip(stack, tooltip);
        List<Integer> colors = PaintedBlockEntity.getColors(stack);
        PaintedBlockEntity.addColorToNBT(stack, colors);
    }

    @Override
    public EquipmentSlot getSlotType()
    {
        return EquipmentSlot.HEAD;
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