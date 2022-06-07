package net.minegate.fr.moreblocks.client.gui.screen.options;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minegate.fr.moreblocks.MoreBlocks;
import net.minegate.fr.moreblocks.world.gen.feature.ConfiguredFeatures;

import java.io.*;
import java.nio.file.Path;

public class DefaultConfig
{
    private static final Path path;
    private static final File optionsFile;
    private static final Gson formattedConfig;

    public static boolean sizeChange;
    public static boolean generationOres;

    public static void init()
    {
        if (!optionsFile.exists())
        {
            create();
        }
        if (DefaultConfig.get("generationOres"))
        {
            generationOres = true;
        }
        if (DefaultConfig.get("sizeChange"))
        {
            sizeChange = true;
        }
        if (DefaultConfig.get("debugMode"))
        {
            MoreBlocks.ConsoleClient("The debug mode is enabled.");
        }
    }

    private static boolean defaultSettings(String Property)
    {
        if (Property.equals("debugMode"))
        {
            return false;
        }
        else if (Property.equals("generationOres"))
        {
            return true;
        }
        else if (Property.equals("sizeChange"))
        {
            return true;
        }
        return false;
    }

    public static void create()
    {
        JsonObject config = new JsonObject();
        config.addProperty("debugMode", defaultSettings("debugMode"));
        config.addProperty("generationOres", defaultSettings("generationOres"));
        config.addProperty("sizeChange", defaultSettings("sizeChange"));

        try (FileWriter file = new FileWriter(optionsFile))
        {
            String result = formattedConfig.toJson(config);
            file.write(result);
            file.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean get(String Property)
    {
        if (!optionsFile.exists())
        {
            create();
        }
        try
        {
            JsonObject config = (JsonObject) new JsonParser().parse(new FileReader(optionsFile));
            if (config.get(Property) != null)
            {
                return config.get(Property).getAsBoolean();
            }
            else
            {
                add(Property, defaultSettings(Property));
                return config.get(Property).getAsBoolean();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static void add(String Property, Boolean value)
    {
        try
        {
            JsonObject config = (JsonObject) new JsonParser().parse(new FileReader(optionsFile));
            config.addProperty(Property, value);

            try (FileWriter file = new FileWriter(optionsFile))
            {
                String result = formattedConfig.toJson(config);
                file.write(result);
                file.flush();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void replace(String Property, Boolean value)
    {
        try
        {
            JsonObject config = (JsonObject) new JsonParser().parse(new FileReader(optionsFile));
            config.remove(Property);
            config.addProperty(Property, value);

            try (FileWriter file = new FileWriter(optionsFile))
            {
                String result = formattedConfig.toJson(config);
                file.write(result);
                file.flush();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    static
    {
        path = FabricLoader.getInstance().getConfigDir();
        optionsFile = new File(String.valueOf(path), "minegate.json");
        formattedConfig = new GsonBuilder().setPrettyPrinting().create();
    }
}