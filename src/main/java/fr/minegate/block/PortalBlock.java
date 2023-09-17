package fr.minegate.block;

import fr.minegate.block.entity.PaintedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PortalBlock extends PaintedBlock
{
    protected static final EnumProperty<Direction.Axis> AXIS;
    protected static final VoxelShape                   X_SHAPE;
    protected static final VoxelShape                   Y_SHAPE;
    protected static final VoxelShape                   Z_SHAPE;

    /**
     * Creation of a portal block.
     *
     * @param settings Block settings.
     */

    public PortalBlock(Settings settings)
    {
        super(settings);
    }

    /**
     * Definition of block properties.
     */

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(AXIS);
    }

    /**
     * Custom outlines of the block.
     */

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return switch (state.get(AXIS))
        {
            case X -> X_SHAPE;
            case Y -> Y_SHAPE;
            default -> Z_SHAPE;
        };
    }

    /**
     * Consideration of placement position.
     */

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        Direction.Axis direction = ctx.getSide().getAxis();
        Direction player = ctx.getHorizontalPlayerFacing();

        if (direction == Direction.Axis.X || direction == Direction.Axis.Z)
        {
            return this.getDefaultState().with(AXIS, Direction.Axis.Y);
        }
        if (player != Direction.NORTH && player != Direction.SOUTH)
        {
            return this.getDefaultState().with(AXIS, Direction.Axis.Z);
        }
        return this.getDefaultState().with(AXIS, Direction.Axis.X);
    }

    /**
     * Set the block is translucent.
     */

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction)
    {
        if (stateFrom.isOf(this) && state.isOf(this))
        {
            return stateFrom.get(AXIS) == state.get(AXIS);
        }
        return false;
    }

    /**
     * Displays particles in the color of the block.
     */

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
    {
        if (random.nextInt(100) == 0)
        {
            world.playSound((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_LAVA_INTO_CAULDRON, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i)
        {
            double d = (double) pos.getX() + random.nextDouble();
            double e = (double) pos.getY() + random.nextDouble();
            double f = (double) pos.getZ() + random.nextDouble();
            double g = ((double) random.nextFloat() - 0.5) * 0.5;
            double h = ((double) random.nextFloat() - 0.5) * 0.5;
            double j = ((double) random.nextFloat() - 0.5) * 0.5;
            int k = random.nextInt(2) * 2 - 1;
            if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this))
            {
                d = (double) pos.getX() + 0.5 + 0.25 * (double) k;
                g = (double) (random.nextFloat() * 2.0F * (float) k);
            }
            else
            {
                f = (double) pos.getZ() + 0.5 + 0.25 * (double) k;
                j = (double) (random.nextFloat() * 2.0F * (float) k);
            }

            PaintedBlockEntity paintedBlockEntity = (PaintedBlockEntity) world.getBlockEntity(pos);

            if (paintedBlockEntity != null)
            {
                DustParticleEffect test = new DustParticleEffect(Vec3d.unpackRgb(paintedBlockEntity.getColor(0)).toVector3f(), 0.7F);
                world.addParticle(test, d, e, f, g, h, j);
            }
        }
    }

    static
    {
        AXIS = Properties.AXIS;
        X_SHAPE = Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
        Y_SHAPE = Block.createCuboidShape(0.0, 6.0, 0.0, 16.0, 10.0, 16.0);
        Z_SHAPE = Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    }
}
