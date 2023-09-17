package fr.minegate.block.painted;

import fr.minegate.block.PaintedBlock;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PaintedIronPressurePlateBlock extends AbstractPaintedPressurePlateBlock {
    public static final IntProperty POWER;
    private final       int         weight;

    public PaintedIronPressurePlateBlock(int weight, AbstractBlock.Settings settings, BlockSetType blockSetType) {
        super(settings, blockSetType);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(POWER, 0));
        this.weight = weight;
    }

    protected int getRedstoneOutput(World world, BlockPos pos) {
        int i = Math.min(getEntityCount(world, BOX.offset(pos), Entity.class), this.weight);
        if (i > 0) {
            float f = (float)Math.min(this.weight, i) / (float)this.weight;
            return MathHelper.ceil(f * 15.0F);
        } else {
            return 0;
        }
    }

    protected int getRedstoneOutput(BlockState state) {
        return (Integer)state.get(POWER);
    }

    protected BlockState setRedstoneOutput(BlockState state, int rsOut) {
        return (BlockState)state.with(POWER, rsOut);
    }

    protected int getTickRate() {
        return 10;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{POWER});
    }

    static {
        POWER = Properties.POWER;
    }
}
