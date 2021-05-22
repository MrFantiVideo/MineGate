package net.minegate.fr.moreblocks;

import net.minegate.fr.moreblocks.block.entity.SitManager;
import net.minegate.fr.moreblocks.client.gui.screen.options.DefaultConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.item.Items;

// Main Class of MineGate.

public class MoreBlocks implements ModInitializer
{
    // MineGate Client.

    public static final boolean DebugClient   = false;
    public static final String  NameClient    = "MineGate";
    public static final String  VersionClient = "1.1.3";
    public static final Logger  ConsoleClient = LogManager.getLogger();

    // Initialization.

    @Override
    public void onInitialize()
    {
        ConsoleClient.info("[" + NameClient + "] Initialization of the Main Class of MineGate. (" + VersionClient + ")");
        if (DebugClient)
        {
            ConsoleClient.info("[" + NameClient + "] The debug mode is enabled.");
        }
        Blocks.init();
        Items.init();
        SitManager.init();
        DefaultConfig.init();
        ConsoleClient.info("[" + NameClient + "] Let's see if you have the level to play...");
    }
}