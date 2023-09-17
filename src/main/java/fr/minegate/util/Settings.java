package fr.minegate.util;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Settings
{
    private final  File       configFile;
    private static JsonObject configJson;

    public Settings(String fileName)
    {
        File configDir = FabricLoader.getInstance().getConfigDir().toFile();
        configFile = new File(configDir, fileName);
    }

    public void load()
    {
        try
        {
            if (!configFile.exists())
            {
                createDefaultConfig();
                save();
            }
            else
            {
                readConfig();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void loadSettings()
    {
        Settings settings = new Settings("minegate-dev.json");
        settings.load();
    }

    private void createDefaultConfig()
    {
        configJson = new JsonObject();

        JsonArray settingsArray = new JsonArray();
        JsonObject clientSetting = new JsonObject();
        clientSetting.addProperty("client", false);
        settingsArray.add(clientSetting);

        configJson.add("settings", settingsArray);
    }

    private void readConfig() throws IOException
    {
        FileReader fileReader = new FileReader(configFile);
        JsonElement jsonElement = JsonParser.parseReader(fileReader);
        configJson = jsonElement.getAsJsonObject();
        fileReader.close();
    }

    public void save() throws IOException
    {
        FileWriter fileWriter = new FileWriter(configFile);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(configJson);
        fileWriter.write(json);
        fileWriter.close();
    }

    public static boolean getClientSetting()
    {
        JsonArray settingsArray = configJson.getAsJsonArray("settings");
        JsonObject clientSetting = settingsArray.get(0).getAsJsonObject();
        return clientSetting.get("client").getAsBoolean();
    }

    public void setClientSetting(boolean value)
    {
        JsonArray settingsArray = configJson.getAsJsonArray("settings");
        JsonObject clientSetting = settingsArray.get(0).getAsJsonObject();
        clientSetting.addProperty("client", value);
    }
}