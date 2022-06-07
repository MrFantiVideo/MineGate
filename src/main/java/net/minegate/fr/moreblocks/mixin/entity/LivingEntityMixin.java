package net.minegate.fr.moreblocks.mixin.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import net.minegate.fr.moreblocks.potion.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ScaledEntity
{
    private static final UUID               SCALED_SPEED_ID;
    private static final TrackedData<Float> SCALE;

    /**
     * Entity size management. (Changes planned next version.)
     **/

    public LivingEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Shadow
    public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

    @Shadow
    public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Override
    public float getScale()
    {
        if (DefaultConfig.sizeChange)
        {
            return this.getDataTracker().get(SCALE);
        }
        return 1.0F;
    }

    private float computeScale()
    {
        Map<StatusEffect, StatusEffectInstance> effects = getActiveStatusEffects();
        if (effects != null && effects.size() > 0)
        {
            int shrink = effects.get(Potions.SHRINK_EFFECT.getStatusEffect()) != null ? effects.get(Potions.SHRINK_EFFECT.getStatusEffect()).getAmplifier() + 1 : 0;
            int grow = effects.get(Potions.GROW_EFFECT.getStatusEffect()) != null ? effects.get(Potions.GROW_EFFECT.getStatusEffect()).getAmplifier() + 1 : 0;
            float value = grow - shrink;
            if (value > 0)
            {
                return (float) Math.pow(2.0F, value);
            }
            else
            {
                return (float) Math.pow(0.5F, Math.abs(value));
            }
        }
        else
        {
            return 1.0F;
        }
    }

    @Inject(at = @At("RETURN"), method = "getDimensions", cancellable = true)
    public void getDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> info)
    {
        if (DefaultConfig.sizeChange)
        {
            info.setReturnValue(scaleDimensions(info.getReturnValue()));
        }
    }

    @Override
    public EntityDimensions scaleDimensions(EntityDimensions dimensions)
    {
        float scale = getScale();
        return new EntityDimensions(Math.max(dimensions.width * scale, 0.5F), Math.max(dimensions.height * scale, 0.5F), dimensions.fixed);
    }

    @Inject(at = @At("RETURN"), method = "getJumpVelocity", cancellable = true)
    public void getJumpVelocity(CallbackInfoReturnable<Float> info)
    {
        if (DefaultConfig.sizeChange)
        {
            info.setReturnValue(info.getReturnValue() * (float) Math.pow(getScale(), 0.4F));
        }
    }

    @Inject(at = @At("RETURN"), method = "getEyeHeight", cancellable = true)
    public void getEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> info)
    {
        if (DefaultConfig.sizeChange)
        {
            if (pose != EntityPose.SLEEPING)
            {
                info.setReturnValue(info.getReturnValue() * getScale());
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info)
    {
        if (DefaultConfig.sizeChange)
        {
            if (!this.getEntityWorld().isClient())
            {
                float scale = computeScale();
                if (this.getDataTracker().get(SCALE) != scale)
                {
                    this.getDataTracker().set(SCALE, computeScale());
                }
            }

            EntityAttributeInstance speedAttribute = getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (speedAttribute.getModifier(SCALED_SPEED_ID) != null)
            {
                speedAttribute.removeModifier(SCALED_SPEED_ID);
            }
            speedAttribute.addTemporaryModifier(new EntityAttributeModifier(SCALED_SPEED_ID, "Scaled speed multiplier", Math.pow(getScale(), 0.4) - 1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    /**
     * Allows to initiate the size on the entities.
     **/

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initDataTracker(CallbackInfo info)
    {
        if (DefaultConfig.sizeChange)
        {
            dataTracker.startTracking(SCALE, 1.0F);
        }
    }

    @Inject(method = "onTrackedDataSet", at = @At("RETURN"))
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo info)
    {
        if (DefaultConfig.sizeChange)
        {
            if (SCALE.equals(data))
            {
                this.calculateDimensions();
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "computeFallDamage", cancellable = true)
    public void computeFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> info)
    {
        if (DefaultConfig.sizeChange)
        {
            info.setReturnValue((int) (info.getReturnValue() * Math.pow(getScale(), -1)));
        }
    }

    static
    {
        SCALED_SPEED_ID = UUID.fromString("c5267238-6a78-4257-ae83-a2a5e34c1128");
        SCALE = DataTracker.registerData(LivingEntityMixin.class, TrackedDataHandlerRegistry.FLOAT);
    }
}