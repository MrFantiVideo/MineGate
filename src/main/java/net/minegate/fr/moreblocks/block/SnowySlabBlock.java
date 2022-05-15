package net.minegate.fr.moreblocks.block;

import net.minecraft.block.*;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class SnowySlabBlock extends SlabBlock
{
    public static final    BooleanProperty SNOWY;
    protected static final VoxelShape      SNOWY_SHAPE;

    public SnowySlabBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(SNOWY, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        ItemStack itemStack = player.getStackInHand(hand);

        if (!state.get(SNOWY) && state.equals(state.with(TYPE, SlabType.BOTTOM)))
        {
            if (itemStack.getItem().equals(Items.SNOW))
            {
                if (!player.getAbilities().creativeMode)
                {
                    itemStack.decrement(1);
                }
                world.playSound(null, pos, SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                world.setBlockState(pos, state.with(SNOWY, Boolean.TRUE));
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        SlabType slabType = state.get(TYPE);
        switch (slabType)
        {
            case DOUBLE:
                return VoxelShapes.fullCube();
            case TOP:
                return TOP_SHAPE;
            default:
                if (state.get(SNOWY))
                {
                    return SNOWY_SHAPE;
                }
                return BOTTOM_SHAPE;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        SlabType slabType = state.get(TYPE);
        return switch (slabType)
                {
                    case DOUBLE -> VoxelShapes.fullCube();
                    case TOP -> TOP_SHAPE;
                    default -> BOTTOM_SHAPE;
                };
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom)
    {
        return direction != Direction.UP ? super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom) : state.with(SNOWY, newState.isOf(Blocks.SNOW_BLOCK) || newState.isOf(Blocks.SNOW));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        Block topBlock = ctx.getWorld().getBlockState(ctx.getBlockPos().up()).getBlock();

        return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? pushEntitiesUpBeforeBlockChange(this.getDefaultState(), this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos()) : super.getPlacementState(ctx).with(SNOWY, topBlock == Blocks.SNOW_BLOCK || topBlock == Blocks.SNOW);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE, SNOWY, WATERLOGGED);
    }

    static
    {
        SNOWY = Properties.SNOWY;
        SNOWY_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    }
}