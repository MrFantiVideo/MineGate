package fr.minegate.mixin.client;

import fr.minegate.MineGate;
import fr.minegate.util.Settings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin
{
    /**
     * Changes the name of the window.
     * Only executes if the client option is enabled.
     */

    @Redirect(method = "getWindowTitle", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;"))
    public String nameOfWindows(StringBuilder instance)
    {
        if (Settings.getClientSetting())
        {
            return MineGate.name + " · " + MineGate.type;
        }
        return instance.toString();
    }

    /**
     * Add custom sound when game crashes. But that shouldn't happen with my mod... Right?
     * Only executes if the client option is enabled.
     */

    @Inject(method = "printCrashReport", at = @At("HEAD"))
    private static void playCrashSound(CallbackInfo ci) throws InterruptedException
    {
        if (Settings.getClientSetting())
        {
            MinecraftClient minecraft = MinecraftClient.getInstance();
            if (minecraft != null && minecraft.isRunning())
            {
                minecraft.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ENTITY_VILLAGER_HURT, 1.0F));
                Thread.sleep(10);
            }
        }
    }
}
