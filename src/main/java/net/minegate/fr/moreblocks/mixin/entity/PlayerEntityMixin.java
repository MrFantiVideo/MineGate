package net.minegate.fr.moreblocks.mixin.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{
    /**
     * Get the dimensions of the entity.
     **/

    @Inject(at = @At("RETURN"), method = "getDimensions", cancellable = true)
    public void getDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> info)
    {
        info.setReturnValue(((ScaledEntity) this).scaleDimensions(info.getReturnValue()));
    }
}