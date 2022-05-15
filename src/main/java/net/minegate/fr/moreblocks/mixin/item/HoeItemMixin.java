package net.minegate.fr.moreblocks.mixin.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.block.PlantableSlabBlock;
import net.minegate.fr.moreblocks.block.SnowySlabBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public class HoeItemMixin
{
    /**
     * Transforms dirt slabs into farmland slabs.
     **/

    @Inject(at = @At("RETURN"), method = "useOnBlock", cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir)
    {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState state = world.getBlockState(blockPos);
        Block block = state.getBlock();
        Block newBlock = Blocks.FARMLAND_SLAB;

        if (block instanceof PlantableSlabBlock || block instanceof SnowySlabBlock)
        {
            PlayerEntity playerEntity = context.getPlayer();
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(blockPos, newBlock.getStateWithProperties(state));
            if (!world.isClient)
            {
                if (playerEntity != null)
                {
                    context.getStack().damage(1, playerEntity, (p) ->
                    {
                        p.sendToolBreakStatus(context.getHand());
                    });
                }
            }
            cir.setReturnValue(ActionResult.success(world.isClient));
        }
        else
        {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}