package fr.minegate.mixin.client.gui.screen.world;

import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectWorldScreen.class)
public class SelectWorldScreenMixin extends Screen
{
    private              ServerInfo                  selectedEntry;
    private static final Text                        INFO_IP;

    protected SelectWorldScreenMixin(Text title)
    {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "init")
    protected void init(CallbackInfo ci)
    {
        this.selectedEntry = new ServerInfo(I18n.translate("selectServer.defaultName"), "", false);
    }

    private void directConnect(boolean confirmedAction)
    {
        if (confirmedAction)
        {
            this.connect(this.selectedEntry);
        }
        else
        {
            this.client.setScreen(this);
        }
    }

    private void connect(ServerInfo entry)
    {
        ConnectScreen.connect(this, this.client, ServerAddress.parse(entry.address), entry, true);
    }

    static
    {
        INFO_IP = Text.translatable("menu.minegate.ip.tooltip").formatted(Formatting.GRAY);
    }
}
