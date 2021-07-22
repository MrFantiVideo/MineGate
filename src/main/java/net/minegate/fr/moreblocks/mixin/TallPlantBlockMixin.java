package net.minegate.fr.moreblocks.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.block.enums.FernType;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TallPlantBlock.class)
public class TallPlantBlockMixin extends PlantBlock
{
    private static final EnumProperty<SlabType>        TYPE;
    private static final EnumProperty<FernType>        FERN_TYPE;
    private static final VoxelShape                    FLOWER_SHAPE = Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    private static final EnumProperty<DoubleBlockHalf> HALF;

    protected TallPlantBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        if (DefaultConfig.get("useMixins"))
        {
            Block block = world.getBlockState(pos.down()).getBlock();
            BlockState blockState = world.getBlockState(pos.down());

            if (block.equals(Blocks.GRASS_BLOCK_SLAB) || block.equals(Blocks.DIRT_SLAB) || block.equals(Blocks.COARSE_DIRT_SLAB) || block.equals(Blocks.PODZOL_SLAB))
            {
                if (blockState.equals(Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(Blocks.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(Blocks.COARSE_DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(Blocks.PODZOL_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)))
                {
                    return FLOWER_SHAPE;
                }
            }
            if (block.equals(net.minecraft.block.Blocks.SUNFLOWER) || block.equals(net.minecraft.block.Blocks.LILAC) || block.equals(net.minecraft.block.Blocks.ROSE_BUSH) || block.equals(net.minecraft.block.Blocks.PEONY) || block.equals(net.minecraft.block.Blocks.TALL_GRASS) || block.equals(net.minecraft.block.Blocks.LARGE_FERN))
            {
                if (blockState.equals(net.minecraft.block.Blocks.SUNFLOWER.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(FERN_TYPE, FernType.PLANT)) ||
                        blockState.equals(net.minecraft.block.Blocks.LILAC.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(FERN_TYPE, FernType.PLANT)) ||
                        blockState.equals(net.minecraft.block.Blocks.ROSE_BUSH.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(FERN_TYPE, FernType.PLANT)) ||
                        blockState.equals(net.minecraft.block.Blocks.PEONY.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(FERN_TYPE, FernType.PLANT)) ||
                        blockState.equals(net.minecraft.block.Blocks.TALL_GRASS.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(FERN_TYPE, FernType.PLANT)) ||
                        blockState.equals(net.minecraft.block.Blocks.LARGE_FERN.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(FERN_TYPE, FernType.PLANT)))
                {
                    return FLOWER_SHAPE;
                }
            }
        }
        return VoxelShapes.fullCube();
    }

    @Inject(at = @At("RETURN"), method = "onPlaced", cancellable = true)
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci)
    {
        if (DefaultConfig.get("useMixins"))
        {
            Block block = world.getBlockState(pos.down()).getBlock();
            BlockState blockState = world.getBlockState(pos.down());

            if (block.equals(Blocks.GRASS_BLOCK_SLAB) || block.equals(Blocks.DIRT_SLAB) || block.equals(Blocks.COARSE_DIRT_SLAB) || block.equals(Blocks.PODZOL_SLAB))
            {
                if (blockState.equals(Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(Blocks.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(Blocks.COARSE_DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                        blockState.equals(Blocks.PODZOL_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)))
                {
                    world.setBlockState(pos, state.with(FERN_TYPE, FernType.PLANT));
                    world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(FERN_TYPE, FernType.PLANT), 3);
                }
                else
                {
                    world.setBlockState(pos, state.with(FERN_TYPE, FernType.DEFAULT));
                    world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(FERN_TYPE, FernType.DEFAULT), 3);
                }
            }
            else
            {
                world.setBlockState(pos, state.with(FERN_TYPE, FernType.DEFAULT));
                world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(FERN_TYPE, FernType.DEFAULT), 3);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "appendProperties", cancellable = true)
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci)
    {
        if (DefaultConfig.get("useMixins"))
        {
            builder.add(FERN_TYPE);
        }
    }

    static
    {
        FERN_TYPE = net.minegate.fr.moreblocks.state.Properties.FERN_TYPE;
        TYPE = Properties.SLAB_TYPE;
        HALF = Properties.DOUBLE_BLOCK_HALF;
    }
}
