package fr.minegate.client.gui.screen.ingame;

import fr.minegate.MineGate;
import fr.minegate.screen.PaintingPaletteScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.apache.commons.lang3.StringUtils;

@Environment(EnvType.CLIENT)
public class PaintingPaletteScreen extends HandledScreen<PaintingPaletteScreenHandler>
{
    private static final Identifier      TEXTURE = new Identifier(MineGate.name.toLowerCase(), "textures/gui/container/painting_palette.png");
    private        String          previousText;
    private static TextFieldWidget nameField;

    public PaintingPaletteScreen(PaintingPaletteScreenHandler handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, title);
        this.backgroundHeight = 133;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    public static String getColor()
    {
        return nameField.getText();
    }

    @Override
    public void init()
    {
        super.init();
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.nameField = new TextFieldWidget(this.textRenderer, i + 70, j + 25, 45, 12, Text.translatable("container.painting_palette.color"));
        this.nameField.setFocusUnlocked(true);
        this.nameField.setFocused(true);
        this.nameField.setDrawsBackground(false);
        this.nameField.setEditableColor(0xFFFFFF);
        this.nameField.setEditable(true);
        this.nameField.setMaxLength(6);
        this.nameField.setChangedListener(this::checkHexInput);
        this.nameField.setText("");
        this.addSelectableChild(this.nameField);
        this.setInitialFocus(this.nameField);
    }

    private void checkHexInput(String newText)
    {
        String validText = StringUtils.stripStart(newText, "0123456789abcdefABCDEF");
        if (!validText.isEmpty())
        {
            this.nameField.setText((previousText != null ? previousText : ""));
        }
        else
        {
            this.previousText = this.nameField.getText();
        }
        MineGate.log("checkHexInput in Screen : " + nameField.getText());
        this.handler.sendContentUpdates();
    }

    public void handledScreenTick()
    {
        super.handledScreenTick();
        this.nameField.tick();
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.nameField.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    public void resize(MinecraftClient client, int width, int height)
    {
        String string = this.nameField.getText();
        this.init(client, width, height);
        this.nameField.setText(string);
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY)
    {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}