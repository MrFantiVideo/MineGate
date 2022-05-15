package net.minegate.fr.moreblocks.mixin.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import net.minegate.fr.moreblocks.potion.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ScaledEntity
{
    /**
     * Entity size management. (Changes planned next version.)
     **/

    @Unique
    private static final UUID SCALED_SPEED_ID = UUID.fromString("c5267238-6a78-4257-ae83-a2a5e34c1128");
    @Unique
    private static final TrackedData<Float> SCALE = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Shadow
    public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

    @Shadow
    public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Override
    public float getScale() {
        return ((LivingEntity) (Object) this).getDataTracker().get(SCALE);
    }

    private float computeScale() {
        Map<StatusEffect, StatusEffectInstance> effects = getActiveStatusEffects();
        if (effects != null && effects.size() > 0) {
            int shrink = effects.get(Potions.SHRINK_EFFECT.getStatusEffect()) != null ? effects.get(Potions.SHRINK_EFFECT.getStatusEffect()).getAmplifier() + 1 : 0;
            int grow = effects.get(Potions.GROW_EFFECT.getStatusEffect()) != null ? effects.get(Potions.GROW_EFFECT.getStatusEffect()).getAmplifier() + 1 : 0;
            float value = grow - shrink;
            if (value > 0) {
                return (float) Math.pow(2f, value);
            } else {
                return (float) Math.pow(0.5f, Math.abs(value));
            }
        } else {
            return 1f;
        }
    }

    @Inject(at = @At("RETURN"), method = "getDimensions", cancellable = true)
    public void getDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> info) {
        info.setReturnValue(scaleDimensions(info.getReturnValue()));
    }

    @Override
    public EntityDimensions scaleDimensions(EntityDimensions dimensions) {
        float scale = getScale();
        return new EntityDimensions(Math.max(dimensions.width * scale, 0.5f), Math.max(dimensions.height * scale, 0.5f), dimensions.fixed);
    }

    @Inject(at = @At("RETURN"), method = "getJumpVelocity", cancellable = true)
    public void getJumpVelocity(CallbackInfoReturnable<Float> info) {
        info.setReturnValue(info.getReturnValue() * (float) Math.pow(getScale(), 0.4f));
    }

    @Inject(at = @At("RETURN"), method = "getEyeHeight", cancellable = true)
    public void getEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> info) {
        if (pose != EntityPose.SLEEPING) {
            info.setReturnValue(info.getReturnValue() * getScale());
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
        if (!((LivingEntity) (Object) this).getEntityWorld().isClient()) {
            float scale = computeScale();
            if (((LivingEntity) (Object) this).getDataTracker().get(SCALE) != scale) {
                ((LivingEntity) (Object) this).getDataTracker().set(SCALE, computeScale());
            }
        }

        EntityAttributeInstance speedAttribute = getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (speedAttribute.getModifier(SCALED_SPEED_ID) != null) {
            speedAttribute.removeModifier(SCALED_SPEED_ID);
        }
        speedAttribute.addTemporaryModifier(new EntityAttributeModifier(SCALED_SPEED_ID, "Scaled speed multiplier", Math.pow(getScale(), 0.4) - 1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Inject(at = @At("RETURN"), method = "initDataTracker")
    public void initDataTracker(CallbackInfo info) {
        ((LivingEntity) (Object) this).getDataTracker().startTracking(SCALE, 1f);
    }

    @Inject(at = @At("RETURN"), method = "onTrackedDataSet")
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo info) {
        if (SCALE.equals(data)) {
            ((LivingEntity) (Object) this).calculateDimensions();
        }
    }

    @Inject(at = @At("RETURN"), method = "computeFallDamage", cancellable = true)
    public void computeFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> info) {
        info.setReturnValue((int) (info.getReturnValue() * Math.pow(getScale(), -1)));
    }
}
