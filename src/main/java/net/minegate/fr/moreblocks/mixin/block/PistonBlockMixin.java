package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin
{
    /**
     * Allows obsidian blocks not to move with PistonBlocks.
     **/

    @Inject(at = @At("HEAD"), method = "isMovable", cancellable = true)
    private static void isMovable(BlockState state, World world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir)
    {
        if (pos.getY() >= world.getBottomY() || pos.getY() <= world.getTopY() - 1 || world.getWorldBorder().contains(pos))
        {
            if (state.isOf(Blocks.OBSIDIAN_STAIRS)
                    || state.isOf(Blocks.OBSIDIAN_STAIRS_VERTICAL)
                    || state.isOf(Blocks.OBSIDIAN_SLAB)
                    || state.isOf(Blocks.OBSIDIAN_SLAB_VERTICAL)
                    || state.isOf(Blocks.OBSIDIAN_QUARTER)
                    || state.isOf(Blocks.OBSIDIAN_QUARTER_VERTICAL)
                    || state.isOf(Blocks.OBSIDIAN_EIGHTH)
                    || state.isOf(Blocks.CRYING_OBSIDIAN_STAIRS)
                    || state.isOf(Blocks.CRYING_OBSIDIAN_STAIRS_VERTICAL)
                    || state.isOf(Blocks.CRYING_OBSIDIAN_SLAB)
                    || state.isOf(Blocks.CRYING_OBSIDIAN_SLAB_VERTICAL)
                    || state.isOf(Blocks.CRYING_OBSIDIAN_QUARTER)
                    || state.isOf(Blocks.CRYING_OBSIDIAN_QUARTER_VERTICAL)
                    || state.isOf(Blocks.CRYING_OBSIDIAN_EIGHTH)
                    || state.isOf(Blocks.OBSIDIAN_BRICKS)
                    || state.isOf(Blocks.OBSIDIAN_BRICKS_STAIRS)
                    || state.isOf(Blocks.OBSIDIAN_BRICKS_STAIRS_VERTICAL)
                    || state.isOf(Blocks.OBSIDIAN_BRICKS_SLAB)
                    || state.isOf(Blocks.OBSIDIAN_BRICKS_SLAB_VERTICAL)
                    || state.isOf(Blocks.OBSIDIAN_BRICKS_QUARTER)
                    || state.isOf(Blocks.OBSIDIAN_BRICKS_QUARTER_VERTICAL)
                    || state.isOf(Blocks.OBSIDIAN_BRICKS_EIGHTH)
                    || state.isOf(Blocks.PACKED_OBSIDIAN)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_STAIRS)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_STAIRS_VERTICAL)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_SLAB)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_SLAB_VERTICAL)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_QUARTER)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_QUARTER_VERTICAL)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_EIGHTH)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_BRICKS)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_STAIRS)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_STAIRS_VERTICAL)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_SLAB)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_SLAB_VERTICAL)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_QUARTER)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_QUARTER_VERTICAL)
                    || state.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_EIGHTH))
            {
                cir.setReturnValue(false);
            }
        }
    }
}