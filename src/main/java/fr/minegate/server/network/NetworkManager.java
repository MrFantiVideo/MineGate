package fr.minegate.server.network;

import fr.minegate.MineGate;
import fr.minegate.block.entity.PaintedBlockEntity;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetworkManager
{
    public static final Identifier UPDATE_LISTENERS_PACKET_ID = new Identifier(MineGate.name.toLowerCase(), "update_listeners");

    public static void init()
    {
        ClientPlayNetworking.registerGlobalReceiver(UPDATE_LISTENERS_PACKET_ID, (client, handler, buf, responseSender) -> handleUpdateListenersPacket(client, buf));
    }

    private static void handleUpdateListenersPacket(MinecraftClient client, PacketByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        int tintIndex = buf.readInt();
        World world = client.world;
        BlockState state = world.getBlockState(pos);

        MineGate.log("Mhmhm...");

        world.getPlayers().forEach(player ->
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof PaintedBlockEntity)
            {
                MineGate.log("Ok.");
            }
        });

    }

    public static void sendUpdateListenersPacketToAll(World world, BlockPos pos, int tintIndex)
    {
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeBlockPos(pos);
        data.writeInt(tintIndex);

        world.getPlayers().forEach(player ->
        {
            if (player instanceof ServerPlayerEntity)
            {
                ServerPlayNetworking.send((ServerPlayerEntity) player, UPDATE_LISTENERS_PACKET_ID, data);
            }
        });
    }
}