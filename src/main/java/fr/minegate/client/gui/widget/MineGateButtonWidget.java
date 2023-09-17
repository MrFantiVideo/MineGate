package fr.minegate.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.minegate.MineGate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class MineGateButtonWidget extends ButtonWidget
{
    private static final Identifier      MINEGATE_TEXTURE;

    protected MineGateButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier)
    {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    protected MutableText getNarrationMessage()
    {
        return Text.literal("Maneguette");
    }

    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta)
    {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, MINEGATE_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        IconLocation iconLocation;
        if (!this.active)
        {
            iconLocation = MineGateButtonWidget.IconLocation.BUTTON_DISABLED;
        }
        else if (this.isHovered())
        {
            iconLocation = MineGateButtonWidget.IconLocation.BUTTON_HOVER;
        }
        else
        {
            iconLocation = MineGateButtonWidget.IconLocation.BUTTON;
        }
        int i = this.active ? 16777215 : 10526880;
        this.drawMessage(context, minecraftClient.textRenderer, i | MathHelper.ceil(this.alpha * 255.0F) << 24);

        if (this.isHovered())
        {
            this.narrationSupplier.createNarrationMessage((Supplier<MutableText>) Text.literal("test"));
        }
    }

    @Environment(EnvType.CLIENT)
    static enum IconLocation
    {
        BUTTON(0, 0),
        BUTTON_HOVER(0, 20),
        BUTTON_DISABLED(0, 40);

        private final int u;
        private final int v;

        private IconLocation(int u, int v)
        {
            this.u = u;
            this.v = v;
        }

        public int getU()
        {
            return this.u;
        }

        public int getV()
        {
            return this.v;
        }
    }

    static
    {
        MINEGATE_TEXTURE = new Identifier(MineGate.name.toLowerCase(), "textures/gui/minegate.png");
    }
}
