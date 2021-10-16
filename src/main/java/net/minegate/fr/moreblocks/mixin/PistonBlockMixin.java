package net.minegate.fr.moreblocks.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin extends FacingBlock
{
    private static final BooleanProperty EXTENDED = Properties.EXTENDED;
    
    protected PistonBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "isMovable")
    private static boolean isMovable(BlockState blockState, World world, BlockPos blockPos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir)
    {
        if (blockPos.getY() >= world.getBottomY() && blockPos.getY() <= world.getHeight() - 1 && world.getWorldBorder().contains(blockPos))
        {
            if (blockState.isAir())
            {
                return true;
            }
            else if (!blockState.isOf(net.minecraft.block.Blocks.OBSIDIAN)
                    && !blockState.isOf(Blocks.OBSIDIAN_STAIRS)
                    && !blockState.isOf(Blocks.OBSIDIAN_STAIRS_VERTICAL)
                    && !blockState.isOf(Blocks.OBSIDIAN_SLAB)
                    && !blockState.isOf(Blocks.OBSIDIAN_SLAB_VERTICAL)
                    && !blockState.isOf(Blocks.OBSIDIAN_QUARTER)
                    && !blockState.isOf(Blocks.OBSIDIAN_QUARTER_VERTICAL)
                    && !blockState.isOf(Blocks.OBSIDIAN_EIGHTH)
                    && !blockState.isOf(net.minecraft.block.Blocks.CRYING_OBSIDIAN)
                    && !blockState.isOf(Blocks.CRYING_OBSIDIAN_STAIRS)
                    && !blockState.isOf(Blocks.CRYING_OBSIDIAN_STAIRS_VERTICAL)
                    && !blockState.isOf(Blocks.CRYING_OBSIDIAN_SLAB)
                    && !blockState.isOf(Blocks.CRYING_OBSIDIAN_SLAB_VERTICAL)
                    && !blockState.isOf(Blocks.CRYING_OBSIDIAN_QUARTER)
                    && !blockState.isOf(Blocks.CRYING_OBSIDIAN_QUARTER_VERTICAL)
                    && !blockState.isOf(Blocks.CRYING_OBSIDIAN_EIGHTH)
                    && !blockState.isOf(Blocks.OBSIDIAN_BRICKS)
                    && !blockState.isOf(Blocks.OBSIDIAN_BRICKS_STAIRS)
                    && !blockState.isOf(Blocks.OBSIDIAN_BRICKS_STAIRS_VERTICAL)
                    && !blockState.isOf(Blocks.OBSIDIAN_BRICKS_SLAB)
                    && !blockState.isOf(Blocks.OBSIDIAN_BRICKS_SLAB_VERTICAL)
                    && !blockState.isOf(Blocks.OBSIDIAN_BRICKS_QUARTER)
                    && !blockState.isOf(Blocks.OBSIDIAN_BRICKS_QUARTER_VERTICAL)
                    && !blockState.isOf(Blocks.OBSIDIAN_BRICKS_EIGHTH)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_STAIRS)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_STAIRS_VERTICAL)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_SLAB)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_SLAB_VERTICAL)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_QUARTER)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_QUARTER_VERTICAL)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_EIGHTH)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_BRICKS)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_STAIRS)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_STAIRS_VERTICAL)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_SLAB)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_SLAB_VERTICAL)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_QUARTER)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_QUARTER_VERTICAL)
                    && !blockState.isOf(Blocks.PACKED_OBSIDIAN_BRICKS_EIGHTH))
            {
                if (direction == Direction.DOWN && blockPos.getY() == 0) {
                    return false;
                } else if (direction == Direction.UP && blockPos.getY() == world.getHeight() - 1) {
                    return false;
                } else {
                    if (!blockState.isOf(net.minecraft.block.Blocks.PISTON) && !blockState.isOf(net.minecraft.block.Blocks.STICKY_PISTON)) {
                        if (blockState.getHardness(world, blockPos) == -1.0F) {
                            return false;
                        }

                        switch(blockState.getPistonBehavior()) {
                            case BLOCK:
                                return false;
                            case DESTROY:
                                return canBreak;
                            case PUSH_ONLY:
                                return direction == pistonDir;
                        }
                    } else if ((Boolean)blockState.get(EXTENDED)) {
                        return false;
                    }

                    return !blockState.hasBlockEntity();
                }
            }
            else
            {
                return false;
            }
        }
        return false;
    }
}