package fr.minegate.mixin.client.gui.screen;

import fr.minegate.client.gui.screen.CustomSplashText;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.Color;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

@Mixin(SplashTextRenderer.class)
@Environment(EnvType.CLIENT)
public class SplashTextRendererMixin
{
    /**
     * Calculates the new size of the text if there is an event.
     */

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;getWidth(Ljava/lang/String;)I"))
    public int render(TextRenderer instance, String text)
    {
        return instance.getWidth(checkEvent(text, -1).getText() + 32);
    }

    /**
     * Changes the text as well as the color of the text if there is an event.
     */

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"))
    public void render(DrawContext instance, TextRenderer textRenderer, String text, int centerX, int y, int color)
    {
        CustomSplashText event = checkEvent(text, color);
        instance.drawCenteredTextWithShadow(textRenderer, event.getText(), 0, -8, event.getColor());
    }

    /**
     * Check the events present.
     */

    @Unique
    CustomSplashText checkEvent(String text, Integer color)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(Calendar.MONTH) + 1 == 3 && calendar.get(Calendar.DATE) == 11)
        {
            text = MessageFormat.format(I18n.translate("menu.minegate.splashtext.happybirthday"), "MrFantiVideo");
            color = Color.HSBtoRGB((System.currentTimeMillis() % 5000L) / 5000f, 0.8f, 0.8f); // May cause problems on some operating systems. (java.awt.Color)
        }
        
        return new CustomSplashText(text, color);
    }
}
