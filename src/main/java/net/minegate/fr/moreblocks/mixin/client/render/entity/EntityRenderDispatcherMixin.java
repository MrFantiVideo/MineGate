package net.minegate.fr.moreblocks.mixin.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldView;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin
{
    /**
     * Adapts the shadow based on the size of the entity. (Changes planned next version.)
     **/

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;renderShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/Entity;FFLnet/minecraft/world/WorldView;F)V"), method = "render", index = 6)
    public float adjustShadowSize(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity, float darkness, float tickDelta, WorldView world, float size)
    {
        if (entity instanceof ScaledEntity)
        {
            return ((ScaledEntity) entity).getScale() * size;
        }
        else
        {
            return size;
        }
    }
}