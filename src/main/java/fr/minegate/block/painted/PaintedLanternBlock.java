package fr.minegate.block.painted;

import fr.minegate.block.entity.PaintedBlockEntity;
import fr.minegate.util.BlockStateHelper;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class PaintedLanternBlock extends PaintedLanternRodBlock
{
    public static final    BooleanProperty           CANDLE;
    public static final    BooleanProperty           LIT;
    public static final    ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    protected static final VoxelShape                LANTERN_SHAPE;
    protected static final VoxelShape                UP_SHAPE;
    protected static final VoxelShape                DOWN_SHAPE;
    protected static final VoxelShape                NORTH_SHAPE;
    protected static final VoxelShape                EAST_SHAPE;
    protected static final VoxelShape                SOUTH_SHAPE;
    protected static final VoxelShape                WEST_SHAPE;
    protected static final Map<Integer, VoxelShape>  VoxelShapeCache = new HashMap<Integer, VoxelShape>();

    /**
     * Creation of a lantern block.
     *
     * @param settings Block settings.
     **/

    public PaintedLanternBlock(AbstractBlock.Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CANDLE, Boolean.FALSE).with(LIT, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE));
    }

    /**
     * Custom outlines of the block.
     **/

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        int voxelShapeIndex = BlockStateHelper.getVoxelShapeIndex(state);
        if (VoxelShapeCache.containsKey(voxelShapeIndex))
            return VoxelShapeCache.get(voxelShapeIndex);

        VoxelShape voxelShapes = VoxelShapes.empty();

        if (state.get(DOWN))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, DOWN_SHAPE);
        }
        if (state.get(NORTH))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, NORTH_SHAPE);
        }
        if (state.get(EAST))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, EAST_SHAPE);
        }
        if (state.get(SOUTH))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, SOUTH_SHAPE);
        }
        if (state.get(WEST))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, WEST_SHAPE);
        }

        if (state.get(UP) || state.get(NORTH) || state.get(EAST) || state.get(SOUTH) || state.get(WEST))
        {
            voxelShapes = VoxelShapes.union(voxelShapes, UP_SHAPE);
        }

        voxelShapes = VoxelShapes.union(voxelShapes, LANTERN_SHAPE);
        VoxelShapeCache.put(voxelShapeIndex, voxelShapes);

        return VoxelShapes.union(voxelShapes, LANTERN_SHAPE);
    }

    /**
     * Lets put the candle in the lantern.
     **/

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (player.getAbilities().allowModifyWorld && itemStack.isEmpty() && state.get(LIT))
        {
            extinguish(player, state, world, pos);
            return ActionResult.success(world.isClient);
        }
        if (itemStack.isIn(ItemTags.CANDLES))
        {
            Block block = Block.getBlockFromItem(item);
            if (block instanceof CandleBlock)
            {
                if (state.equals(state.with(CANDLE, Boolean.FALSE)))
                {
                    if (!player.isCreative())
                    {
                        itemStack.decrement(1);
                    }

                    world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.setBlockState(pos, state.with(CANDLE, Boolean.TRUE));
                    world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    player.incrementStat(Stats.USED.getOrCreateStat(item));
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }

    /**
     * Light the candle using a flaming projectile.
     **/

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile)
    {
        if (!world.isClient && projectile.isOnFire() && this.isNotLit(state))
        {
            setLit(world, state, hit.getBlockPos(), true);
        }
    }

    /**
     * See if the candle is not lit.
     **/

    protected boolean isNotLit(BlockState state)
    {
        return !(Boolean) state.get(LIT);
    }

    /**
     * Set whether the candle is lit or not.
     **/

    private static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit)
    {
        if (!state.equals(state.with(CANDLE, Boolean.FALSE)))
        {
            world.setBlockState(pos, state.with(LIT, lit), 11);
        }
    }

    /**
     * Allows the candle to be extinguished by hand.
     **/

    public static void extinguish(@Nullable PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos)
    {
        setLit(world, state, pos, false);
        double d = (double) pos.getX() + 0.5D;
        double e = (double) pos.getY() + 0.6D;
        double f = (double) pos.getZ() + 0.5D;
        if (state.getBlock() instanceof LanternBlock)
        {
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.10000000149011612D, 0.0D);
        }
        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    /**
     * Displays candle particles in the lantern.
     **/

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
    {
        if (state.get(LIT))
        {
            double d = (double) pos.getX() + 0.5D;
            double e = (double) pos.getY() + 0.6D;
            double f = (double) pos.getZ() + 0.5D;
            float r = random.nextFloat();
            if (r < 0.3F)
            {
                world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
                if (r < 0.17F)
                {
                    world.playSound(d, e, f, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
                }
            }
            world.addParticle(ParticleTypes.SMALL_FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Definition of block properties.
     **/

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(CANDLE, UP, DOWN, EAST, NORTH, SOUTH, WEST, LIT, WATERLOGGED);
    }

    static
    {
        LANTERN_SHAPE = Stream.of(
                Block.createCuboidShape(7.0D, 12.0D, 7.0D, 9.0D, 14.0D, 9.0D),
                Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 5.0D, 12.0D),
                Block.createCuboidShape(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D),
                Block.createCuboidShape(4.0D, 11.0D, 4.0D, 12.0D, 12.0D, 12.0D)).reduce((v1, v2) ->
                VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        UP_SHAPE = Block.createCuboidShape(7.0D, 14.0D, 7.0D, 9.0D, 16.0D, 9.0D);
        DOWN_SHAPE = Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 4.0D, 9.0D);
        NORTH_SHAPE = Block.createCuboidShape(7.0D, 14.0D, 0.0D, 9.0D, 16.0D, 7.0D);
        EAST_SHAPE = Block.createCuboidShape(9.0D, 14.0D, 7.0D, 16.0D, 16.0D, 9.0D);
        SOUTH_SHAPE = Block.createCuboidShape(7.0D, 14.0D, 9.0D, 9.0D, 16.0D, 16.0D);
        WEST_SHAPE = Block.createCuboidShape(0.0D, 14.0D, 7.0D, 7.0D, 16.0D, 9.0D);
        CANDLE = fr.minegate.state.property.Properties.CANDLE;
        LIT = Properties.LIT;
        STATE_TO_LUMINANCE = (state) -> state.get(LIT) ? 15 : 0;
    }
}
