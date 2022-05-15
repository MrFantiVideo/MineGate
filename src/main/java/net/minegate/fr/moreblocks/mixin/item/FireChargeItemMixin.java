package net.minegate.fr.moreblocks.mixin.item;

import net.minecraft.block.BlockState;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minegate.fr.moreblocks.block.LanternBlock;
import net.minegate.fr.moreblocks.block.enums.ColorsType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(FireChargeItem.class)
public class FireChargeItemMixin
{
    private static final EnumProperty<ColorsType> TYPE;
    private static final BooleanProperty          LIT;

    /**
     * Allows you to light the block lanterns with a fire charge.
     **/

    @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir)
    {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.getBlock() instanceof LanternBlock && (blockState != blockState.with(TYPE, ColorsType.EMPTY)) && !blockState.get(LIT))
        {
            Random random = world.getRandom();
            world.playSound(null, blockPos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            world.setBlockState(blockPos, blockState.with(Properties.LIT, true));
            world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_PLACE, blockPos);
            context.getStack().decrement(1);
            cir.setReturnValue(ActionResult.success(world.isClient()));
        }
    }

    static
    {
        LIT = Properties.LIT;
        TYPE = net.minegate.fr.moreblocks.state.Properties.COLORS_TYPE;
    }
}