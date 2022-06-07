package net.minegate.fr.moreblocks.mixin.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public class CameraMixin
{
    @Shadow
    private Entity focusedEntity;

    /**
     * Adapts the camera based on the size of the entity.
     **/

    @Inject(method = "clipToSpace", at = @At("TAIL"), cancellable = true)
    public void clipToSpace(double desiredCameraDistance, CallbackInfoReturnable<Double> cir)
    {
        if (DefaultConfig.sizeChange)
        {
            float scale = focusedEntity instanceof ScaledEntity ? ((ScaledEntity) focusedEntity).getScale() : 1.0F;
            cir.setReturnValue(desiredCameraDistance * scale);
        }
    }
}