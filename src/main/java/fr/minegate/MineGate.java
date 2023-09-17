package fr.minegate;

import fr.minegate.block.Blocks;
import fr.minegate.block.PaintedBlocks;
import fr.minegate.block.entity.BlockEntityType;
import fr.minegate.entity.EntityType;
import fr.minegate.item.ItemGroups;
import fr.minegate.item.Items;
import fr.minegate.server.network.NetworkManager;
import fr.minegate.screen.MineGateScreenHandlerType;
import fr.minegate.server.command.SetColorItemCommand;
import fr.minegate.util.Settings;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;

public class MineGate implements ModInitializer
{
    public static final String name    = "MineGate";
    public static final String type    = "Dev";
    public static final String version = "1.0.0";

    /**
     * Initialization.
     */

    @Override
    public void onInitialize()
    {
        MineGate.log("Initialization of the main class. (" + type + "-" + version + ")");
        //Normalizer.init();
        Settings.loadSettings();
        Blocks.init();
        Items.init();
        ItemGroups.init();
        PaintedBlocks.init();
        EntityType.init();
        BlockEntityType.init();
        MineGateScreenHandlerType.init();
        NetworkManager.init();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
        {
            SetColorItemCommand.register(dispatcher);
        });
        MineGate.log("Let's see if you have the level to play...");
    }

    /**
     * Message in the Console for logging.
     *
     * @param message String of Message.
     */

    public static void log(String message)
    {
        LogManager.getLogger().info("[" + name + "] " + message);
    }
}
