package fr.minegate.item;

import fr.minegate.block.PaintedBlock;
import fr.minegate.block.PaintedBlocks;
import fr.minegate.block.entity.PaintedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.List;

public class PaintBrushItem extends Item
{
    public PaintBrushItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();

        if (PaintedBlocks.get(state.getBlock()) instanceof PaintedBlock)
        {
            Block oldBLock = PaintedBlocks.get(state.getBlock());
            BlockState painted = oldBLock.getStateWithProperties(state);
            world.setBlockState(pos, painted);
        }
        if ((state.getBlock() instanceof PaintedBlock) || PaintedBlocks.get(state.getBlock()) instanceof PaintedBlock)
        {
            int colorItem = PaintedBlockEntity.getColorItem(itemStack, 0);
            int colorBlock = PaintedBlockEntity.getColorBlockAt(world, pos, 0);
            if (colorItem != colorBlock)
            {
                PaintedBlockEntity.setColorBlockAt(world, pos, itemStack);
                world.playSound(playerEntity, pos, SoundEvents.ITEM_BRUSH_BRUSHING_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);

                float red = (float) ((colorItem >> 16) & 0xFF) / 255.0F;
                float green = (float) ((colorItem >> 8) & 0xFF) / 255.0F;
                float blue = (float) (colorItem & 0xFF) / 255.0F;
                float alpha = 1.0F;

                Vector3f colorVector = new Vector3f(red, green, blue);
                DustParticleEffect dustParticleEffect = new DustParticleEffect(colorVector, alpha);

                int j = world.getRandom().nextBetweenExclusive(7, 12);
                Direction direction = context.getSide();

                for (int k = 0; k < j; ++k)
                {
                    double offsetX = direction.getOffsetX();
                    double offsetY = direction.getOffsetY();
                    double offsetZ = direction.getOffsetZ();

                    double distance = 0.25;
                    double diffusion = 0.8;

                    double posX = context.getHitPos().getX() + (offsetX * distance) + (offsetX == 0 ? (world.getRandom().nextDouble() - 0.5) * diffusion : 0.0);
                    double posY = context.getHitPos().getY() + (offsetY * distance) + (offsetY == 0 ? (world.getRandom().nextDouble() - 0.5) * diffusion : 0.0);
                    double posZ = context.getHitPos().getZ() + (offsetZ * distance) + (offsetZ == 0 ? (world.getRandom().nextDouble() - 0.5) * diffusion : 0.0);

                    if (world.getServer() != null && world.getServer().getPlayerManager() != null)
                    {
                        world.getServer().getPlayerManager().sendToAll(new ParticleS2CPacket((ParticleEffect) dustParticleEffect, false, posX, posY, posZ, (float) 0.1, (float) 0.1, (float) 0.1, 0, 1));
                    }
                }


                EquipmentSlot equipmentSlot = itemStack.equals(context.getPlayer().getEquippedStack(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                itemStack.damage(1, context.getPlayer(), (userx) ->
                {
                    userx.sendEquipmentBreakStatus(equipmentSlot);
                });
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        PaintedBlockEntity.addColorTooltip(stack, tooltip);
        List<Integer> colors = PaintedBlockEntity.getColors(stack);
        PaintedBlockEntity.addColorToNBT(stack, colors);
    }
}
