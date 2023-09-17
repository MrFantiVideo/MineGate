package fr.minegate.item;

import fr.minegate.block.entity.PaintedBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WritableBookItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class PaintedWritableBookItem extends WritableBookItem
{
    public PaintedWritableBookItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        PaintedBlockEntity.addColorTooltip(stack, tooltip);
        List<Integer> colors = PaintedBlockEntity.getColors(stack);
        PaintedBlockEntity.addColorToNBT(stack, colors);
    }
}
