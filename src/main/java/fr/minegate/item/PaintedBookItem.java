package fr.minegate.item;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.minegate.MineGate;
import fr.minegate.block.entity.PaintedBlockEntity;
import fr.minegate.client.color.item.ItemColors;
import fr.minegate.util.PaintColor;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

public class PaintedBookItem extends BookItem
{
    public PaintedBookItem(Settings settings)
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

    private static ItemStack itemScreen;

    /**
     * System to set the color of the books in the Lectern.
     **/

    public static void setBookScreen(ItemStack item)
    {
        itemScreen = item;
    }

    /**
     * System to get the color of the books in the Lectern.
     **/

    public static ItemStack getBookScreen()
    {
        return itemScreen;
    }

    public static void setColorBookScreen(DrawContext context)
    {
        ItemStack stack = PaintedBookItem.getBookScreen();
        int colorDecimal = PaintedBlockEntity.getColorItem(stack, 0);

        float[] colorComponents = PaintColor.decimalToRGBA(colorDecimal);

        if (colorComponents != null)
        {
            float red = colorComponents[0];
            float green = colorComponents[1];
            float blue = colorComponents[2];
            float alpha = colorComponents[3];

            int i = (context.getScaledWindowWidth() - 192) / 2;

            RenderSystem.setShaderColor(red, green, blue, alpha);
            context.drawTexture(new Identifier(MineGate.name.toLowerCase(), "textures/gui/painted_book.png"), i, 2, 0, 0, 192, 192);
        }
    }
}
