package fr.minegate.entity;

import fr.minegate.MineGate;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityType
{
    public static final net.minecraft.entity.EntityType<SitEntity> SIT;

    /**
     * Initialization.
     */

    public static void init()
    {
        MineGate.log("Loading of the different types of entity.");
    }

    /**
     * Registration of MineGate entity type.
     *
     * @param id      String id of the block entity type.
     * @param builder Builder from the list of block entity type.
     */

    private static <T extends Entity> net.minecraft.entity.EntityType<T> create(String id, FabricEntityTypeBuilder<T> builder)
    {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(MineGate.name.toLowerCase(), id), builder.build());
    }

    static
    {
        SIT = create("sit", FabricEntityTypeBuilder.create(SpawnGroup.MISC, SitEntity::new).dimensions(EntityDimensions.fixed(0.001F, 0.001F)));
    }
}
