package net.minegate.fr.moreblocks.block.entity;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class SitManager
{
    public static final EntityType<SitEntity> SIT_ENTITY_TYPE = Registry.register(Registry.ENTITY_TYPE,
            new Identifier("minegate", "entity_sit"),
            FabricEntityTypeBuilder.<SitEntity>create(SpawnGroup.MISC, SitEntity::new)
                    .dimensions(EntityDimensions.fixed(0.001F, 0.001F)).build());

    private static final List<ISitBlock> _sitBlocks = new ArrayList<>();

    public static void init()
    {
        FabricDefaultAttributeRegistry.register(SIT_ENTITY_TYPE, LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 0));
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
        {
            BlockState blockState = world.getBlockState(hitResult.getBlockPos());
            Block block = blockState.getBlock();
            if ((block instanceof ISitBlock) && !SitEntity.OCCUPIED.containsKey(new Vec3d(hitResult.getBlockPos().getX(), hitResult.getBlockPos().getY(), hitResult.getBlockPos().getZ())) && player.getStackInHand(hand).isEmpty())
            {
                ISitBlock sitBlock = (ISitBlock) block;
                Vec3d sitPos = sitBlock.getSitPosition();
                Vec3d entityPos = new Vec3d(hitResult.getBlockPos().getX() + sitPos.getX(), hitResult.getBlockPos().getY() + sitPos.getY(), hitResult.getBlockPos().getZ() + sitPos.getZ());

                SitEntity sitEntity = SIT_ENTITY_TYPE.create(world);
                _sitBlocks.add(sitBlock);
                SitEntity.OCCUPIED.put(entityPos, player.getBlockPos());
                sitEntity.setPosition(block, entityPos.getX(), entityPos.getY(), entityPos.getZ());
                world.spawnEntity(sitEntity);
                player.startRiding(sitEntity);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }

    public static void unSit(ISitBlock block)
    {
        _sitBlocks.remove(block);
    }
}