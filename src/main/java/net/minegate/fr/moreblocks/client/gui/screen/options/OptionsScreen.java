package net.minegate.fr.moreblocks.client.gui.screen.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class OptionsScreen extends Screen
{
    private final Screen           parent;
    private       ButtonListWidget buttonList;

    public OptionsScreen(Screen parent)
    {
        super(new TranslatableText("options.minegate.title"));
        this.parent = parent;
    }

    protected void init()
    {
        this.buttonList = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        Text generationOres = new TranslatableText("options.minegate.generation.ores." + DefaultConfig.get("generationOres"));
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 6, 200, 20, generationOres, (button) ->
        {
            if (DefaultConfig.get("generationOres"))
            {
                button.setMessage(new TranslatableText("options.minegate.generation.ores.false"));
                DefaultConfig.replace("generationOres", false);
            }
            else
            {
                button.setMessage(new TranslatableText("options.minegate.generation.ores.true"));
                DefaultConfig.replace("generationOres", true);
            }
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (button) ->
        {
            this.client.options.write();
            this.client.getWindow().applyVideoMode();
            this.client.openScreen(this.parent);
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        this.renderBackground(matrices);
        this.buttonList.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 14, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
        for (AbstractButtonWidget button : buttons)
        {
            if (!button.isHovered())
                continue;
            Text generationOres = new TranslatableText("options.minegate.generation.ores." + DefaultConfig.get("generationOres"));
            if (!button.getMessage().equals(generationOres))
                continue;

            List<Text> generationOresTooltip = Arrays.asList(new TranslatableText("options.minegate.generation.ores.tooltip.one"), new TranslatableText("options.minegate.generation.ores.tooltip.two"));
            renderTooltip(matrices, generationOresTooltip, mouseX, mouseY);
            break;
        }
    }
}
