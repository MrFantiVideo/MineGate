package net.minegate.fr.moreblocks.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.tag.ItemTags;
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
import net.minecraft.world.event.GameEvent;
import net.minegate.fr.moreblocks.block.enums.ColorsType;
import net.minegate.fr.moreblocks.item.BookItem;

import java.util.stream.Stream;

public class BookshelfBlock extends LecternBlock implements Waterloggable
{
    public static          Item                     itemScreen;
    public static final    EnumProperty<ColorsType> TYPE;
    public static final    BooleanProperty          WATERLOGGED;
    protected static final VoxelShape               VOXEL_SHAPE;

    /**
     * Creation of an bookshelf block.
     *
     * @param settings Block settings.
     **/

    public BookshelfBlock(Settings settings)
    {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(TYPE, ColorsType.EMPTY).with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Storage and display of books in the block.
     **/

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        ItemStack itemStack = player.getStackInHand(hand);

        if (state.get(HAS_BOOK))
        {
            if (blockEntity instanceof LecternBlockEntity lecternBlockEntity)
            {
                ItemStack book = lecternBlockEntity.getBook().copy();

                if (book.getItem() instanceof BookItem || book.getItem() == net.minecraft.item.Items.BOOK)
                {
                    Direction direction = state.get(FACING);
                    float f = 0.25F * (float) direction.getOffsetX();
                    float g = 0.25F * (float) direction.getOffsetZ();
                    setHasBook(world, pos, state, false);
                    ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + 0.5D + (double) f, (double) (pos.getY() + 1), (double) pos.getZ() + 0.5D + (double) g, book);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                    lecternBlockEntity.clear();
                }
                else
                {
                    if (!world.isClient)
                    {
                        this.openScreen(world, pos, player);
                    }
                }
            }
            return ActionResult.success(world.isClient);
        }
        else
        {
            if (!itemStack.isEmpty() && (itemStack.isIn(ItemTags.LECTERN_BOOKS) || (itemStack.getItem() instanceof BookItem) || (itemStack.getItem() == net.minecraft.item.Items.BOOK)))
            {
                if (blockEntity instanceof LecternBlockEntity lecternBlockEntity)
                {
                    lecternBlockEntity.setBook(itemStack.split(1));
                    setHasBook(world, pos, state, true);
                    world.playSound(null, pos, SoundEvents.ITEM_BOOK_PUT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    return ActionResult.CONSUME;
                }
            }
            return !itemStack.isEmpty() && (!itemStack.isIn(ItemTags.LECTERN_BOOKS) || !(itemStack.getItem() instanceof BookItem) || !(itemStack.getItem() == net.minecraft.item.Items.BOOK)) ? ActionResult.CONSUME : ActionResult.PASS;
        }
    }

    /**
     * Required for onUse.
     **/

    private void openScreen(World world, BlockPos pos, PlayerEntity player)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LecternBlockEntity)
        {
            player.openHandledScreen((LecternBlockEntity) blockEntity);
            player.incrementStat(Stats.INTERACT_WITH_LECTERN);
        }

    }

    /**
     * Custom outlines of the block.
     **/

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        ColorsType slabType_1 = state.get(TYPE);
        if (slabType_1 == ColorsType.EMPTY)
        {
            return VOXEL_SHAPE;
        }
        else
        {
            return VoxelShapes.fullCube();
        }
    }

    /**
     * Custom outlines collision of the block.
     **/

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        ColorsType slabType_1 = state.get(TYPE);
        if (slabType_1 == ColorsType.EMPTY)
        {
            return VOXEL_SHAPE;
        }
        else
        {
            return VoxelShapes.fullCube();
        }
    }

    /**
     * Definition of block properties.
     **/

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE, WATERLOGGED, FACING, POWERED, HAS_BOOK);
    }

    /**
     * Allows you to make the block waterlogged.
     **/

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    /**
     * Try to make the block waterlogged.
     **/

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState)
    {
        return state.get(TYPE) == ColorsType.EMPTY && Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    /**
     * Can make the block waterlogged.
     **/

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid)
    {
        return state.get(TYPE) == ColorsType.EMPTY && Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
    }

    /**
     * System to set the color of the books in the Lectern.
     **/

    public static void setBookScreen(Item item)
    {
        itemScreen = item;
    }

    /**
     * System to get the color of the books in the Lectern.
     **/

    public static Item getBookScreen()
    {
        return itemScreen;
    }

    static
    {
        TYPE = net.minegate.fr.moreblocks.state.Properties.COLORS_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
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
    }
}