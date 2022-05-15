package net.minegate.fr.moreblocks.mixin.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public abstract class CameraMixin
{
    @Shadow
    private Entity focusedEntity;

    @Shadow
    protected abstract double clipToSpace(double distance);

    @Shadow
    protected abstract void moveBy(double x, double y, double z);

    /**
     * Adapts the camera based on the size of the entity. (Changes planned next version.)
     **/

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;clipToSpace(D)D"))
    public double onUpdateClipToSpaceProxy(Camera obj, double distance)
    {
        float scale = focusedEntity instanceof ScaledEntity ? ((ScaledEntity) focusedEntity).getScale() : 1f;
        return clipToSpace(distance * scale);
    }
}