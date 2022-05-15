package net.minegate.fr.moreblocks.block;

import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minegate.fr.moreblocks.MoreBlocks;
import net.minegate.fr.moreblocks.block.enums.FlowerType;

public class VaseBlock extends Block
{
    public static final    EnumProperty<FlowerType> FLOWER_TYPE;
    protected static final VoxelShape               VASE_SHAPE;
    protected static final VoxelShape               VASE_HIGH_SHAPE;
    protected static final VoxelShape               VASE_SMALL_SHAPE;

    /**
     * Creation of a vase block.
     *
     * @param settings Block settings.
     **/

    public VaseBlock(Settings settings)
    {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(FLOWER_TYPE, FlowerType.EMPTY));
    }

    /**
     * Storage and display of flowers in the block.
     **/

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        ItemStack itemStack = player.getStackInHand(hand);
        String flowerString = itemStack.getItem().toString().toUpperCase();

        if (state.equals(state.with(FLOWER_TYPE, FlowerType.EMPTY)))
        {
            FlowerType flowerType;
            try
            {
                flowerType = FlowerType.valueOf(flowerString);
            } catch (IllegalArgumentException exception)
            {
                return ActionResult.CONSUME;
            }
            if (!player.getAbilities().creativeMode)
            {
                itemStack.decrement(1);
            }
            player.incrementStat(Stats.POT_FLOWER);
            world.setBlockState(pos, state.with(FLOWER_TYPE, flowerType), 3);
            return ActionResult.CONSUME;
        }
        else
        {
            ItemStack flower = new ItemStack(Registry.BLOCK.get(new Identifier(state.get(FLOWER_TYPE).toString().toLowerCase())));
            if (flower.getItem().equals(Items.AIR))
            {
                flower = new ItemStack(Registry.BLOCK.get(new Identifier(MoreBlocks.NameClient.toLowerCase(), state.get(FLOWER_TYPE).toString().toLowerCase())));
            }
            if (itemStack.isEmpty())
            {
                player.setStackInHand(hand, flower);
            }
            else if (!player.giveItemStack(flower))
            {
                player.dropItem(flower, false);
            }
            world.setBlockState(pos, state.with(FLOWER_TYPE, FlowerType.EMPTY), 3);
            return ActionResult.CONSUME;
        }
    }

    /**
     * Drop the flower from the block when you break it.
     **/

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        ItemStack flower = new ItemStack(Registry.BLOCK.get(new Identifier(state.get(FLOWER_TYPE).toString().toLowerCase())));
        if (flower.getItem().equals(Items.AIR))
        {
            flower = new ItemStack(Registry.BLOCK.get(new Identifier(MoreBlocks.NameClient.toLowerCase(), state.get(FLOWER_TYPE).toString().toLowerCase())));
        }
        ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + 0.5D, (double) (pos.getY() + 1), (double) pos.getZ() + 0.5D, flower);
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
        this.spawnBreakParticles(world, player, pos, state);
        world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
    }
    /**
     * Custom outlines of the block.
     **/

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return VASE_SHAPE;
    }

    /**
     * Definition of block properties.
     **/

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(FLOWER_TYPE);
    }

    /**
     * Allows entities to walk through it.
     **/

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type)
    {
        return false;
    }

    static
    {
        FLOWER_TYPE = net.minegate.fr.moreblocks.state.Properties.FLOWER_TYPE;
        VASE_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
        VASE_HIGH_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
        VASE_SMALL_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D);
    }
}