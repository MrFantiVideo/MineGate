package fr.minegate.mixin.client.util;

import fr.minegate.MineGate;
import fr.minegate.util.Settings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.Icons;
import net.minecraft.client.util.Window;
import net.minecraft.resource.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Mixin(Window.class)
@Environment(EnvType.CLIENT)
public class WindowMixin
{
    /**
     * Get custom application icon on macOS.
     * Only executes if the client option is enabled.
     */

    @Redirect(method = "setIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Icons;getMacIcon(Lnet/minecraft/resource/ResourcePack;)Lnet/minecraft/resource/InputSupplier;"))
    public InputSupplier<InputStream> getMacIcon(Icons instance, ResourcePack resourcePack) throws URISyntaxException, IOException
    {
        if (Settings.getClientSetting())
        {
            return this.getIcon("minegate.icns");
        }
        return instance.getMacIcon(resourcePack);
    }

    /**
     * Get custom application icon on other device types.
     * Only executes if the client option is enabled.
     */

    @Redirect(method = "setIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Icons;getIcons(Lnet/minecraft/resource/ResourcePack;)Ljava/util/List;"))
    public List<InputSupplier<InputStream>> getIcons(Icons instance, ResourcePack resourcePack) throws URISyntaxException, IOException
    {
        if (Settings.getClientSetting())
        {
            return List.of(this.getIcon("icon_16x16.png"), this.getIcon("icon_32x32.png"), this.getIcon("icon_48x48.png"), this.getIcon("icon_128x128.png"), this.getIcon("icon_256x256.png"));
        }
        return instance.getIcons(resourcePack);
    }

    /**
     * Method getIcon present in the Icons enum of Minecraft.
     * Allows to change the icon with a custom path in the resource pack.
     */

    @Unique
    private InputSupplier<InputStream> getIcon(String fileName) throws URISyntaxException
    {
        String resourcePath = "/assets/minegate/icons/" + MineGate.type.toLowerCase() + "/" + fileName;
        URI fileUri = Objects.requireNonNull(getClass().getResource(resourcePath)).toURI();
        return InputSupplier.create(Paths.get(fileUri));
    }
}
