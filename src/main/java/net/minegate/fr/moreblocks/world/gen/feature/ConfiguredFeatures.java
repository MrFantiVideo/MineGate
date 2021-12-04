package net.minegate.fr.moreblocks.world.gen.feature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minegate.fr.moreblocks.block.Blocks;

import static net.minegate.fr.moreblocks.MoreBlocks.ConsoleClient;
import static net.minegate.fr.moreblocks.MoreBlocks.NameClient;

public class ConfiguredFeatures
{
    private static final ConfiguredFeature<?, ?> ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE           = Feature.ORE.configure(new OreFeatureConfig(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, Blocks.OBSIDIAN_ORE.getDefaultState(), 7));
    private static final ConfiguredFeature<?, ?> DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE = Feature.ORE.configure(new OreFeatureConfig(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_OBSIDIAN_ORE.getDefaultState(), 7));
    private static final ConfiguredFeature<?, ?> ORE_TILE_OVERWORLD_CONFIGURED_FEATURE               = Feature.ORE.configure(new OreFeatureConfig(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, Blocks.TILE_ORE.getDefaultState(), 9));
    private static final ConfiguredFeature<?, ?> DEEPSLATE_ORE_TILE_OVERWORLD_CONFIGURED_FEATURE     = Feature.ORE.configure(new OreFeatureConfig(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_TILE_ORE.getDefaultState(), 9));

    public static PlacedFeature ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE           = ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(45)));
    public static PlacedFeature DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE = DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE.withPlacement(CountPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(0)));
    public static PlacedFeature ORE_TILE_OVERWORLD_PLACED_FEATURE               = ORE_TILE_OVERWORLD_CONFIGURED_FEATURE.withPlacement(CountPlacementModifier.of(20), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.getTop()));
    public static PlacedFeature DEEPSLATE_ORE_TILE_OVERWORLD_PLACED_FEATURE     = DEEPSLATE_ORE_TILE_OVERWORLD_CONFIGURED_FEATURE.withPlacement(CountPlacementModifier.of(20), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(0)));

    public static void init()
    {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(NameClient.toLowerCase(), "ore_obsidian_overworld"), ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(NameClient.toLowerCase(), "ore_obsidian_overworld"), ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(NameClient.toLowerCase(), "ore_obsidian_overworld")));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(NameClient.toLowerCase(), "deepslate_ore_obsidian_overworld"), DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(NameClient.toLowerCase(), "deepslate_ore_obsidian_overworld"), DEEPSLATE_ORE_OBSIDIAN_OVERWORLD_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(NameClient.toLowerCase(), "deepslate_ore_obsidian_overworld")));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(NameClient.toLowerCase(), "ore_tile_overworld"), ORE_TILE_OVERWORLD_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(NameClient.toLowerCase(), "ore_tile_overworld"), ORE_TILE_OVERWORLD_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(NameClient.toLowerCase(), "ore_tile_overworld")));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(NameClient.toLowerCase(), "deepslate_ore_tile_overworld"), DEEPSLATE_ORE_TILE_OVERWORLD_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(NameClient.toLowerCase(), "deepslate_ore_tile_overworld"), DEEPSLATE_ORE_TILE_OVERWORLD_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(NameClient.toLowerCase(), "deepslate_ore_tile_overworld")));

        ConsoleClient("Loading of the different ores of world.");
    }
}
