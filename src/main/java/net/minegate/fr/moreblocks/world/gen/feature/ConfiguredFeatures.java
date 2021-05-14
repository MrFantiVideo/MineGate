package net.minegate.fr.moreblocks.world.gen.feature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minegate.fr.moreblocks.block.Blocks;

import static net.minegate.fr.moreblocks.MoreBlocks.ConsoleClient;
import static net.minegate.fr.moreblocks.MoreBlocks.NameClient;

public class ConfiguredFeatures
{
    private static final ConfiguredFeature<?, ?> ORE_OBSIDIAN_OVERWORLD =  Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.OBSIDIAN_ORE.getDefaultState(), 8)).rangeOf(16).spreadHorizontally().repeat(8);
    private static final ConfiguredFeature<?, ?> ORE_TILE_OVERWORLD =  Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.TILE_ORE.getDefaultState(), 9)).rangeOf(64).spreadHorizontally().repeat(20);

    public static void init()
    {
        RegistryKey<ConfiguredFeature<?, ?>> OreObsidianOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier(NameClient.toLowerCase(), "ore_obsidian_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, OreObsidianOverworld.getValue(), ORE_OBSIDIAN_OVERWORLD);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, OreObsidianOverworld);
        RegistryKey<ConfiguredFeature<?, ?>> OreTileOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier(NameClient.toLowerCase(), "ore_tile_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, OreTileOverworld.getValue(), ORE_TILE_OVERWORLD);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, OreTileOverworld);
        ConsoleClient.info("[" + NameClient + "] Loading of the different ores of world.");
    }
}
