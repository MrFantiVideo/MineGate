package net.minegate.fr.moreblocks.mixin.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin
{
    /**
     * Adapts the scale based on the size of the entity. (Changes planned next version.)
     **/

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;scale(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;F)V"), method = "render", index = 1)
    public MatrixStack adjustScale(LivingEntity livingEntity, MatrixStack matrices, float amount)
    {
        float scale = ((ScaledEntity) livingEntity).getScale();
        matrices.scale(scale, scale, scale);
        return matrices;
    }
}