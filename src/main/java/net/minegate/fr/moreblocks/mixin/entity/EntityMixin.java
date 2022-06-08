package net.minegate.fr.moreblocks.mixin.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin
{
    @Shadow
    private ImmutableList<Entity> passengerList;

    @Shadow
    private EntityDimensions dimensions;

    @Shadow
    private Vec3d pos;

    @Shadow
    private EntityType<?> type;

    /**
     * Allows you to adapt the position of the passenger on an entity according to its size. (Changes planned next version.)
     **/

    @Inject(at = @At("RETURN"), method = "updatePassengerPosition(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity$PositionUpdater;)V")
    private void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater, CallbackInfo ci)
    {
        if (this.hasPassenger(passenger))
        {
            double scale = passenger instanceof ScaledEntity ? ((ScaledEntity) passenger).getScale() : 1F;
            double d = this.pos.y + this.getMountedHeightOffset() + passenger.getHeightOffset() + ((1.0F - scale) * 0.60F);
            if (this.type.equals(net.minegate.fr.moreblocks.entity.EntityType.SIT))
            {
                d = d - 0.38F;
            } else if (this.type.equals(EntityType.MINECART))
            {
                d = d - 0.38F;
            }
            positionUpdater.accept(passenger, this.pos.x, d, this.pos.z);
        }
    }

    public boolean hasPassenger(Entity passenger)
    {
        return this.passengerList.contains(passenger);
    }

    public double getMountedHeightOffset()
    {
        return (double) this.dimensions.height * 0.75D;
    }
}