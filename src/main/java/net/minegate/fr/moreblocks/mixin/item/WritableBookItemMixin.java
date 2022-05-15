package net.minegate.fr.moreblocks.mixin.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.WritableBookItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WritableBookItem.class)
public class WritableBookItemMixin
{
    /**
     * Allows color books to be stored in the Bookshelf Block.
     **/

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir)
    {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof LecternBlock)
        {
            cir.setReturnValue(LecternBlock.putBookIfAbsent(context.getPlayer(), world, blockPos, blockState, context.getStack()) ? ActionResult.success(world.isClient) : ActionResult.PASS);
        }
    }
}