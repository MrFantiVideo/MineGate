package net.minegate.fr.moreblocks.mixin.block;

import net.minegate.fr.moreblocks.block.glass.StainedGlassSlabBlock;
import net.minegate.fr.moreblocks.block.glass.StainedGlassStairsBlock;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.Direction;

@Mixin(StainedGlassBlock.class)
public class StainedGlassBlocksMixin extends AbstractGlassBlock
{
    public StainedGlassBlocksMixin(Settings FabricBlockSettings)
    {
        super(FabricBlockSettings);
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState newState, Direction direction)
    {
        if (newState.getBlock() instanceof StainedGlassSlabBlock)
        {
            if (isInvisibleToGlassSlab(newState, direction))
            {
                return true;
            }
        }
        if (newState.getBlock() instanceof StainedGlassStairsBlock)
        {
            if (isInvisibleToGlassStairs(newState, direction))
            {
                return true;
            }
        }
        return super.isSideInvisible(state, newState, direction);
    }

    private boolean isInvisibleToGlassSlab(BlockState state, Direction direction)
    {
        SlabType type2 = state.get(SlabBlock.TYPE);

        if (type2 == SlabType.DOUBLE)
        {
            return true;
        }
        if (direction == Direction.UP)
        {
            return type2 != SlabType.TOP;
        }
        if (direction == Direction.DOWN)
        {
            return type2 != SlabType.BOTTOM;
        }
        return false;
    }

    private boolean isInvisibleToGlassStairs(BlockState state, Direction direction)
    {
        BlockHalf half2 = state.get(StairsBlock.HALF);
        Direction facing2 = state.get(StairsBlock.FACING);

        if (direction == Direction.UP)
        {
            return half2 != BlockHalf.BOTTOM;
        }
        if (direction == Direction.DOWN)
        {
            return half2 != BlockHalf.TOP;
        }
        if (facing2 == direction.getOpposite())
        {
            return true;
        }
        return false;
    }
}