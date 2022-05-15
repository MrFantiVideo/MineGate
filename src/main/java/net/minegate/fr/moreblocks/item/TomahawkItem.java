package net.minegate.fr.moreblocks.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.entity.projectile.TomahawkEntity;

import java.util.Set;
import java.util.function.Consumer;

public class TomahawkItem extends ToolItem implements Vanishable
{
    private final        Multimap<EntityAttribute, EntityAttributeModifier> field_23746;
    private static final Set<Material>                                      field_23139;
    private static final float                                              miningSpeed = 7.0F;

    public TomahawkItem(ToolMaterials material, Item.Settings settings)
    {
        super(material, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 8.0D, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", -2.9000000953674316D, EntityAttributeModifier.Operation.ADDITION));
        this.field_23746 = builder.build();
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state)
    {
        Material material = state.getMaterial();
        return field_23139.contains(material) ? TomahawkItem.miningSpeed : super.getMiningSpeedMultiplier(stack, state);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner)
    {
        return !miner.isCreative();
    }

    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.SPEAR;
    }

    public int getMaxUseTime(ItemStack stack)
    {
        return 72000;
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks)
    {
        if (user instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) user;
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            if (i >= 10)
            {
                int j = EnchantmentHelper.getRiptide(stack);
                if (j <= 0 || playerEntity.isTouchingWaterOrRain())
                {
                    if (!world.isClient)
                    {
                        stack.damage(1, playerEntity, (Consumer<LivingEntity>) ((p) -> p.sendToolBreakStatus(user.getActiveHand())));
                        if (j == 0)
                        {
                            TomahawkEntity tridentEntity = new TomahawkEntity(world, playerEntity, stack);
                            tridentEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 1.5F + (float) j * 0.5F, 1.0F);
                            if (playerEntity.getAbilities().creativeMode)
                            {
                                tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                            }

                            world.spawnEntity(tridentEntity);
                            world.playSoundFromEntity(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            if (!playerEntity.getAbilities().creativeMode)
                            {
                                playerEntity.getInventory().removeOne(stack);
                            }
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                    if (j > 0)
                    {
                        float tridentEntity = playerEntity.getYaw();
                        float f = playerEntity.getPitch();
                        float g = -MathHelper.sin(tridentEntity * 0.017453292F) * MathHelper.cos(f * 0.017453292F);
                        float h = -MathHelper.sin(f * 0.017453292F);
                        float k = MathHelper.cos(tridentEntity * 0.017453292F) * MathHelper.cos(f * 0.017453292F);
                        float l = MathHelper.sqrt(g * g + h * h + k * k);
                        float m = 3.0F * ((1.0F + (float) j) / 4.0F);
                        g *= m / l;
                        h *= m / l;
                        k *= m / l;
                        playerEntity.addVelocity((double) g, (double) h, (double) k);
                        playerEntity.useRiptide(20);
                        if (playerEntity.isOnGround())
                        {
                            float n = 1.1999999F;
                            playerEntity.move(MovementType.SELF, new Vec3d(0.0D, 1.1999999284744263D, 0.0D));
                        }

                        SoundEvent n;
                        if (j >= 3)
                        {
                            n = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                        }
                        else if (j == 2)
                        {
                            n = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                        }
                        else
                        {
                            n = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                        }

                        world.playSoundFromEntity((PlayerEntity) null, playerEntity, n, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }

                }
            }
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1)
        {
            return TypedActionResult.fail(itemStack);
        }
        else if (EnchantmentHelper.getRiptide(itemStack) > 0 && !user.isTouchingWaterOrRain())
        {
            return TypedActionResult.fail(itemStack);
        }
        else
        {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner)
    {
        if ((double) state.getHardness(world, pos) != 0.0D)
        {
            stack.damage(2, miner, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot)
    {
        return slot == EquipmentSlot.MAINHAND ? this.field_23746 : super.getAttributeModifiers(slot);
    }

    public int getEnchantability()
    {
        return 1;
    }

    static
    {
        field_23139 = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANT, Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.GOURD);
    }
}