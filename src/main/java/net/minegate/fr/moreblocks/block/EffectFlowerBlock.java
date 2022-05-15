package net.minegate.fr.moreblocks.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class EffectFlowerBlock extends FlowerBlock
{
    StatusEffect effect;

    /**
     * Creation of a flower block with effect.
     *
     * @param effect Effect desired.
     * @param settings Block settings.
     **/

    public EffectFlowerBlock(StatusEffect effect, Settings settings)
    {
        super(effect, 8, settings);
        this.effect = effect;
    }

    /**
     * When an entity collides with the flower.
     **/

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
    {
        if (!world.isClient && world.getDifficulty() != Difficulty.PEACEFUL)
        {
            if (entity instanceof LivingEntity livingEntity)
            {
                livingEntity.addStatusEffect(new StatusEffectInstance(effect, 240));
            }
        }
    }
}