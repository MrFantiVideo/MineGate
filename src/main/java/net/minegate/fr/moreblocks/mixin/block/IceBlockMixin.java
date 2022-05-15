package net.minegate.fr.moreblocks.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.Direction;
import net.minegate.fr.moreblocks.block.Blocks;

@Mixin(IceBlock.class)
public class IceBlockMixin extends TransparentBlock
{
    public IceBlockMixin(Settings FabricBlockSettings)
    {
        super(FabricBlockSettings);
    }

    @Override
    public boolean isSideInvisible(BlockState blockState_1, BlockState blockState, Direction direction)
    {
        if (blockState.getBlock() == Blocks.ICE_STAIRS)
        {
            if (isInvisibleToGlassStairs(blockState, direction))
            {
                return true;
            }
        }
        if (blockState.getBlock() == Blocks.ICE_SLAB)
        {
            if (isInvisibleToGlassSlab(blockState, direction))
            {
                return true;
            }
        }
        return super.isSideInvisible(blockState_1, blockState, direction);
    }

    private boolean isInvisibleToGlassSlab(BlockState blockState, Direction direction)
    {
        SlabType type2 = blockState.get(SlabBlock.TYPE);

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

    private boolean isInvisibleToGlassStairs(BlockState blockState, Direction direction)
    {
        BlockHalf half2 = blockState.get(StairsBlock.HALF);
        Direction facing2 = blockState.get(StairsBlock.FACING);

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