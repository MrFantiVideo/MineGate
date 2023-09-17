package fr.minegate.block;

import fr.minegate.block.entity.PaintedBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PaintedBlock extends BlockWithEntity
{
    /**
     * Creation of a painted block.
     *
     * @param settings Block settings.
     */

    public PaintedBlock(AbstractBlock.Settings settings)
    {
        super(settings);
    }

    /**
     * Create a painting entity from the block.
     */

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new PaintedBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
    {
        PaintedBlockEntity.setColorBlockAt(world, pos, itemStack);
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
    {
        ItemStack stack = new ItemStack(this);
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtList colorsNbt = nbt.getList("Colors", NbtElement.COMPOUND_TYPE);
        int colors = PaintedBlockEntity.getColorLayersCountAt((World) world, pos);
        for (int tintIndex = 0; tintIndex < colors; tintIndex++)
        {
            NbtCompound colorNbt = new NbtCompound();
            int color = PaintedBlockEntity.getColorBlockAt((World) world, pos, tintIndex);
            colorNbt.putInt(String.valueOf(tintIndex), color);
            colorsNbt.add(colorNbt);

        }
        nbt.put("Colors", colorsNbt);
        stack.setNbt(nbt);
        return stack;
    }

    @Override
    public String getTranslationKey()
    {
        Item item = Item.BLOCK_ITEMS.get(this);
        if (item instanceof BlockItem)
        {
            BlockItem blockItem = (BlockItem) item;
            Identifier itemIdentifier = Registries.ITEM.getId(blockItem);
            if (itemIdentifier != null)
            {
                String itemName = itemIdentifier.getPath();
                String transformedName = itemName.replace("painted_", "minecraft.");
                String translationKey = "block." + transformedName;
                String translatedName = I18n.translate(translationKey) + " " + I18n.translate("block.minegate.painted");

                return translatedName;
            }
        }
        return super.getTranslationKey();
    }
}
