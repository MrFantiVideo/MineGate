package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minegate.fr.moreblocks.block.enums.BookshelfType;
import net.minegate.fr.moreblocks.item.Items;

import java.util.stream.Stream;

public class BookshelfBlock extends Block implements Waterloggable
{
    public static final EnumProperty<BookshelfType> BOOKSHELF_TYPE;
    public static final BooleanProperty             WATERLOGGED;
    static final        VoxelShape                  VOXEL_SHAPE;

    public BookshelfBlock(Settings blockSettings)
    {
        super(blockSettings);
        setDefaultState(this.stateManager.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.EMPTY).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(BOOKSHELF_TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        BookshelfType slabType_1 = state.get(BOOKSHELF_TYPE);
        if (slabType_1 == BookshelfType.EMPTY)
        {
            return VOXEL_SHAPE;
        }
        else
        {
            return VoxelShapes.fullCube();
        }
    }


    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState)
    {
        return state.get(BOOKSHELF_TYPE) == BookshelfType.EMPTY && Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid)
    {
        return state.get(BOOKSHELF_TYPE) == BookshelfType.EMPTY && Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom)
    {
        if (state.get(WATERLOGGED))
        {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type)
    {
        switch (type)
        {
            case LAND:
                return false;
            case WATER:
                return world.getFluidState(pos).isIn(FluidTags.WATER);
            case AIR:
                return false;
            default:
                return false;
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        Block block = state.getBlock();
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.EMPTY))
        {
            if (item.equals(net.minecraft.item.Items.BOOK) || item.equals(Items.BLACK_BOOK) || item.equals(Items.BLUE_BOOK) || item.equals(Items.BROWN_BOOK)
                    || item.equals(Items.CYAN_BOOK) || item.equals(Items.GRAY_BOOK) || item.equals(Items.GREEN_BOOK) || item.equals(Items.LIGHT_BLUE_BOOK)
                    || item.equals(Items.LIGHT_GRAY_BOOK) || item.equals(Items.LIME_BOOK) || item.equals(Items.MAGENTA_BOOK) || item.equals(Items.ORANGE_BOOK)
                    || item.equals(Items.PINK_BOOK) || item.equals(Items.PURPLE_BOOK) || item.equals(Items.RED_BOOK) || item.equals(Items.WHITE_BOOK) || item.equals(Items.YELLOW_BOOK))
            {
                if (item == net.minecraft.item.Items.BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.DEFAULT));
                }
                if (item == Items.BLACK_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.BLACK));
                }
                if (item == Items.BLUE_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.BLUE));
                }
                if (item == Items.BROWN_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.BROWN));
                }
                if (item == Items.CYAN_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.CYAN));
                }
                if (item == Items.GRAY_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.GRAY));
                }
                if (item == Items.GREEN_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.GREEN));
                }
                if (item == Items.LIGHT_BLUE_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.LIGHT_BLUE));
                }
                if (item == Items.LIGHT_GRAY_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.LIGHT_GRAY));
                }
                if (item == Items.LIME_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.LIME));
                }
                if (item == Items.MAGENTA_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.MAGENTA));
                }
                if (item == Items.ORANGE_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.ORANGE));
                }
                if (item == Items.PINK_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.PINK));
                }
                if (item == Items.PURPLE_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.PURPLE));
                }
                if (item == Items.RED_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.RED));
                }
                if (item == Items.WHITE_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.WHITE));
                }
                if (item == Items.YELLOW_BOOK)
                {
                    if (!player.abilities.creativeMode)
                    {
                        itemStack.decrement(1);
                    }
                    world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.YELLOW));
                }
                world.playSound(null, pos, SoundEvents.ITEM_BOOK_PUT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.CONSUME;
            }
        }
        else
        {
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.DEFAULT))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(net.minecraft.item.Items.BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.BLACK))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.BLACK_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.BLUE))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.BLUE_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.BROWN))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.BROWN_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.CYAN))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.CYAN_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.GRAY))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.GRAY_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.GREEN))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.GREEN_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.LIGHT_BLUE))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.LIGHT_BLUE_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.LIGHT_GRAY))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.LIGHT_GRAY_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.LIME))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.LIME_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.MAGENTA))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.MAGENTA_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.ORANGE))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.ORANGE_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.PINK))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.PINK_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.PURPLE))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.PURPLE_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.RED))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.RED_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.WHITE))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.WHITE_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (state == block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.YELLOW))
            {
                if (!player.abilities.creativeMode)
                {
                    ItemStack itemBook = new ItemStack(Items.YELLOW_BOOK);
                    if (itemStack.isEmpty())
                    {
                        player.setStackInHand(hand, itemBook);
                    }
                    else if (!player.giveItemStack(itemBook))
                    {
                        player.dropItem(itemBook, false);
                    }
                }
            }
            if (player.abilities.creativeMode)
            {
                world.playSound(null, pos, SoundEvents.ITEM_BOOK_PUT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            world.setBlockState(pos, block.getDefaultState().with(BOOKSHELF_TYPE, BookshelfType.EMPTY));
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    static
    {
        VOXEL_SHAPE = Stream.of(
                Block.createCuboidShape(0, 0, 0, 16, 1, 16),
                Block.createCuboidShape(0, 15, 0, 16, 16, 16),
                Block.createCuboidShape(0, 7, 0, 16, 9, 16),
                Block.createCuboidShape(0, 1, 0, 1, 7, 1),
                Block.createCuboidShape(0, 9, 0, 1, 15, 1),
                Block.createCuboidShape(15, 9, 0, 16, 15, 1),
                Block.createCuboidShape(15, 1, 0, 16, 7, 1),
                Block.createCuboidShape(15, 1, 15, 16, 7, 16),
                Block.createCuboidShape(15, 9, 15, 16, 15, 16),
                Block.createCuboidShape(0, 9, 15, 1, 15, 16),
                Block.createCuboidShape(0, 1, 15, 1, 7, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        WATERLOGGED = Properties.WATERLOGGED;
        BOOKSHELF_TYPE = net.minegate.fr.moreblocks.state.Properties.BOOKSHELF_TYPE;
    }
}