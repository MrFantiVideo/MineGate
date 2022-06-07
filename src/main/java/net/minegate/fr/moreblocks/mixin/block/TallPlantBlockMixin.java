package net.minegate.fr.moreblocks.mixin.block;

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
import net.minegate.fr.moreblocks.block.PlantableSlabBlock;
import net.minegate.fr.moreblocks.block.SnowySlabBlock;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TallPlantBlock.class)
public class TallPlantBlockMixin extends PlantBlock
{
    private static final EnumProperty<SlabType> TYPE;
    private static final VoxelShape             FLOWER_SHAPE;

    protected TallPlantBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        Block blockDown = world.getBlockState(pos.down()).getBlock();
        Block blockDownDown = world.getBlockState(pos.down().down()).getBlock();
        BlockState blockStateDown = world.getBlockState(pos.down());
        BlockState blockStateDownDown = world.getBlockState(pos.down().down());

        if (blockDown instanceof PlantableSlabBlock || blockDown instanceof SnowySlabBlock)
        {
            if (blockStateDown.equals(blockStateDown.with(TYPE, SlabType.BOTTOM)))
            {
                return FLOWER_SHAPE;
            }
        }
        if (blockDown instanceof TallPlantBlock)
        {
            if (blockDownDown instanceof PlantableSlabBlock || blockDownDown instanceof SnowySlabBlock)
            {
                if (blockStateDownDown.equals(blockStateDownDown.with(TYPE, SlabType.BOTTOM)))
                {
                    return FLOWER_SHAPE;
                }
            }
        }
        return VoxelShapes.fullCube();
    }

    static
    {
        TYPE = Properties.SLAB_TYPE;
        FLOWER_SHAPE = Block.createCuboidShape(0.0D, -8.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }
}