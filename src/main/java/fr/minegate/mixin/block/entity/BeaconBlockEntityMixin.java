package fr.minegate.mixin.block.entity;

import fr.minegate.block.PaintedBlock;
import fr.minegate.block.entity.PaintedBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BeaconBlockEntity.class)
@Environment(EnvType.CLIENT)
public class BeaconBlockEntityMixin
{
    /**
     * Float array that temporarily stores the color of a PaintedBlock.
     */

    @Unique
    private static float[] color;

    /**
     * Applies the color of a PaintedBlock to the beacon beam.
     *
     * @param instance The color already found in Stainable blocks.
     *
     * @return The float array containing the color to be applied to the beacon beam.
     */

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DyeColor;getColorComponents()[F"))
    private static float[] setBlockEntityColorAtPos(DyeColor instance)
    {
        if ((color != null) && (instance.getColorComponents() != color))
        {
            float[] cacheColor = color;
            color = null;
            return cacheColor;
        }
        return instance.getColorComponents();
    }

    /**
     * Retrieves the color of a PaintedBlock within the beacon's beam radius.
     *
     * @param instance The world in which the block is located.
     * @param pos      The position of the block within the beam.
     *
     * @return The BlockState of the block intersecting with the beacon's beam.
     */

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
    private static BlockState getBlockEntityColorAtPos(World instance, BlockPos pos)
    {
        if (instance.getBlockState(pos).getBlock() instanceof PaintedBlock)
        {
            color = PaintedBlockEntity.getColorsComponents(PaintedBlockEntity.getColorBlockAt(instance, pos, 0));
        }
        return instance.getBlockState(pos);
    }

    /**
     * Allows a PaintedBlock to pass through the beacon's beam.
     *
     * @param instance The BlockState of the block passing through the beacon's beam.
     *
     * @return A Stainable block to bypass the verification.
     */

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
    private static Block unlockStainable(BlockState instance)
    {
        if (instance.getBlock() instanceof PaintedBlock)
        {
            return Blocks.WHITE_STAINED_GLASS;
        }
        return instance.getBlock();
    }
}
