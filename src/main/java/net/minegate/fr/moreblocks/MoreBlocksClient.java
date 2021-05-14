package net.minegate.fr.moreblocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.block.SlabPlantableBlock;
import net.minegate.fr.moreblocks.block.SlabSpreadableBlock;
import net.minegate.fr.moreblocks.block.entity.SitEntity;
import net.minegate.fr.moreblocks.block.entity.SitManager;
import net.minegate.fr.moreblocks.item.Items;

import java.util.Random;

import static net.minegate.fr.moreblocks.MoreBlocks.*;

// Main Class of MineGate Client.

public class MoreBlocksClient implements ClientModInitializer
{
    // Initialization.

    @Override
    public void onInitializeClient()
    {
        // Render Entities on the client.

        EntityRendererRegistry.INSTANCE.register(SitManager.SIT_ENTITY_TYPE, (entityRenderDispatcher, context) -> new EmptyRenderer(entityRenderDispatcher));
        if (DebugClient)
        {
            ConsoleClient.info("[" + NameClient + "] EntityRendererRegistry : OK !");
        }

        // Render Blocks on the client.

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                Blocks.GLASS_STAIRS,
                Blocks.GLASS_STAIRS_VERTICAL,
                Blocks.GLASS_SLAB,
                Blocks.GLASS_SLAB_VERTICAL,
                Blocks.GLASS_QUARTER,
                Blocks.GLASS_QUARTER_VERTICAL,
                Blocks.GLASS_EIGHTH,
                Blocks.ICE_STAIRS,
                Blocks.ICE_STAIRS_VERTICAL,
                Blocks.ICE_SLAB,
                Blocks.ICE_SLAB_VERTICAL,
                Blocks.ICE_QUARTER,
                Blocks.ICE_QUARTER_VERTICAL,
                Blocks.ICE_EIGHTH,
                Blocks.BLACK_STAINED_GLASS_STAIRS,
                Blocks.BLUE_STAINED_GLASS_STAIRS,
                Blocks.BROWN_STAINED_GLASS_STAIRS,
                Blocks.CYAN_STAINED_GLASS_STAIRS,
                Blocks.GRAY_STAINED_GLASS_STAIRS,
                Blocks.GREEN_STAINED_GLASS_STAIRS,
                Blocks.LIGHT_BLUE_STAINED_GLASS_STAIRS,
                Blocks.LIGHT_GRAY_STAINED_GLASS_STAIRS,
                Blocks.LIME_STAINED_GLASS_STAIRS,
                Blocks.MAGENTA_STAINED_GLASS_STAIRS,
                Blocks.ORANGE_STAINED_GLASS_STAIRS,
                Blocks.PINK_STAINED_GLASS_STAIRS,
                Blocks.PURPLE_STAINED_GLASS_STAIRS,
                Blocks.RED_STAINED_GLASS_STAIRS,
                Blocks.WHITE_STAINED_GLASS_STAIRS,
                Blocks.YELLOW_STAINED_GLASS_STAIRS,
                Blocks.BLACK_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.BLUE_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.BROWN_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.CYAN_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.GRAY_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.GREEN_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.LIGHT_BLUE_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.LIGHT_GRAY_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.LIME_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.MAGENTA_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.ORANGE_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.PINK_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.PURPLE_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.RED_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.WHITE_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.YELLOW_STAINED_GLASS_STAIRS_VERTICAL,
                Blocks.BLACK_STAINED_GLASS_SLAB,
                Blocks.BLUE_STAINED_GLASS_SLAB,
                Blocks.BROWN_STAINED_GLASS_SLAB,
                Blocks.CYAN_STAINED_GLASS_SLAB,
                Blocks.GRAY_STAINED_GLASS_SLAB,
                Blocks.GREEN_STAINED_GLASS_SLAB,
                Blocks.LIGHT_BLUE_STAINED_GLASS_SLAB,
                Blocks.LIGHT_GRAY_STAINED_GLASS_SLAB,
                Blocks.LIME_STAINED_GLASS_SLAB,
                Blocks.MAGENTA_STAINED_GLASS_SLAB,
                Blocks.ORANGE_STAINED_GLASS_SLAB,
                Blocks.PINK_STAINED_GLASS_SLAB,
                Blocks.PURPLE_STAINED_GLASS_SLAB,
                Blocks.RED_STAINED_GLASS_SLAB,
                Blocks.WHITE_STAINED_GLASS_SLAB,
                Blocks.YELLOW_STAINED_GLASS_SLAB,
                Blocks.BLACK_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.BLUE_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.BROWN_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.CYAN_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.GRAY_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.GREEN_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.LIGHT_BLUE_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.LIGHT_GRAY_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.LIME_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.MAGENTA_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.ORANGE_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.PINK_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.PURPLE_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.RED_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.WHITE_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.YELLOW_STAINED_GLASS_SLAB_VERTICAL,
                Blocks.BLACK_STAINED_GLASS_QUARTER,
                Blocks.BLUE_STAINED_GLASS_QUARTER,
                Blocks.BROWN_STAINED_GLASS_QUARTER,
                Blocks.CYAN_STAINED_GLASS_QUARTER,
                Blocks.GRAY_STAINED_GLASS_QUARTER,
                Blocks.GREEN_STAINED_GLASS_QUARTER,
                Blocks.LIGHT_BLUE_STAINED_GLASS_QUARTER,
                Blocks.LIGHT_GRAY_STAINED_GLASS_QUARTER,
                Blocks.LIME_STAINED_GLASS_QUARTER,
                Blocks.MAGENTA_STAINED_GLASS_QUARTER,
                Blocks.ORANGE_STAINED_GLASS_QUARTER,
                Blocks.PINK_STAINED_GLASS_QUARTER,
                Blocks.PURPLE_STAINED_GLASS_QUARTER,
                Blocks.RED_STAINED_GLASS_QUARTER,
                Blocks.WHITE_STAINED_GLASS_QUARTER,
                Blocks.YELLOW_STAINED_GLASS_QUARTER,
                Blocks.BLACK_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.BLUE_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.BROWN_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.CYAN_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.GRAY_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.GREEN_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.LIGHT_BLUE_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.LIGHT_GRAY_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.LIME_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.MAGENTA_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.ORANGE_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.PINK_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.PURPLE_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.RED_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.WHITE_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.YELLOW_STAINED_GLASS_QUARTER_VERTICAL,
                Blocks.BLACK_STAINED_GLASS_EIGHTH,
                Blocks.BLUE_STAINED_GLASS_EIGHTH,
                Blocks.BROWN_STAINED_GLASS_EIGHTH,
                Blocks.CYAN_STAINED_GLASS_EIGHTH,
                Blocks.GRAY_STAINED_GLASS_EIGHTH,
                Blocks.GREEN_STAINED_GLASS_EIGHTH,
                Blocks.LIGHT_BLUE_STAINED_GLASS_EIGHTH,
                Blocks.LIGHT_GRAY_STAINED_GLASS_EIGHTH,
                Blocks.LIME_STAINED_GLASS_EIGHTH,
                Blocks.MAGENTA_STAINED_GLASS_EIGHTH,
                Blocks.ORANGE_STAINED_GLASS_EIGHTH,
                Blocks.PINK_STAINED_GLASS_EIGHTH,
                Blocks.PURPLE_STAINED_GLASS_EIGHTH,
                Blocks.RED_STAINED_GLASS_EIGHTH,
                Blocks.WHITE_STAINED_GLASS_EIGHTH,
                Blocks.YELLOW_STAINED_GLASS_EIGHTH,
                Blocks.OAK_LEAVES_STAIRS,
                Blocks.OAK_LEAVES_STAIRS_VERTICAL,
                Blocks.OAK_LEAVES_SLAB,
                Blocks.OAK_LEAVES_SLAB_VERTICAL,
                Blocks.OAK_LEAVES_QUARTER,
                Blocks.OAK_LEAVES_QUARTER_VERTICAL,
                Blocks.OAK_LEAVES_EIGHTH,
                Blocks.SPRUCE_LEAVES_STAIRS,
                Blocks.SPRUCE_LEAVES_STAIRS_VERTICAL,
                Blocks.SPRUCE_LEAVES_SLAB,
                Blocks.SPRUCE_LEAVES_SLAB_VERTICAL,
                Blocks.SPRUCE_LEAVES_QUARTER,
                Blocks.SPRUCE_LEAVES_QUARTER_VERTICAL,
                Blocks.SPRUCE_LEAVES_EIGHTH,
                Blocks.BIRCH_LEAVES_STAIRS,
                Blocks.BIRCH_LEAVES_STAIRS_VERTICAL,
                Blocks.BIRCH_LEAVES_SLAB,
                Blocks.BIRCH_LEAVES_SLAB_VERTICAL,
                Blocks.BIRCH_LEAVES_QUARTER,
                Blocks.BIRCH_LEAVES_QUARTER_VERTICAL,
                Blocks.BIRCH_LEAVES_EIGHTH,
                Blocks.JUNGLE_LEAVES_STAIRS,
                Blocks.JUNGLE_LEAVES_STAIRS_VERTICAL,
                Blocks.JUNGLE_LEAVES_SLAB,
                Blocks.JUNGLE_LEAVES_SLAB_VERTICAL,
                Blocks.JUNGLE_LEAVES_QUARTER,
                Blocks.JUNGLE_LEAVES_QUARTER_VERTICAL,
                Blocks.JUNGLE_LEAVES_EIGHTH,
                Blocks.ACACIA_LEAVES_STAIRS,
                Blocks.ACACIA_LEAVES_STAIRS_VERTICAL,
                Blocks.ACACIA_LEAVES_SLAB,
                Blocks.ACACIA_LEAVES_SLAB_VERTICAL,
                Blocks.ACACIA_LEAVES_QUARTER,
                Blocks.ACACIA_LEAVES_QUARTER_VERTICAL,
                Blocks.ACACIA_LEAVES_EIGHTH,
                Blocks.DARK_OAK_LEAVES_STAIRS,
                Blocks.DARK_OAK_LEAVES_STAIRS_VERTICAL,
                Blocks.DARK_OAK_LEAVES_SLAB,
                Blocks.DARK_OAK_LEAVES_SLAB_VERTICAL,
                Blocks.DARK_OAK_LEAVES_QUARTER,
                Blocks.DARK_OAK_LEAVES_QUARTER_VERTICAL,
                Blocks.DARK_OAK_LEAVES_EIGHTH,
                Blocks.WOODCUTTER
        );
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getSpruceColor(),
                Blocks.SPRUCE_LEAVES_STAIRS, Blocks.SPRUCE_LEAVES_STAIRS_VERTICAL, Blocks.SPRUCE_LEAVES_SLAB, Blocks.SPRUCE_LEAVES_SLAB_VERTICAL, Blocks.SPRUCE_LEAVES_QUARTER, Blocks.SPRUCE_LEAVES_QUARTER_VERTICAL, Blocks.SPRUCE_LEAVES_EIGHTH
        );
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getBirchColor(),
                Blocks.BIRCH_LEAVES_STAIRS, Blocks.BIRCH_LEAVES_STAIRS_VERTICAL, Blocks.BIRCH_LEAVES_SLAB, Blocks.BIRCH_LEAVES_SLAB_VERTICAL, Blocks.BIRCH_LEAVES_QUARTER, Blocks.BIRCH_LEAVES_QUARTER_VERTICAL, Blocks.BIRCH_LEAVES_EIGHTH
        );
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
                Blocks.OAK_LEAVES_STAIRS, Blocks.OAK_LEAVES_STAIRS_VERTICAL, Blocks.OAK_LEAVES_SLAB, Blocks.OAK_LEAVES_SLAB_VERTICAL, Blocks.OAK_LEAVES_QUARTER, Blocks.OAK_LEAVES_QUARTER_VERTICAL, Blocks.OAK_LEAVES_EIGHTH,
                Blocks.JUNGLE_LEAVES_STAIRS, Blocks.JUNGLE_LEAVES_STAIRS_VERTICAL, Blocks.JUNGLE_LEAVES_SLAB, Blocks.JUNGLE_LEAVES_SLAB_VERTICAL, Blocks.JUNGLE_LEAVES_QUARTER, Blocks.JUNGLE_LEAVES_QUARTER_VERTICAL, Blocks.JUNGLE_LEAVES_EIGHTH,
                Blocks.ACACIA_LEAVES_STAIRS, Blocks.ACACIA_LEAVES_STAIRS_VERTICAL, Blocks.ACACIA_LEAVES_SLAB, Blocks.ACACIA_LEAVES_SLAB_VERTICAL, Blocks.ACACIA_LEAVES_QUARTER, Blocks.ACACIA_LEAVES_QUARTER_VERTICAL, Blocks.ACACIA_LEAVES_EIGHTH,
                Blocks.DARK_OAK_LEAVES_STAIRS, Blocks.DARK_OAK_LEAVES_STAIRS_VERTICAL, Blocks.DARK_OAK_LEAVES_SLAB, Blocks.DARK_OAK_LEAVES_SLAB_VERTICAL, Blocks.DARK_OAK_LEAVES_QUARTER, Blocks.DARK_OAK_LEAVES_QUARTER_VERTICAL, Blocks.DARK_OAK_LEAVES_EIGHTH
        );

        // Render Items on the client.

        BlockRenderLayerMap.INSTANCE.putItems(RenderLayer.getTranslucent(),
                Items.BLACK_STAINED_GLASS_STAIRS,
                Items.BLUE_STAINED_GLASS_STAIRS,
                Items.BROWN_STAINED_GLASS_STAIRS,
                Items.CYAN_STAINED_GLASS_STAIRS,
                Items.GRAY_STAINED_GLASS_STAIRS,
                Items.GREEN_STAINED_GLASS_STAIRS,
                Items.LIGHT_BLUE_STAINED_GLASS_STAIRS,
                Items.LIGHT_GRAY_STAINED_GLASS_STAIRS,
                Items.LIME_STAINED_GLASS_STAIRS,
                Items.MAGENTA_STAINED_GLASS_STAIRS,
                Items.ORANGE_STAINED_GLASS_STAIRS,
                Items.PINK_STAINED_GLASS_STAIRS,
                Items.PURPLE_STAINED_GLASS_STAIRS,
                Items.RED_STAINED_GLASS_STAIRS,
                Items.WHITE_STAINED_GLASS_STAIRS,
                Items.YELLOW_STAINED_GLASS_STAIRS,
                Items.BLACK_STAINED_GLASS_STAIRS_VERTICAL,
                Items.BLUE_STAINED_GLASS_STAIRS_VERTICAL,
                Items.BROWN_STAINED_GLASS_STAIRS_VERTICAL,
                Items.CYAN_STAINED_GLASS_STAIRS_VERTICAL,
                Items.GRAY_STAINED_GLASS_STAIRS_VERTICAL,
                Items.GREEN_STAINED_GLASS_STAIRS_VERTICAL,
                Items.LIGHT_BLUE_STAINED_GLASS_STAIRS_VERTICAL,
                Items.LIGHT_GRAY_STAINED_GLASS_STAIRS_VERTICAL,
                Items.LIME_STAINED_GLASS_STAIRS_VERTICAL,
                Items.MAGENTA_STAINED_GLASS_STAIRS_VERTICAL,
                Items.ORANGE_STAINED_GLASS_STAIRS_VERTICAL,
                Items.PINK_STAINED_GLASS_STAIRS_VERTICAL,
                Items.PURPLE_STAINED_GLASS_STAIRS_VERTICAL,
                Items.RED_STAINED_GLASS_STAIRS_VERTICAL,
                Items.WHITE_STAINED_GLASS_STAIRS_VERTICAL,
                Items.YELLOW_STAINED_GLASS_STAIRS_VERTICAL,
                Items.BLACK_STAINED_GLASS_SLAB,
                Items.BLUE_STAINED_GLASS_SLAB,
                Items.BROWN_STAINED_GLASS_SLAB,
                Items.CYAN_STAINED_GLASS_SLAB,
                Items.GRAY_STAINED_GLASS_SLAB,
                Items.GREEN_STAINED_GLASS_SLAB,
                Items.LIGHT_BLUE_STAINED_GLASS_SLAB,
                Items.LIGHT_GRAY_STAINED_GLASS_SLAB,
                Items.LIME_STAINED_GLASS_SLAB,
                Items.MAGENTA_STAINED_GLASS_SLAB,
                Items.ORANGE_STAINED_GLASS_SLAB,
                Items.PINK_STAINED_GLASS_SLAB,
                Items.PURPLE_STAINED_GLASS_SLAB,
                Items.RED_STAINED_GLASS_SLAB,
                Items.WHITE_STAINED_GLASS_SLAB,
                Items.YELLOW_STAINED_GLASS_SLAB,
                Items.BLACK_STAINED_GLASS_SLAB_VERTICAL,
                Items.BLUE_STAINED_GLASS_SLAB_VERTICAL,
                Items.BROWN_STAINED_GLASS_SLAB_VERTICAL,
                Items.CYAN_STAINED_GLASS_SLAB_VERTICAL,
                Items.GRAY_STAINED_GLASS_SLAB_VERTICAL,
                Items.GREEN_STAINED_GLASS_SLAB_VERTICAL,
                Items.LIGHT_BLUE_STAINED_GLASS_SLAB_VERTICAL,
                Items.LIGHT_GRAY_STAINED_GLASS_SLAB_VERTICAL,
                Items.LIME_STAINED_GLASS_SLAB_VERTICAL,
                Items.MAGENTA_STAINED_GLASS_SLAB_VERTICAL,
                Items.ORANGE_STAINED_GLASS_SLAB_VERTICAL,
                Items.PINK_STAINED_GLASS_SLAB_VERTICAL,
                Items.PURPLE_STAINED_GLASS_SLAB_VERTICAL,
                Items.RED_STAINED_GLASS_SLAB_VERTICAL,
                Items.WHITE_STAINED_GLASS_SLAB_VERTICAL,
                Items.YELLOW_STAINED_GLASS_SLAB_VERTICAL,
                Items.BLACK_STAINED_GLASS_QUARTER,
                Items.BLUE_STAINED_GLASS_QUARTER,
                Items.BROWN_STAINED_GLASS_QUARTER,
                Items.CYAN_STAINED_GLASS_QUARTER,
                Items.GRAY_STAINED_GLASS_QUARTER,
                Items.GREEN_STAINED_GLASS_QUARTER,
                Items.LIGHT_BLUE_STAINED_GLASS_QUARTER,
                Items.LIGHT_GRAY_STAINED_GLASS_QUARTER,
                Items.LIME_STAINED_GLASS_QUARTER,
                Items.MAGENTA_STAINED_GLASS_QUARTER,
                Items.ORANGE_STAINED_GLASS_QUARTER,
                Items.PINK_STAINED_GLASS_QUARTER,
                Items.PURPLE_STAINED_GLASS_QUARTER,
                Items.RED_STAINED_GLASS_QUARTER,
                Items.WHITE_STAINED_GLASS_QUARTER,
                Items.YELLOW_STAINED_GLASS_QUARTER,
                Items.BLACK_STAINED_GLASS_QUARTER_VERTICAL,
                Items.BLUE_STAINED_GLASS_QUARTER_VERTICAL,
                Items.BROWN_STAINED_GLASS_QUARTER_VERTICAL,
                Items.CYAN_STAINED_GLASS_QUARTER_VERTICAL,
                Items.GRAY_STAINED_GLASS_QUARTER_VERTICAL,
                Items.GREEN_STAINED_GLASS_QUARTER_VERTICAL,
                Items.LIGHT_BLUE_STAINED_GLASS_QUARTER_VERTICAL,
                Items.LIGHT_GRAY_STAINED_GLASS_QUARTER_VERTICAL,
                Items.LIME_STAINED_GLASS_QUARTER_VERTICAL,
                Items.MAGENTA_STAINED_GLASS_QUARTER_VERTICAL,
                Items.ORANGE_STAINED_GLASS_QUARTER_VERTICAL,
                Items.PINK_STAINED_GLASS_QUARTER_VERTICAL,
                Items.PURPLE_STAINED_GLASS_QUARTER_VERTICAL,
                Items.RED_STAINED_GLASS_QUARTER_VERTICAL,
                Items.WHITE_STAINED_GLASS_QUARTER_VERTICAL,
                Items.YELLOW_STAINED_GLASS_QUARTER_VERTICAL,
                Items.BLACK_STAINED_GLASS_EIGHTH,
                Items.BLUE_STAINED_GLASS_EIGHTH,
                Items.BROWN_STAINED_GLASS_EIGHTH,
                Items.CYAN_STAINED_GLASS_EIGHTH,
                Items.GRAY_STAINED_GLASS_EIGHTH,
                Items.GREEN_STAINED_GLASS_EIGHTH,
                Items.LIGHT_BLUE_STAINED_GLASS_EIGHTH,
                Items.LIGHT_GRAY_STAINED_GLASS_EIGHTH,
                Items.LIME_STAINED_GLASS_EIGHTH,
                Items.MAGENTA_STAINED_GLASS_EIGHTH,
                Items.ORANGE_STAINED_GLASS_EIGHTH,
                Items.PINK_STAINED_GLASS_EIGHTH,
                Items.PURPLE_STAINED_GLASS_EIGHTH,
                Items.RED_STAINED_GLASS_EIGHTH,
                Items.WHITE_STAINED_GLASS_EIGHTH,
                Items.YELLOW_STAINED_GLASS_EIGHTH
        );
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getSpruceColor(),
                Blocks.SPRUCE_LEAVES_STAIRS, Blocks.SPRUCE_LEAVES_STAIRS_VERTICAL, Blocks.SPRUCE_LEAVES_SLAB, Blocks.SPRUCE_LEAVES_SLAB_VERTICAL, Blocks.SPRUCE_LEAVES_QUARTER, Blocks.SPRUCE_LEAVES_QUARTER_VERTICAL, Blocks.SPRUCE_LEAVES_EIGHTH
        );
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getBirchColor(),
                Blocks.BIRCH_LEAVES_STAIRS, Blocks.BIRCH_LEAVES_STAIRS_VERTICAL, Blocks.BIRCH_LEAVES_SLAB, Blocks.BIRCH_LEAVES_SLAB_VERTICAL, Blocks.BIRCH_LEAVES_QUARTER, Blocks.BIRCH_LEAVES_QUARTER_VERTICAL, Blocks.BIRCH_LEAVES_EIGHTH
        );
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(),
                Blocks.OAK_LEAVES_STAIRS, Blocks.OAK_LEAVES_STAIRS_VERTICAL, Blocks.OAK_LEAVES_SLAB, Blocks.OAK_LEAVES_SLAB_VERTICAL, Blocks.OAK_LEAVES_QUARTER, Blocks.OAK_LEAVES_QUARTER_VERTICAL, Blocks.OAK_LEAVES_EIGHTH,
                Blocks.JUNGLE_LEAVES_STAIRS, Blocks.JUNGLE_LEAVES_STAIRS_VERTICAL, Blocks.JUNGLE_LEAVES_SLAB, Blocks.JUNGLE_LEAVES_SLAB_VERTICAL, Blocks.JUNGLE_LEAVES_QUARTER, Blocks.JUNGLE_LEAVES_QUARTER_VERTICAL, Blocks.JUNGLE_LEAVES_EIGHTH,
                Blocks.ACACIA_LEAVES_STAIRS, Blocks.ACACIA_LEAVES_STAIRS_VERTICAL, Blocks.ACACIA_LEAVES_SLAB, Blocks.ACACIA_LEAVES_SLAB_VERTICAL, Blocks.ACACIA_LEAVES_QUARTER, Blocks.ACACIA_LEAVES_QUARTER_VERTICAL, Blocks.ACACIA_LEAVES_EIGHTH,
                Blocks.DARK_OAK_LEAVES_STAIRS, Blocks.DARK_OAK_LEAVES_STAIRS_VERTICAL, Blocks.DARK_OAK_LEAVES_SLAB, Blocks.DARK_OAK_LEAVES_SLAB_VERTICAL, Blocks.DARK_OAK_LEAVES_QUARTER, Blocks.DARK_OAK_LEAVES_QUARTER_VERTICAL, Blocks.DARK_OAK_LEAVES_EIGHTH
        );
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                Blocks.SPRUCE_LADDER,
                Blocks.BIRCH_LADDER,
                Blocks.JUNGLE_LADDER,
                Blocks.ACACIA_LADDER,
                Blocks.DARK_OAK_LADDER,
                Blocks.CRIMSON_LADDER,
                Blocks.WARPED_LADDER
        );

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) ->
        {
            if (tintIndex == 1)
                return view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : GrassColors.getColor(0.5D, 1.0D);

            return -1;
        }, Blocks.GRASS_BLOCK_SLAB, Blocks.DIRT_SLAB, Blocks.COARSE_DIRT_SLAB, Blocks.PODZOL_SLAB);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D), Blocks.GRASS_BLOCK_SLAB, Blocks.DIRT_SLAB, Blocks.COARSE_DIRT_SLAB, Blocks.PODZOL_SLAB);

        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.GRASS_BLOCK_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.DIRT_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.COARSE_DIRT_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.PODZOL_SLAB, RenderLayer.getCutoutMipped());

        if (DebugClient)
        {
            ConsoleClient.info("[" + NameClient + "] BlockRenderLayerMap & ColorProviderRegistry : OK !");
        }

        ConsoleClient.info("[" + NameClient + "] All is well! Good game with MineGate!");
    }

    public static boolean hasTopSlab(BlockState state)
    {
        Block block = state.getBlock();
        return (block instanceof SlabPlantableBlock) && (state.get(SlabPlantableBlock.TYPE) == SlabType.TOP || state.get(SlabPlantableBlock.TYPE) == SlabType.BOTTOM || state.get(SlabPlantableBlock.TYPE) == SlabType.DOUBLE);
    }

    public static boolean isDirtType(Block block)
    {
        return block == Blocks.COARSE_DIRT_SLAB || block == Blocks.DIRT_SLAB || block == Blocks.FARMLAND_SLAB || block == Blocks.PODZOL_SLAB;
    }

    public static boolean isGrassType(Block block)
    {
        return block == Blocks.GRASS_BLOCK_SLAB || block == Blocks.MYCELIUM_SLAB || isDirtType(block);
    }

    public static void dirtParticles(World world, BlockPos pos, int count)
    {
        if (!world.isClient)
            ((ServerWorld) world).spawnParticles(ParticleTypes.MYCELIUM, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, MathHelper.nextInt(world.random, 1, count), 0.25, 0.02, 0.25, 0.1);
    }

    public static void waterParticles(World world, BlockPos pos, int count)
    {
        if (!world.isClient)
            ((ServerWorld) world).spawnParticles(ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, MathHelper.nextInt(world.random, 1, count), 0.25, 0.02, 0.25, 0.1);
    }

    public static void happyParticles(World world, BlockPos pos, int count)
    {
        if (!world.isClient)
            ((ServerWorld) world).spawnParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, MathHelper.nextInt(world.random, 1, count), 0.25, 0.02, 0.25, 0.1);
    }

    public static void sadParticles(World world, BlockPos pos, int count)
    {
        if (!world.isClient)
            ((ServerWorld) world).spawnParticles(ParticleTypes.ANGRY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, MathHelper.nextInt(world.random, 1, count), 0.25, 0.02, 0.25, 0.1);
    }

    public static void setToDirt(World world, BlockPos pos)
    {
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof SlabBlock)
            world.setBlockState(pos, Blocks.DIRT_SLAB.getDefaultState().with(SlabBlock.TYPE, state.get(SlabBlock.TYPE)).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED)));

        else world.setBlockState(pos, net.minecraft.block.Blocks.DIRT.getDefaultState());

        dirtParticles(world, pos, 3);
    }

    public static void spreadableTick(BlockState spreader, ServerWorld world, BlockPos pos, Random random)
    {
        if (SlabSpreadableBlock.canSurvive(spreader, world, pos) && world.getLightLevel(pos.up()) >= 9)
        {
            for (int x = 0; x < 4; ++x)
            {
                BlockPos randBlockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                BlockState spreadee = world.getBlockState(randBlockPos);

                if (SlabSpreadableBlock.canSpread(spreader, world, randBlockPos))
                {
                    if (spreader.getBlock() == Blocks.GRASS_BLOCK_SLAB && spreadee.getBlock() == net.minecraft.block.Blocks.DIRT)
                    {
                        world.setBlockState(randBlockPos, net.minecraft.block.Blocks.GRASS_BLOCK.getDefaultState());
                    }

                    else if (spreader.getBlock() == Blocks.MYCELIUM_SLAB && spreadee.getBlock() == net.minecraft.block.Blocks.DIRT)
                    {
                        world.setBlockState(randBlockPos, net.minecraft.block.Blocks.MYCELIUM.getDefaultState());
                    }

                    else if ((spreader.getBlock() == net.minecraft.block.Blocks.GRASS_BLOCK || spreader.getBlock() == Blocks.GRASS_BLOCK_SLAB) && spreadee.getBlock() == Blocks.DIRT_SLAB)
                    {
                        world.setBlockState(randBlockPos, Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(SlabBlock.TYPE, spreadee.get(SlabBlock.TYPE)).with(SlabBlock.WATERLOGGED, spreadee.get(SlabBlock.WATERLOGGED)));
                    }

                    else if ((spreader.getBlock() == net.minecraft.block.Blocks.MYCELIUM || spreader.getBlock() == Blocks.MYCELIUM_SLAB) && spreadee.getBlock() == Blocks.DIRT_SLAB)
                    {
                        world.setBlockState(randBlockPos, Blocks.MYCELIUM_SLAB.getDefaultState().with(SlabBlock.TYPE, spreadee.get(SlabBlock.TYPE)).with(SlabBlock.WATERLOGGED, spreadee.get(SlabBlock.WATERLOGGED)));
                    }
                }
            }
        }
    }

    // Render of SitEntity.

    private static class EmptyRenderer extends EntityRenderer<SitEntity>
    {
        protected EmptyRenderer(EntityRenderDispatcher entityRenderDispatcher)
        {
            super(entityRenderDispatcher);
        }

        @Override
        public boolean shouldRender(SitEntity entity, Frustum frustum, double d, double e, double f)
        {
            return false;
        }

        @Override
        public Identifier getTexture(SitEntity entity)
        {
            return null;
        }
    }
}