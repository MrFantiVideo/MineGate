package net.minegate.fr.moreblocks.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minegate.fr.moreblocks.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowBlock.class)
public class SnowBlockMixin extends Block
{
    private static final IntProperty            LAYERS;
    private static final VoxelShape[]           SLAB_LAYERS_TO_SHAPE;
    private static final VoxelShape[]           LAYERS_TO_SHAPE;
    private static final BooleanProperty        SNOWY;
    private static final EnumProperty<SlabType> TYPE;

    public SnowBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    protected void SnowBlock(Settings settings, CallbackInfo ci)
    {
        this.setDefaultState(this.stateManager.getDefaultState().with(SNOWY, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (block.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.MYCELIUM_SLAB))
        {
            if (state == state.with(SNOWY, true))
            {
                if (blockState == blockState.with(TYPE, SlabType.BOTTOM))
                {
                    return SLAB_LAYERS_TO_SHAPE[state.get(LAYERS)];
                }
            }
        }
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (block.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.MYCELIUM_SLAB))
        {
            if (state == state.with(SNOWY, true))
            {
                if (blockState == blockState.with(TYPE, SlabType.BOTTOM))
                {
                    return SLAB_LAYERS_TO_SHAPE[state.get(LAYERS) - 1];
                }
            }
        }
        return LAYERS_TO_SHAPE[state.get(LAYERS) - 1];
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (block.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.MYCELIUM_SLAB))
        {
            if (state == state.with(SNOWY, true))
            {
                if (blockState == blockState.with(TYPE, SlabType.BOTTOM))
                {
                    return SLAB_LAYERS_TO_SHAPE[state.get(LAYERS)];
                }
            }
        }
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (block.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.MYCELIUM_SLAB))
        {
            if (state == state.with(SNOWY, true))
            {
                if (blockState == blockState.with(TYPE, SlabType.BOTTOM))
                {
                    return SLAB_LAYERS_TO_SHAPE[state.get(LAYERS)];
                }
            }
        }
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Inject(at = @At("RETURN"), method = "canPlaceAt", cancellable = true)
    public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        BlockState blockState = world.getBlockState(pos.down());
        if (!blockState.isOf(net.minecraft.block.Blocks.ICE) && !blockState.isOf(net.minecraft.block.Blocks.PACKED_ICE) && !blockState.isOf(net.minecraft.block.Blocks.BARRIER))
        {
            if (!blockState.isOf(net.minecraft.block.Blocks.HONEY_BLOCK) && !blockState.isOf(net.minecraft.block.Blocks.SOUL_SAND))
            {
                cir.setReturnValue(Block.isFaceFullSquare(blockState.getCollisionShape(world, pos.down()), Direction.UP) || blockState.getBlock() == this && (Integer) blockState.get(LAYERS) == 8);
            }
            else
            {
                cir.setReturnValue(true);
            }
        }
        if (blockState.isOf(Blocks.GRASS_BLOCK_SLAB) || blockState.isOf(net.minecraft.block.Blocks.GRASS_BLOCK) ||
                blockState.isOf(Blocks.PODZOL_SLAB) || blockState.isOf(net.minecraft.block.Blocks.PODZOL) ||
                blockState.isOf(Blocks.MYCELIUM_SLAB) || blockState.isOf(net.minecraft.block.Blocks.MYCELIUM))
        {
            cir.setReturnValue(true);
        }
        else
        {
            cir.setReturnValue(false);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
    {
        Block downBlock = world.getBlockState(pos.down()).getBlock();
        BlockState downState = world.getBlockState(pos.down());

        if (downBlock.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) ||
                downBlock.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) ||
                downBlock.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) ||
                downBlock.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB) ||
                downBlock.equals(net.minegate.fr.moreblocks.block.Blocks.MYCELIUM_SLAB))
        {
            if (downState == downState.with(Properties.SLAB_TYPE, SlabType.BOTTOM))
            {
                world.setBlockState(pos, state.with(SNOWY, true));
            }
            else
            {
                world.setBlockState(pos, state.with(SNOWY, false));
            }
        }
        if (downBlock.equals(net.minecraft.block.Blocks.GRASS_BLOCK))
        {
            if (downState == downState.with(SNOWY, true))
            {
                world.setBlockState(pos, state.with(SNOWY, false));
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "appendProperties", cancellable = true)
    public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci)
    {
        builder.add(SNOWY);
    }

    static
    {
        LAYERS = Properties.LAYERS;
        SLAB_LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, -6.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, -4.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, -2.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 0.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 2.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 6.0D, 16.0D),
                Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D)};
        LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
        TYPE = Properties.SLAB_TYPE;
        SNOWY = Properties.SNOWY;
    }
}
