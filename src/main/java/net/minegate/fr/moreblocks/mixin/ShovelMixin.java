package net.minegate.fr.moreblocks.mixin;

import java.util.function.Consumer;

import net.minecraft.particle.BlockStateParticleEffect;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.block.SlabDirtPathBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

@Mixin(ShovelItem.class)
public class ShovelMixin {
    @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
    private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info){
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if(context.getSide() != Direction.DOWN && SlabDirtPathBlock.canExistAt(state, world, pos)){
            PlayerEntity player = context.getPlayer();
            Block block = state.getBlock();
            Boolean isPlayerSneaking = player.isSneaking();
            Boolean success = false;
            BlockState newState = net.minecraft.block.Blocks.GREEN_WOOL.getDefaultState();
            SlabType slabType = block instanceof SlabBlock ? (SlabType)state.get(SlabBlock.TYPE) : SlabType.DOUBLE;

            if(isPlayerSneaking && block instanceof SlabBlock && slabType != SlabType.DOUBLE){ // single swaps
                if(block == Blocks.DIRT_SLAB){
                    newState = block.getDefaultState().with(SlabBlock.TYPE, slabType == SlabType.BOTTOM ? SlabType.TOP : SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));

                    success = true;
                }

                else if(block == Blocks.GRASS_BLOCK_SLAB){
                    newState = block.getDefaultState().with(SlabBlock.TYPE, slabType == SlabType.BOTTOM ? SlabType.TOP : SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));

                    success = true;
                }

                else if(block == Blocks.COARSE_DIRT_SLAB && slabType == SlabType.BOTTOM || slabType == SlabType.TOP){
                    newState = block.getDefaultState().with(SlabBlock.TYPE, slabType == SlabType.BOTTOM ? SlabType.TOP : SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));

                    success = true;
                }

                else if(block == Blocks.FARMLAND_SLAB && slabType == SlabType.BOTTOM || slabType == SlabType.TOP){
                    newState = block.getDefaultState().with(SlabBlock.TYPE, slabType == SlabType.BOTTOM ? SlabType.TOP : SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));

                    success = true;
                }

                else if(block == Blocks.DIRT_PATH_SLAB && slabType == SlabType.BOTTOM || slabType == SlabType.TOP){
                    newState = block.getDefaultState().with(SlabBlock.TYPE, slabType == SlabType.BOTTOM ? SlabType.TOP : SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));

                    success = true;
                }

                else if(block == Blocks.MYCELIUM_SLAB && slabType == SlabType.BOTTOM || slabType == SlabType.TOP){
                    newState = block.getDefaultState().with(SlabBlock.TYPE, slabType == SlabType.BOTTOM ? SlabType.TOP : SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));

                    success = true;
                }

                else if(block == Blocks.PODZOL_SLAB && slabType == SlabType.BOTTOM || slabType == SlabType.TOP){
                    newState = block.getDefaultState().with(SlabBlock.TYPE, slabType == SlabType.BOTTOM ? SlabType.TOP : SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));

                    success = true;
                }
            }

            else if(isPlayerSneaking && ((block instanceof SlabBlock && slabType == SlabType.DOUBLE) || block instanceof Block)){ // doubles to singles
                NbtList enchantments = context.getStack().getEnchantments();
                Boolean isSilkTouch = false;

                for(int x = 0; x < enchantments.size(); ++x) {
                    NbtCompound compoundTag = enchantments.getCompound(x);

                    if(compoundTag.getString("id") == String.valueOf(Registry.ENCHANTMENT.getId(Enchantments.SILK_TOUCH))) isSilkTouch = true;
                }

                if(block == net.minecraft.block.Blocks.DIRT || block == Blocks.DIRT_SLAB){
                    newState = Blocks.DIRT_SLAB.getDefaultState();

                    success = true;
                }

                else if(block == net.minecraft.block.Blocks.GRASS_BLOCK || block == Blocks.GRASS_BLOCK_SLAB){
                    newState = Blocks.GRASS_BLOCK_SLAB.getDefaultState();

                    success = true;
                }

                else if(block == net.minecraft.block.Blocks.COARSE_DIRT || block == Blocks.COARSE_DIRT_SLAB){
                    newState = Blocks.COARSE_DIRT_SLAB.getDefaultState();

                    success = true;

                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(newState.getBlock().asItem())));
                }

                else if(block == net.minecraft.block.Blocks.FARMLAND || block == Blocks.FARMLAND_SLAB){
                    newState = Blocks.FARMLAND_SLAB.getDefaultState();

                    success = true;
                }

                else if(block == net.minecraft.block.Blocks.DIRT_PATH || block == Blocks.DIRT_PATH_SLAB){
                    newState = Blocks.DIRT_PATH_SLAB.getDefaultState();

                    success = true;
                }

                else if(block == net.minecraft.block.Blocks.MYCELIUM || block == Blocks.MYCELIUM_SLAB){
                    newState = Blocks.MYCELIUM_SLAB.getDefaultState();

                    success = true;
                }

                else if(block == net.minecraft.block.Blocks.PODZOL || block == Blocks.PODZOL_SLAB){
                    newState = Blocks.PODZOL_SLAB.getDefaultState();

                    success = true;
                }

                if(success && !(block == net.minecraft.block.Blocks.COARSE_DIRT || block == Blocks.COARSE_DIRT_SLAB)){
                    if(context.getStack().hasEnchantments() && isSilkTouch) world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(newState.getBlock().asItem())));

                    else world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Blocks.DIRT_SLAB.asItem())));
                }
            }

            else if(!isPlayerSneaking){
                if(block == net.minecraft.block.Blocks.DIRT){
                    newState = net.minecraft.block.Blocks.DIRT_PATH.getDefaultState();

                    success = true;
                }

                else if((block == Blocks.GRASS_BLOCK_SLAB || block == Blocks.DIRT_SLAB)){
                    newState = Blocks.DIRT_PATH_SLAB.getDefaultState().with(SlabBlock.TYPE, state.get(SlabBlock.TYPE)).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));

                    success = true;
                }
            }

            if(success){
                if(!world.isClient){
                    world.setBlockState(pos, newState);

                    ((ServerWorld) world).spawnParticles((new BlockStateParticleEffect(ParticleTypes.BLOCK, state)), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3, 0.25, 0.02, 0.25, 0.15);

                    if(player != null) context.getStack().damage(1, (LivingEntity)player, (Consumer<LivingEntity>)((playerEntity_1x) -> { (playerEntity_1x).sendToolBreakStatus(context.getHand()); }));
                }

                else world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);


                info.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}