package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class IronFenceGateBlock extends FenceGateBlock
{
    /**
     * Creation of an iron fence gate block.
     *
     * @param settings Block settings.
     **/

    public IronFenceGateBlock(Settings settings)
    {
        super(settings);
    }

    /**
     * Allows to put the sound of metal as well as the activation.
     **/

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if (this.material == Material.METAL)
        {
            return ActionResult.PASS;
        }
        if (state.get(OPEN))
        {
            state = state.with(OPEN, false);
        }
        else
        {
            Direction direction = player.getHorizontalFacing();
            if (state.get(FACING) == direction.getOpposite())
            {
                state = state.with(FACING, direction);
            }

            state = state.with(OPEN, true);
        }
        world.setBlockState(pos, state, 10);
        boolean direction = state.get(OPEN);
        world.syncWorldEvent(player, direction ? 1011 : 1012, pos, 0);
        world.emitGameEvent(player, direction ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return ActionResult.success(world.isClient);
    }

    /**
     * Allows you to put the sound of metal when updating the block.
     **/

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify)
    {
        if (!world.isClient)
        {
            boolean bl = world.isReceivingRedstonePower(pos);
            if (state.get(POWERED) != bl)
            {
                world.setBlockState(pos, state.with(POWERED, bl).with(OPEN, bl), 2);
                if (state.get(OPEN) != bl)
                {
                    world.syncWorldEvent(null, bl ? 1005 : 1006, pos, 0);
                    world.emitGameEvent(null, bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                }
            }
        }
    }

    /**
     * Allows entities to walk through it.
     **/

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type)
    {
        return false;
    }
}