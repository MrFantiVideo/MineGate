package net.minegate.fr.moreblocks.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.block.enums.FernType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin extends PlantBlock
{
    private static final IntProperty            AGE;
    private static final EnumProperty<SlabType> TYPE;
    private static final EnumProperty<FernType> FERN_TYPE;
    private static final VoxelShape             SLAB_CUBE_SHAPE;
    private static final VoxelShape             SLAB_SMALL_SHAPE;
    private static final VoxelShape             SLAB_LARGE_SHAPE;
    private static final VoxelShape             SMALL_SHAPE;
    private static final VoxelShape             LARGE_SHAPE;

    protected SweetBerryBushBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "getOutlineShape", cancellable = true)
    private void getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (state.get(AGE) == 0)
        {
            if (block.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB))
            {
                if (blockState.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)))
                {
                    cir.setReturnValue(SLAB_SMALL_SHAPE);
                }
                else
                {
                    cir.setReturnValue(SMALL_SHAPE);
                }
            }
        }
        else
        {
            if (block.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB))
            {
                if (blockState.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)))
                {
                    if (state.get(AGE) < 3)
                    {
                        cir.setReturnValue(SLAB_LARGE_SHAPE);
                    }
                    else if (state.get(AGE) == 3)
                    {
                        cir.setReturnValue(SLAB_CUBE_SHAPE);
                    }
                }
                else
                {
                    cir.setReturnValue(state.get(AGE) < 3 ? LARGE_SHAPE : super.getOutlineShape(state, world, pos, context));
                }
            }
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (block.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB))
        {
            if (blockState.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                    blockState.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                    blockState.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                    blockState.equals(Blocks.PODZOL_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)))
            {
                world.setBlockState(pos, state.with(FERN_TYPE, FernType.PLANT));
            }
            else
            {
                world.setBlockState(pos, state.with(FERN_TYPE, FernType.DEFAULT));
            }
        }
        else
        {
            world.setBlockState(pos, state.with(FERN_TYPE, FernType.DEFAULT));
        }
    }

    @Inject(at = @At("HEAD"), method = "appendProperties", cancellable = true)
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci)
    {
        builder.add(FERN_TYPE);
    }

    static
    {
        AGE = Properties.AGE_3;
        FERN_TYPE = net.minegate.fr.moreblocks.state.Properties.FERN_TYPE;
        TYPE = Properties.SLAB_TYPE;
        SLAB_SMALL_SHAPE = Block.createCuboidShape(3.0D, -8.0D, 3.0D, 13.0D, 0.0D, 13.0D);
        SLAB_LARGE_SHAPE = Block.createCuboidShape(1.0D, -8.0D, 1.0D, 15.0D, 8.0D, 15.0D);
        SLAB_CUBE_SHAPE = Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        SMALL_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
        LARGE_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    }
}
