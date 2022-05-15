package net.minegate.fr.moreblocks.world.gen.feature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minegate.fr.moreblocks.MoreBlocks;
import net.minegate.fr.moreblocks.block.Blocks;

import java.util.Arrays;

public class ConfiguredFeatures
{
    private static final ConfiguredFeature<?, ?> ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE;
    private static final ConfiguredFeature<?, ?> DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE;
    private static final ConfiguredFeature<?, ?> ORE_TILE_OVERWORLD_CONFIGURED_FEATURE;
    private static final ConfiguredFeature<?, ?> DEEPSLATE_ORE_TILE_OVERWORLD_CONFIGURED_FEATURE;
    private static final PlacedFeature           ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE;
    private static final PlacedFeature           DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE;
    private static final PlacedFeature           ORE_TILE_OVERWORLD_PLACED_FEATURE;
    private static final PlacedFeature           DEEPSLATE_ORE_TILE_OVERWORLD_PLACED_FEATURE;

    /**
     * Initialization of ores in world generation.
     **/

    public static void init()
    {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MoreBlocks.NameClient.toLowerCase(), "ore_obsidian_overworld"), ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(MoreBlocks.NameClient.toLowerCase(), "ore_obsidian_overworld"), ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MoreBlocks.NameClient.toLowerCase(), "ore_obsidian_overworld")));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MoreBlocks.NameClient.toLowerCase(), "deepslate_ore_obsidian_overworld"), DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(MoreBlocks.NameClient.toLowerCase(), "deepslate_ore_obsidian_overworld"), DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MoreBlocks.NameClient.toLowerCase(), "deepslate_ore_obsidian_overworld")));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MoreBlocks.NameClient.toLowerCase(), "ore_tile_overworld"), ORE_TILE_OVERWORLD_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(MoreBlocks.NameClient.toLowerCase(), "ore_tile_overworld"), ORE_TILE_OVERWORLD_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MoreBlocks.NameClient.toLowerCase(), "ore_tile_overworld")));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MoreBlocks.NameClient.toLowerCase(), "deepslate_ore_tile_overworld"), DEEPSLATE_ORE_TILE_OVERWORLD_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(MoreBlocks.NameClient.toLowerCase(), "deepslate_ore_tile_overworld"), DEEPSLATE_ORE_TILE_OVERWORLD_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MoreBlocks.NameClient.toLowerCase(), "deepslate_ore_tile_overworld")));
        MoreBlocks.ConsoleClient("Loading of the different ores of world.");
    }

    static
    {
        ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE = new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, Blocks.OBSIDIAN_ORE.getDefaultState(), 7));
        DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE = new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_OBSIDIAN_ORE.getDefaultState(), 7));
        ORE_TILE_OVERWORLD_CONFIGURED_FEATURE = new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, Blocks.TILE_ORE.getDefaultState(), 9));
        DEEPSLATE_ORE_TILE_OVERWORLD_CONFIGURED_FEATURE = new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_TILE_ORE.getDefaultState(), 9));
        ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE), Arrays.asList(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(45))));
        DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE), Arrays.asList(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(0))));
        ORE_TILE_OVERWORLD_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(ORE_TILE_OVERWORLD_CONFIGURED_FEATURE), Arrays.asList(CountPlacementModifier.of(20), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.getTop())));
        DEEPSLATE_ORE_TILE_OVERWORLD_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(DEEPSLATE_ORE_TILE_OVERWORLD_CONFIGURED_FEATURE), Arrays.asList(CountPlacementModifier.of(20), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(0))));
    }
}