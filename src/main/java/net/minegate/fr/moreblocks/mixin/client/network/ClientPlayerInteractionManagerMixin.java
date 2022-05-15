package net.minegate.fr.moreblocks.mixin.client.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minegate.fr.moreblocks.entity.ScaledEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin
{
    @Shadow
    @Final
    private MinecraftClient client;

    /**
     * Adapts the reach distance based on the size of the entity. (Changes planned next version.)
     **/

    @Inject(at = @At("RETURN"), method = "getReachDistance", cancellable = true)
    public void getReachDistance(CallbackInfoReturnable<Float> info)
    {
        assert client.player != null;
        info.setReturnValue((float) Math.min(8d, info.getReturnValue() * ((ScaledEntity) client.player).getScale()));
    }
}