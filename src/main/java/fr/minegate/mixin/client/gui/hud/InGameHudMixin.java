package fr.minegate.mixin.client.gui.hud;

import fr.minegate.client.gui.hud.TitleDevHud;
import fr.minegate.util.Settings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin
{
    @Mutable
    @Final
    @Shadow
    private final MinecraftClient client;
    @Shadow
    private       int             scaledWidth;
    @Shadow
    private       int             scaledHeight;

    public InGameHudMixin(MinecraftClient client)
    {
        this.client = client;
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    public void render(DrawContext context, float tickDelta, CallbackInfo ci)
    {
        if(Settings.getClientSetting())
        {
            TitleDevHud.render(context, client, scaledWidth, scaledHeight);
        }
    }
}
