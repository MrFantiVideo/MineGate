package net.minegate.fr.moreblocks.state;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public final class BlockStateHelper
{
    public static final BooleanProperty UP;
    public static final BooleanProperty DOWN;
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;

    /**
     * Get voxels from cache to avoid long world loads.
     **/

    public static int getVoxelShapeIndex(BlockState state)
    {
        int index = 0;
        if (state.get(NORTH))
            index |= getDirectionMask(Direction.NORTH);
        if (state.get(SOUTH))
            index |= getDirectionMask(Direction.SOUTH);
        if (state.get(EAST))
            index |= getDirectionMask(Direction.EAST);
        if (state.get(WEST))
            index |= getDirectionMask(Direction.WEST);
        if (state.get(DOWN))
            index |= getDirectionMask(Direction.DOWN);
        if (state.get(UP))
            index |= getDirectionMask(Direction.UP);
        return index;
    }

    /**
     * Retrieves direction with up and down support.
     **/

    private static int getDirectionMask(Direction direction)
    {
        if (direction == Direction.UP)
            return 1 << 10;
        else if (direction == Direction.DOWN)
            return 1 << 22;
        return 1 << direction.getHorizontal();
    }

    static
    {
        UP = net.minecraft.state.property.Properties.UP;
        DOWN = net.minecraft.state.property.Properties.DOWN;
        EAST = net.minecraft.state.property.Properties.EAST;
        NORTH = net.minecraft.state.property.Properties.NORTH;
        SOUTH = net.minecraft.state.property.Properties.SOUTH;
        WEST = Properties.WEST;
    }
}