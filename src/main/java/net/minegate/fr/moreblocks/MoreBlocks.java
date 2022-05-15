package net.minegate.fr.moreblocks;

import net.fabricmc.api.ModInitializer;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import net.minegate.fr.moreblocks.entity.EntityType;
import net.minegate.fr.moreblocks.entity.SitManager;
import net.minegate.fr.moreblocks.item.Items;
import net.minegate.fr.moreblocks.potion.Potions;
import org.apache.logging.log4j.LogManager;

public class MoreBlocks implements ModInitializer
{
    public static final String NameClient    = "MineGate";
    public static final String VersionClient = "1.2.0";

    /**
     * Initialization.
     **/

    @Override
    public void onInitialize()
    {
        ConsoleClient("Initialization of the Main Class of MineGate. (" + VersionClient + ")");
        Potions.init();
        Blocks.init();
        Items.init();
        EntityType.init();
        SitManager.init();
        DefaultConfig.init();
        ConsoleClient("Let's see if you have the level to play...");
    }

    /**
     * Message in the Console of Client.
     *
     * @param message String of Message.
     **/

    public static void ConsoleClient(String message)
    {
        LogManager.getLogger().info("[" + NameClient + "] " + message);
    }

    /**
     * Return true if Debug Mode is enabled in the configuration file.
     **/

    public static boolean DebugClient()
    {
        return DefaultConfig.get("debugMode");
    }
}