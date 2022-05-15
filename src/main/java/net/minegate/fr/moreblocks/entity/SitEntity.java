package net.minegate.fr.moreblocks.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Arm;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.HashMap;

public class SitEntity extends LivingEntity
{
    public static final  HashMap<Vec3d, BlockPos> OCCUPIED = new HashMap<>();
    private static final DirectionProperty        FACING   = HorizontalFacingBlock.FACING;

    private boolean _removable = false;
    private boolean location   = false;
    private Block   _block;

    public SitEntity(EntityType<? extends SitEntity> type, World world)
    {
        super(type, world);
        noClip = true;
    }

    public void setPosition(Block sittingBlock, double x, double y, double z)
    {
        _block = sittingBlock;
        setPos(x, y, z);
        EntityDimensions dimensions = getDimensions(EntityPose.STANDING);
        double dimWidth = (double) dimensions.width / 2.0;
        double dimHeight = dimensions.height;
        Box box = new Box(x - dimWidth, y, z - dimWidth, x + dimWidth, y + dimHeight, z + dimWidth);
        setBoundingBox(box);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger)
    {
        if (passenger instanceof PlayerEntity)
        {
            BlockPos pos = OCCUPIED.remove(getPos());

            if (pos != null)
            {
                remove(RemovalReason.DISCARDED);
                return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            }
        }

        remove(RemovalReason.DISCARDED);
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    protected void addPassenger(Entity passenger)
    {
        super.addPassenger(passenger);
    }

    @Override
    public void removePassenger(Entity passenger)
    {
        super.removePassenger(passenger);
        _removable = true;
        SitManager.unSit((ISitBlock) _block);

        double scale = passenger instanceof ScaledEntity ? ((ScaledEntity) passenger).getScale() : 1.0F;
        super.teleport(passenger.getX(), passenger.getY() + ((1.0F + scale) * 0.60F), passenger.getZ());
    }

    @Override
    public void tick()
    {
        if (_removable && !hasPassengers())
        {
            SitEntity.OCCUPIED.remove(getPos());
            remove(RemovalReason.DISCARDED);
        }
        if (hasPassengers() && !location)
        {
            Entity passenger = this.getPassengerList().get(0);
            BlockState blockState = world.getBlockState(this.getBlockPos());
            Direction direction = blockState.get(FACING);

            this.prevHeadYaw = this.headYaw;
            this.bodyYaw = this.headYaw;
            this.prevBodyYaw = this.bodyYaw;

            switch (direction)
            {
                case NORTH:
                    bodyYaw = 180;
                    prevBodyYaw = 180;
                    break;
                case EAST:
                    bodyYaw = 270;
                    prevBodyYaw = 270;
                    break;
                case SOUTH:
                    bodyYaw = 0;
                    prevBodyYaw = 0;
                    break;
                case WEST:
                    bodyYaw = 90;
                    prevBodyYaw = 90;
                    break;
            }
            location = true;
        }
        BlockState blockState = world.getBlockState(this.getBlockPos());
        Block block = blockState.getBlock();
        if (hasPassengers() && !(block instanceof ISitBlock) && !_removable)
        {
            Entity passenger = this.getPassengerList().get(0);
            this.removeAllPassengers();
            _removable = true;
            SitManager.unSit((ISitBlock) _block);
            super.teleport(passenger.getX(), passenger.getY() + 0.65, passenger.getZ());
        }
    }

    @Override
    public void initDataTracker()
    {
        super.initDataTracker();
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt)
    {
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt)
    {
    }

    @Override
    public boolean collides()
    {
        return false;
    }

    @Override
    public boolean hasNoGravity()
    {
        return true;
    }

    @Override
    public boolean isInvisible()
    {
        return false;
    }

    @Override
    public float getHealth()
    {
        return 0F;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance statusEffectInstance)
    {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return null;
    }

    @Override
    public void equipStack(EquipmentSlot equipmentSlot, ItemStack itemStack)
    {
    }

    @Override
    public Iterable<ItemStack> getArmorItems()
    {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot equipmentSlot)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public Arm getMainArm()
    {
        return Arm.RIGHT;
    }
}