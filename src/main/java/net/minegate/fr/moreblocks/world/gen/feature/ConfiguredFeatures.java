package net.minegate.fr.moreblocks.world.gen.feature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minegate.fr.moreblocks.block.Blocks;

import static net.minegate.fr.moreblocks.MoreBlocks.ConsoleClient;
import static net.minegate.fr.moreblocks.MoreBlocks.NameClient;

public class ConfiguredFeatures
{
    private static final ConfiguredFeature<?, ?> ORE_OBSIDIAN_OVERWORLD           = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.OBSIDIAN_ORE.getDefaultState(), 8)).range(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.fixed(18), YOffset.fixed(32)))).spreadHorizontally().repeat(8);
    private static final ConfiguredFeature<?, ?> DEEPSLATE_ORE_OBSIDIAN_OVERWORLD = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.DEEPSLATE_OBSIDIAN_ORE.getDefaultState(), 8)).range(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.fixed(0), YOffset.fixed(18)))).spreadHorizontally().repeat(8);
    private static final ConfiguredFeature<?, ?> ORE_TILE_OVERWORLD               = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.TILE_ORE.getDefaultState(), 9)).range(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.fixed(18), YOffset.fixed(64)))).spreadHorizontally().repeat(20);
    private static final ConfiguredFeature<?, ?> DEEPSLATE_ORE_TILE_OVERWORLD     = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, Blocks.DEEPSLATE_TILE_ORE.getDefaultState(), 9)).range(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.fixed(0), YOffset.fixed(18)))).spreadHorizontally().repeat(20);

    public static void init()
    {
        RegistryKey<ConfiguredFeature<?, ?>> OreObsidianOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(NameClient.toLowerCase(), "ore_obsidian_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, OreObsidianOverworld.getValue(), ORE_OBSIDIAN_OVERWORLD);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, OreObsidianOverworld);
        RegistryKey<ConfiguredFeature<?, ?>> DeepslateOreObsidianOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(NameClient.toLowerCase(), "deepslate_ore_obsidian_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, DeepslateOreObsidianOverworld.getValue(), DEEPSLATE_ORE_OBSIDIAN_OVERWORLD);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, DeepslateOreObsidianOverworld);
        RegistryKey<ConfiguredFeature<?, ?>> OreTileOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(NameClient.toLowerCase(), "ore_tile_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, OreTileOverworld.getValue(), ORE_TILE_OVERWORLD);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, OreTileOverworld);
        RegistryKey<ConfiguredFeature<?, ?>> DeepslateOreTileOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(NameClient.toLowerCase(), "deepslate_ore_tile_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, DeepslateOreTileOverworld.getValue(), DEEPSLATE_ORE_TILE_OVERWORLD);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, DeepslateOreTileOverworld);
        ConsoleClient("Loading of the different ores of world.");
    }
}
