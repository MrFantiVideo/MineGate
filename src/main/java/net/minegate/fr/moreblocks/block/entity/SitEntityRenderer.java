package net.minegate.fr.moreblocks.block.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class SitEntityRenderer extends EntityRenderer<SitEntity>
{
    public SitEntityRenderer(EntityRendererFactory.Context context)
    {
        super(context);
    }

    @Override
    public boolean shouldRender(SitEntity entity, Frustum frustum, double d, double e, double f)
    {
        return false;
    }

    @Override
    public Identifier getTexture(SitEntity entity)
    {
        return null;
    }
}