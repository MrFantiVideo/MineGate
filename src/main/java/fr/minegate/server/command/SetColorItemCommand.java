package fr.minegate.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.minegate.block.entity.PaintedBlockEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.text.MessageFormat;

public class SetColorItemCommand
{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("dev")
                .then(CommandManager.literal("setColorItem")
                        .then(CommandManager.argument("layer", IntegerArgumentType.integer(0, 15))
                                .then(CommandManager.argument("color", IntegerArgumentType.integer())
                                        .executes(SetColorItemCommand::setPaintedColorItem))
                        )
                )
        );
    }

    private static int setPaintedColorItem(CommandContext<ServerCommandSource> context)
    {
        ServerCommandSource source = context.getSource();
        int layer = IntegerArgumentType.getInteger(context, "layer");
        int color = IntegerArgumentType.getInteger(context, "color");

        ItemStack heldItem = source.getPlayer().getMainHandStack();
        PaintedBlockEntity.setColorItem(heldItem, layer, color);

        source.sendFeedback(() -> Text.literal(MessageFormat.format(I18n.translate("command.minegate.set.color.item"), color)).styled(style -> style.withColor(color)), false);
        return 1;
    }
}
