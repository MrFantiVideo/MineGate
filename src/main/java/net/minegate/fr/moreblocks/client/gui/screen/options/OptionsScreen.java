package net.minegate.fr.moreblocks.client.gui.screen.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minegate.fr.moreblocks.MoreBlocks;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class OptionsScreen extends Screen
{
    private final        Screen           parent;
    private              ButtonListWidget buttonBackground;
    private              ButtonWidget     buttonGenerationOres;
    private              ButtonWidget     buttonDebugMode;
    private              ButtonWidget     buttonSizeChange;
    private static final Identifier       SOCIAL_TEXTURE   = new Identifier(MoreBlocks.NameClient.toLowerCase(), "textures/gui/social.png");
    private static final Identifier       MINEGATE_TEXTURE = new Identifier(MoreBlocks.NameClient.toLowerCase(), "textures/gui/minegate.png");

    public OptionsScreen(Screen parent)
    {
        super(Text.translatable("options.minegate.title"));
        this.parent = parent;
    }

    protected void init()
    {
        buttonBackground = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        buttonGenerationOres = button("generationOres", 0);
        buttonDebugMode = button("debugMode", 24);
        buttonSizeChange = button("sizeChange", 48);
        addDrawableChild(new TexturedButtonWidget(this.width / 2 - 100, this.height / 6 + 72, 100, 20, 0, 0, 20, MINEGATE_TEXTURE, 100, 40, (buttonWidget) ->
        {
            client.setScreen(new ConfirmLinkScreen((bl) ->
            {
                if (bl)
                {
                    Util.getOperatingSystem().open("https://minegate.fr");
                }
                client.setScreen(this);
            }, "https://minegate.fr", true));
        }));
        buttonLink("https://youtube.com/user/FantiVideo654", 0, 0);
        buttonLink("https://facebook.com/MineGate", 25, 40);
        buttonLink("https://twitter.com/MineGateFR", 50, 80);
        buttonLink("https://minegate.fr/discord", 75, 120);
        addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (button) ->
        {
            client.options.write();
            client.getWindow().applyVideoMode();
            client.setScreen(this.parent);
        }));
    }

    public ButtonWidget button(String name, int placement)
    {
        Text buttonName = Text.translatable("options.minegate." + name + "." + DefaultConfig.get(name));
        return addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + placement, 200, 20, buttonName, (button) ->
        {
            if (DefaultConfig.get(name))
            {
                button.setMessage(Text.translatable("options.minegate." + name + ".false"));
                DefaultConfig.replace(name, false);
            }
            else
            {
                button.setMessage(Text.translatable("options.minegate." + name + ".true"));
                DefaultConfig.replace(name, true);
            }
        }));
    }

    public ButtonWidget buttonLink(String url, int placement, int v)
    {
        return addDrawableChild(new TexturedButtonWidget(this.width / 2 + 5 + placement, this.height / 6 + 72, 20, 20, 0, v, 20, SOCIAL_TEXTURE, 20, 160, (buttonWidget) ->
        {
            client.setScreen(new ConfirmLinkScreen((bl) ->
            {
                if (bl)
                {
                    Util.getOperatingSystem().open(url);
                }
                client.setScreen(this);
            }, url, true));
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        renderBackground(matrices);
        buttonBackground.render(matrices, mouseX, mouseY, delta);
        buttonGenerationOres.render(matrices, mouseX, mouseY, delta);
        buttonDebugMode.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 14, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
        for (int b = 0; b < 1; b = b + 1)
        {
            if (buttonGenerationOres.isHovered() || buttonSizeChange.isHovered())
            {
                List<Text> generationOresTooltip = Arrays.asList(Text.translatable("options.minegate.warning.tooltip.one"), Text.translatable("options.minegate.warning.tooltip.two"));
                renderTooltip(matrices, generationOresTooltip, mouseX, mouseY);
                break;
            }
        }
    }
}
