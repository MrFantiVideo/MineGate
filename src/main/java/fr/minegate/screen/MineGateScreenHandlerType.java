package fr.minegate.screen;

import fr.minegate.MineGate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class MineGateScreenHandlerType
{
    public static final ScreenHandlerType<PaintingPaletteScreenHandler> PAINTING_PALETTE_SCREEN = register("painting_palette", PaintingPaletteScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory)
    {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(MineGate.name.toLowerCase(), id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void init()
    {
        MineGate.log("J'ai bien chargé les handler type de MineGate...");
    }
}