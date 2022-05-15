package net.minegate.fr.moreblocks.client.gui.screen.options.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minegate.fr.moreblocks.client.gui.screen.options.OptionsScreen;

@Environment(EnvType.CLIENT)
public class ModMenuEntry implements ModMenuApi
{
    /**
     * Allows ModMenu mod to open the settings menu.
     **/

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return OptionsScreen::new;
    }
}