package fr.minegate.client;

import fr.minegate.MineGate;
import fr.minegate.client.color.block.BlockColors;
import fr.minegate.client.gui.screen.ingame.PaintingPaletteScreen;
import fr.minegate.client.render.RenderLayers;
import fr.minegate.screen.MineGateScreenHandlerType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class MineGateClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        MineGate.log("Initialization of the client class.");
        BlockColors.init();
        RenderLayers.init();
        HandledScreens.register(MineGateScreenHandlerType.PAINTING_PALETTE_SCREEN, PaintingPaletteScreen::new);
        MineGate.log("All is well! Good game with MineGate!");
    }
}
