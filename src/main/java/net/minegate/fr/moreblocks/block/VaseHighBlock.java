package net.minegate.fr.moreblocks.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class VaseHighBlock extends VaseBlock
{
    /**
     * Creation of a vase high block.
     *
     * @param settings Block settings.
     **/

    public VaseHighBlock(Settings settings)
    {
        super(settings);
    }

    /**
     * Custom outlines of the block.
     **/

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return VASE_HIGH_SHAPE;
    }
}