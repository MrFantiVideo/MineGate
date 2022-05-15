package net.minegate.fr.moreblocks.block.glass;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class GlassSlabBlock extends SlabBlock
{
    /**
     * Creation of a glass slab  block.
     *
     * @param settings Block settings.
     **/

    public GlassSlabBlock(Settings settings)
    {
        super(settings);
    }

    /**
     * Set the block is translucent.
     **/

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos)
    {
        return !state.getBlock().equals(net.minegate.fr.moreblocks.block.Blocks.TINTED_GLASS_SLAB);
    }

    /**
     * Set the ambient occlusion light level.
     **/

    @Override
    @Environment(EnvType.CLIENT)
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos)
    {
        return 1.0F;
    }

    /**
     * Set the side invisible.
     **/

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState newState, Direction direction)
    {
        if (newState.getBlock() == Blocks.GLASS)
            return true;

        if (newState.getBlock() == this)
            if (isInvisibleToGlassSlab(state, newState, direction))
                return true;

        if (newState.getBlock() == net.minegate.fr.moreblocks.block.Blocks.GLASS_STAIRS)
            if (isInvisibleToGlassStairs(state, newState, direction))
                return true;

        return super.isSideInvisible(state, newState, direction);
    }

    private boolean isInvisibleToGlassSlab(BlockState state, BlockState newState, Direction direction)
    {
        SlabType type1 = state.get(SlabBlock.TYPE);
        SlabType type2 = newState.get(SlabBlock.TYPE);

        if (type2 == SlabType.DOUBLE)
            return true;

        switch (direction)
        {
            case UP:
            case DOWN:
                if (type1 != type2)
                    return true;
                break;

            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                if (type1 == type2)
                    return true;
                break;
        }

        return false;
    }

    private boolean isInvisibleToGlassStairs(BlockState state, BlockState newState, Direction direction)
    {
        SlabType type1 = state.get(SlabBlock.TYPE);
        BlockHalf half2 = newState.get(StairsBlock.HALF);
        Direction facing2 = newState.get(StairsBlock.FACING);

        if (direction == Direction.UP)
            if (half2 == BlockHalf.BOTTOM)
                return true;

        if (direction == Direction.DOWN)
            if (half2 == BlockHalf.TOP)
                return true;

        if (facing2 == direction.getOpposite())
            return true;

        if (direction.getHorizontal() != -1)
            if (type1 == SlabType.BOTTOM && half2 == BlockHalf.BOTTOM)
                return true;
            else return type1 == SlabType.TOP && half2 == BlockHalf.TOP;

        return false;
    }
}