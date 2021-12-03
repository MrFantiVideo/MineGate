package net.minegate.fr.moreblocks.client.gui.screen.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.LockButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class OptionsScreen extends Screen
{
    private final Screen           parent;
    private       ButtonListWidget buttonBackground;
    private       ButtonWidget buttonGenerationOres;
    private       ButtonWidget     buttonDebugMode;
    private       ButtonWidget     buttonUseMixins;

    public OptionsScreen(Screen parent)
    {
        super(new TranslatableText("options.minegate.title"));
        this.parent = parent;
    }

    protected void init()
    {
        buttonBackground = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        buttonGenerationOres = button("generationOres", 0);
        buttonDebugMode = button("debugMode", 24);
        buttonUseMixins = button("useMixins", 48);
        addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (button) ->
        {
            client.options.write();
            client.getWindow().applyVideoMode();
            client.setScreen(this.parent);
        }));
    }

    public ButtonWidget button(String name, int placement)
    {
        Text buttonName = new TranslatableText("options.minegate." + name + "." + DefaultConfig.get(name));
        if (placement == 0)
        {
            buttonName = new TranslatableText("options.minegate." + name + ".false");
        }
        return addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + placement, 200, 20, buttonName, (button) ->
        {
            if (DefaultConfig.get(name))
            {
                DefaultConfig.replace(name, false);
            }
            else
            {
                DefaultConfig.replace(name, true);
            }
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        renderBackground(matrices);
        buttonBackground.render(matrices, mouseX, mouseY, delta);
        buttonGenerationOres.render(matrices, mouseX, mouseY, delta);
        buttonGenerationOres.active = false;
        buttonDebugMode.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 14, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
        for (int b = 0; b < 1; b = b + 1)
        {
            if (buttonGenerationOres.isHovered())
            {
                List<Text> generationOresTooltip = Arrays.asList(new TranslatableText("options.minegate.warning.tooltip.one"), new TranslatableText("options.minegate.warning.tooltip.two"));
                renderTooltip(matrices, generationOresTooltip, mouseX, mouseY);
                break;
            }
            if (buttonUseMixins.isHovered())
            {
                List<Text> useMixinsTooltip = Arrays.asList(new TranslatableText("options.minegate.useMixins.tooltip.one"), new TranslatableText("options.minegate.useMixins.tooltip.two"), new TranslatableText(""), new TranslatableText("options.minegate.useMixins.tooltip.three"), new TranslatableText("options.minegate.useMixins.tooltip.four"), new TranslatableText("options.minegate.useMixins.tooltip.five"), new TranslatableText("options.minegate.useMixins.tooltip.six"), new TranslatableText(""), new TranslatableText("options.minegate.warning.tooltip.one"), new TranslatableText("options.minegate.warning.tooltip.two"));
                renderTooltip(matrices, useMixinsTooltip, mouseX, mouseY);
                break;
            }
        }
    }
}
