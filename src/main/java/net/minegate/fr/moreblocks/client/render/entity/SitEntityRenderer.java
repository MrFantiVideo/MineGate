package net.minegate.fr.moreblocks.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minegate.fr.moreblocks.entity.SitEntity;

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