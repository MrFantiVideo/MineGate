package net.minegate.fr.moreblocks.mixin.client.gui.screen.ingame;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public class InventoryScreenMixin
{
    /**
     * Adapt the size of the entity in the inventory. (Changes planned next version.)
     **/

    @ModifyVariable(at = @At("HEAD"), method = "drawEntity", argsOnly = true, index = 2)
    private static int adjustEntitySize(int initial, int x, int y, int size, float mouseX, float mouseY, LivingEntity entity)
    {
        if (entity instanceof ScaledEntity)
        {
            return (int) (initial / ((ScaledEntity) entity).getScale());
        }
        else
        {
            return initial;
        }
    }
}