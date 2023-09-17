package fr.minegate.client.gui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TitleDevHud
{
    public static void render(DrawContext context, MinecraftClient client, int scaledWidth, int scaledHeight)
    {
        String NAME = "MineGate Dev";
        String DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        int sizeName = client.textRenderer.getWidth(NAME);
        int sizeDate = client.textRenderer.getWidth(DATE);
        context.drawTextWithShadow(client.textRenderer, NAME, (scaledWidth - sizeName - 4), (scaledHeight - 7 - 15), 4080255);
        context.drawTextWithShadow(client.textRenderer, DATE, (scaledWidth - sizeDate - 4), (scaledHeight - 7 - 5), 16777215);
        client.getProfiler().pop();
    }
}
