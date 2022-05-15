package net.minegate.fr.moreblocks.world.biome;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

import static net.minegate.fr.moreblocks.MoreBlocks.NameClient;

public class Biomes
{
    public static final RegistryKey<Biome> HALLOWEEN_KEY;
    static final        Biome              HALLOWEEN;

    /**
     * Initialization.
     **/

    public static void init()
    {
        Registry.register(BuiltinRegistries.BIOME, HALLOWEEN_KEY.getValue(), HALLOWEEN);
    }

    /**
     * Creation of Biomes. (Changes planned next version.)
     **/

    private static Biome createHalloween()
    {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addMonsters(spawnSettings, 95, 5, 100, false);
        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.SNOW)
                .category(Biome.Category.NONE)
                .temperature(1.0F)
                .downfall(1.0F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0xE32626)
                        .waterFogColor(0x890E0E)
                        .foliageColor(0xEF5F1C)
                        .grassColor(0xEF5F1C)
                        .fogColor(0x9048E1)
                        .skyColor(0x7800FF)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    static
    {
        HALLOWEEN_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(NameClient.toLowerCase(), "halloween"));
        HALLOWEEN = createHalloween();
    }
}