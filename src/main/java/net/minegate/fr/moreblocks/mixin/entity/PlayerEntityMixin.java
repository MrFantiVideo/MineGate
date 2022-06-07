package net.minegate.fr.moreblocks.mixin.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements ScaledEntity
{
    /**
     * Get the dimensions of the entity.
     **/

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    public void getDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> info)
    {
        if (DefaultConfig.sizeChange)
        {
            info.setReturnValue(this.scaleDimensions(info.getReturnValue()));
        }
    }
}