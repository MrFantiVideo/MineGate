package fr.minegate.block.painted;

import fr.minegate.block.PaintedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public class PaintedHorizontalFacingBlock extends PaintedBlock
{
    /**
     * Creation of a painted block.
     *
     * @param settings Block settings.
     */
    public PaintedHorizontalFacingBlock(Settings settings)
    {
        super(settings);
    }


    public static final DirectionProperty FACING;

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
    }
}
