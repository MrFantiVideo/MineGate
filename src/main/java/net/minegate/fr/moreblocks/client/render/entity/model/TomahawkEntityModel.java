package net.minegate.fr.moreblocks.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minegate.fr.moreblocks.MoreBlocks;

@Environment(EnvType.CLIENT)
public class TomahawkEntityModel extends Model
{
    public static final Identifier TEXTURE = new Identifier(MoreBlocks.NameClient.toLowerCase(), "textures/entity/tomahawk.png");
    private final       ModelPart  root;


    public static TexturedModelData getTexturedModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild("1", ModelPartBuilder.create().uv(0, 0).cuboid(0.5F, -3F, -0.5F, 2F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("2", ModelPartBuilder.create().uv(6, 0).cuboid(-0.5F, -2F, -0.5F, 4F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("3", ModelPartBuilder.create().uv(16, 0).cuboid(-1.5F, -1F, -0.5F, 5F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("4", ModelPartBuilder.create().uv(0, 3).cuboid(-2.5F, 0F, -0.5F, 7F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("5", ModelPartBuilder.create().uv(16, 3).cuboid(-2.5F, 1F, -0.5F, 7F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("6", ModelPartBuilder.create().uv(0, 6).cuboid(-1.5F, 2F, -0.5F, 7F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("7", ModelPartBuilder.create().uv(16, 6).cuboid(-0.5F, 3F, -0.5F, 6F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("8", ModelPartBuilder.create().uv(0, 9).cuboid(-1.5F, 4F, -0.5F, 3F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("9", ModelPartBuilder.create().uv(14, 9).cuboid(-2.5F, 5F, -0.5F, 3F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("10", ModelPartBuilder.create().uv(22, 9).cuboid(-3.5F, 6F, -0.5F, 3F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("11", ModelPartBuilder.create().uv(0, 12).cuboid(-4.5F, 7F, -0.5F, 3F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("12", ModelPartBuilder.create().uv(8, 12).cuboid(-5.5F, 8F, -0.5F, 3F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("13", ModelPartBuilder.create().uv(16, 12).cuboid(-6.5F, 9F, -0.5F, 3F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("14", ModelPartBuilder.create().uv(24, 12).cuboid(-6.5F, 10F, -0.5F, 2F, 1F, 1F), ModelTransform.NONE);
        modelPartData2.addChild("15", ModelPartBuilder.create().uv(8, 9).cuboid(2.5F, 4F, -0.5F, 2F, 1F, 1F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 32, 32);
    }

    public TomahawkEntityModel(ModelPart root)
    {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha)
    {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}