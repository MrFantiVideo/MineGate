package net.minegate.fr.moreblocks.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minegate.fr.moreblocks.MoreBlocks;
import net.minegate.fr.moreblocks.entity.projectile.TomahawkEntity;

public class EntityType
{
    /**
     * Initialization.
     **/

    public static void init()
    {
        MoreBlocks.ConsoleClient("Loading of the different types of entities.");
    }

    /**
     * List of new Entity.
     **/

    public static final net.minecraft.entity.EntityType<TomahawkEntity> TOMAHAWK;
    public static final net.minecraft.entity.EntityType<SitEntity>      SIT;

    /**
     * Registration of MineGate entities.
     *
     * @param ID     String ID of the Block.
     * @param Entity Entity from the list of entities.
     **/

    private static net.minecraft.entity.EntityType register(String ID, FabricEntityTypeBuilder Entity)
    {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(MoreBlocks.NameClient.toLowerCase(), ID), Entity.build());
    }

    static
    {
        TOMAHAWK = register("tomahawk", FabricEntityTypeBuilder.create(SpawnGroup.MISC, (net.minecraft.entity.EntityType.EntityFactory<TomahawkEntity>) TomahawkEntity::new).dimensions(EntityDimensions.fixed(1, 2)));
        SIT = register("sit", FabricEntityTypeBuilder.create(SpawnGroup.MISC, SitEntity::new).dimensions(EntityDimensions.fixed(0.001F, 0.001F)));
    }
}