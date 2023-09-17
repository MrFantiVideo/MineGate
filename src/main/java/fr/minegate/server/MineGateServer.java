package fr.minegate.server;

import fr.minegate.MineGate;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class MineGateServer implements DedicatedServerModInitializer
{
    @Override
    public void onInitializeServer()
    {
        MineGate.log("All is well! Good game with MineGate!");
    }
}
