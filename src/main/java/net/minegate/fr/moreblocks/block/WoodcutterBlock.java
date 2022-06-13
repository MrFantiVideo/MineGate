package net.minegate.fr.moreblocks.block;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.screen.WoodcutterScreenHandler;

public class WoodcutterBlock extends StonecutterBlock
{
    private static final Text TITLE = Text.translatable("container.woodcutter");

    /**
     * Creation of a woodcutter block.
     *
     * @param settings Block settings.
     **/

    public WoodcutterBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    /**
     * Open the woodcutter menu.
     **/

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos)
    {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new WoodcutterScreenHandler(i, playerInventory, ScreenHandlerContext.create(world, pos)), TITLE);
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