package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minegate.fr.moreblocks.block.PlantableSlabBlock;
import net.minegate.fr.moreblocks.block.SnowySlabBlock;
import net.minegate.fr.moreblocks.block.enums.FernType;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import static net.minecraft.block.SlabBlock.TYPE;

@Mixin(PlantBlock.class)
public class PlantBlockMixin extends Block
{
    private static final EnumProperty<FernType> FERN_TYPE;

    public PlantBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
    public void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (DefaultConfig.get("useMixins"))
        {
            cir.setReturnValue(floor.getBlock() instanceof PlantableSlabBlock || floor.getBlock() instanceof SnowySlabBlock || floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND));
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
    {
        BlockState downBlockState = world.getBlockState(pos.down());

        if (downBlockState.getBlock() instanceof PlantableSlabBlock || downBlockState.getBlock() instanceof SnowySlabBlock)
        {
            if (downBlockState.equals(downBlockState.with(TYPE, SlabType.BOTTOM)))
            {
                world.setBlockState(pos, state.with(FERN_TYPE, FernType.PLANT), 3);
            }
            else
            {
                world.setBlockState(pos, state.with(FERN_TYPE, FernType.DEFAULT), 3);
            }
        }
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        if (DefaultConfig.get("useMixins"))
        {
            Block block = world.getBlockState(pos.down()).getBlock();
            BlockState blockState = world.getBlockState(pos.down());

            if (block instanceof PlantableSlabBlock || block instanceof SnowySlabBlock)
            {
                if (blockState.equals(blockState.with(TYPE, SlabType.BOTTOM)))
                {
                    return getDefaultState().with(FERN_TYPE, FernType.PLANT);
                }
                else
                {
                    return getDefaultState().with(FERN_TYPE, FernType.DEFAULT);
                }
            }
            else
            {
                return getDefaultState().with(FERN_TYPE, FernType.DEFAULT);
            }
        }
        return getDefaultState();
    }

    /**
     * Definition of block properties.
     **/

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        if (DefaultConfig.get("useMixins"))
        {
            builder.add(FERN_TYPE);
        }
    }

    static
    {
        FERN_TYPE = net.minegate.fr.moreblocks.state.Properties.FERN_TYPE;
    }
}