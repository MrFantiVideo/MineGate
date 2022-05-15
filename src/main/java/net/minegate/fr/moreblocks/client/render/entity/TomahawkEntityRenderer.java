package net.minegate.fr.moreblocks.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minegate.fr.moreblocks.client.render.entity.model.TomahawkEntityModel;
import net.minegate.fr.moreblocks.entity.projectile.TomahawkEntity;

@Environment(EnvType.CLIENT)
public class TomahawkEntityRenderer extends EntityRenderer<TomahawkEntity>
{
    public static final EntityModelLayer TOMAHAWK_LAYER_MODEL = new EntityModelLayer(TomahawkEntityModel.TEXTURE, "main");

    private final TomahawkEntityModel model;

    public TomahawkEntityRenderer(EntityRendererFactory.Context context)
    {
        super(context);
        this.model = new TomahawkEntityModel(context.getPart(TOMAHAWK_LAYER_MODEL));
    }

    public void render(TomahawkEntity tomahawkEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i)
    {
        matrixStack.push();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, tomahawkEntity.prevYaw, tomahawkEntity.getYaw()) - 90.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, tomahawkEntity.prevPitch, tomahawkEntity.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(this.getTexture(tomahawkEntity)), false, tomahawkEntity.isEnchanted());
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(tomahawkEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(TomahawkEntity tomahawkEntity)
    {
        return TomahawkEntityModel.TEXTURE;
    }
}