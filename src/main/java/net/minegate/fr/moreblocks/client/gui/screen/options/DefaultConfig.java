package net.minegate.fr.moreblocks.client.gui.screen.options;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minegate.fr.moreblocks.world.gen.feature.ConfiguredFeatures;

import java.io.*;
import java.nio.file.Path;

public class DefaultConfig
{
    private static final Path   path        = FabricLoader.getInstance().getConfigDir();
    private static final File   optionsFile = new File(String.valueOf(path), "minegate.json");

    public static void init()
    {
        if(!optionsFile.exists())
        {
            create();
        }
        if(DefaultConfig.get("generationOres"))
        {
            ConfiguredFeatures.init();
        }
    }

    public static void create()
    {
        JsonObject defaultConfig = new JsonObject();
        defaultConfig.addProperty("generationOres", true);

        try (FileWriter file = new FileWriter(optionsFile))
        {
            file.write(defaultConfig.toString());
            file.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean get(String Property)
    {
        try
        {
            JsonObject file = (JsonObject) new JsonParser().parse(new FileReader(optionsFile));
            return file.get(Property).getAsBoolean();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static void replace(String Property, Boolean value)
    {
        try
        {
            JsonObject file = (JsonObject) new JsonParser().parse(new FileReader(optionsFile));
            file.remove(Property);
            file.addProperty(Property, value);

            try (FileWriter newFile = new FileWriter(optionsFile))
            {
                newFile.write(file.toString());
                newFile.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
