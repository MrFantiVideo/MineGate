package net.minegate.fr.moreblocks.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.BookshelfBlock;
import net.minegate.fr.moreblocks.block.enums.ColorsType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LecternBlock.class)
public class LecternBlockMixin
{
    /**
     * Allows you to visually empty the library.
     **/

    @Inject(method = "setHasBook", at = @At("RETURN"))
    private static void setHasBook(World world, BlockPos pos, BlockState state, boolean hasBook, CallbackInfo ci)
    {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof LecternBlockEntity lecternBlockEntity)
        {
            if (state.getBlock() instanceof BookshelfBlock)
            {
                if (!hasBook)
                {
                    world.setBlockState(pos, state.with(LecternBlock.POWERED, false).with(LecternBlock.HAS_BOOK, Boolean.FALSE).with(BookshelfBlock.TYPE, ColorsType.EMPTY));
                }
                else
                {
                    String bookType = lecternBlockEntity.getBook().getItem().toString().replace("written_", "").replace("writable_", "").replace("_book", "").toUpperCase();
                    if (lecternBlockEntity.getBook().getItem() == net.minecraft.item.Items.BOOK || lecternBlockEntity.getBook().getItem() == net.minecraft.item.Items.WRITABLE_BOOK || lecternBlockEntity.getBook().getItem() == net.minecraft.item.Items.WRITTEN_BOOK)
                    {
                        bookType = "DEFAULT";
                    }
                    world.setBlockState(pos, state.with(LecternBlock.POWERED, true).with(LecternBlock.HAS_BOOK, Boolean.TRUE).with(BookshelfBlock.TYPE, ColorsType.valueOf(bookType)));
                }
            }
        }
    }
}