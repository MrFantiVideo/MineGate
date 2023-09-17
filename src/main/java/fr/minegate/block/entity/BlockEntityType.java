package fr.minegate.block.entity;

import fr.minegate.MineGate;
import fr.minegate.block.Blocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityType
{
    public static final net.minecraft.block.entity.BlockEntityType<PaintedBlockEntity> PAINTED;

    /**
     * Initialization.
     */

    public static void init()
    {
        MineGate.log("Loading of the different types of block entity.");
    }

    /**
     * Registration of MineGate block entity type.
     *
     * @param id      String id of the block entity type.
     * @param builder Builder from the list of block entity type.
     */

    private static <T extends BlockEntity> net.minecraft.block.entity.BlockEntityType<T> create(String id, FabricBlockEntityTypeBuilder<T> builder)
    {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MineGate.name.toLowerCase(), id), builder.build());
    }

    static
    {
        PAINTED = create("painted", FabricBlockEntityTypeBuilder.create(PaintedBlockEntity::new, Blocks.PAINTED_BLOCKS));
    }
}
