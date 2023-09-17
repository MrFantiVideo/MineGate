package fr.minegate.util;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class ServerPing
{
    public static CompletableFuture<PingResult> ping(String address)
    {
        return ping(address, 25565);
    }

    public static CompletableFuture<PingResult> ping(String address, int port)
    {
        return ping(new InetSocketAddress(address, port), 1000);
    }

    public static CompletableFuture<PingResult> ping(InetSocketAddress address, int timeout)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            try (Socket socket = new Socket())
            {

                socket.setSoTimeout(timeout);
                socket.connect(address, timeout);

                try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                     InputStream in = socket.getInputStream();
                     InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_16BE))
                {

                    out.write(new byte[]{(byte) 0xFE, 0x01});

                    int packetId = in.read();
                    int length = reader.read();

                    if (packetId != 0xFF)
                    {
                        throw new IOException("Invalid packet id: " + packetId);
                    }

                    if (length <= 0)
                    {
                        throw new IOException("Invalid length: " + length);
                    }

                    char[] chars = new char[length];

                    if (reader.read(chars, 0, length) != length)
                    {
                        throw new IOException("Premature end of stream");
                    }

                    String string = new String(chars);

                    if (!string.startsWith("§"))
                    {
                        throw new IOException("Unexpected response: " + string);
                    }

                    String[] data = string.split("\000");

                    int players = Integer.parseInt(data[4]);
                    int maxPlayers = Integer.parseInt(data[5]);

                    return new PingResult(players, maxPlayers, data[3]);
                }
            } catch (IOException e)
            {
                throw new CompletionException(e);
            }
        });
    }

    public static Text MOTD;
    static         long startTime = 0;

    public static void getMotdServer(ButtonWidget button)
    {
        if (System.currentTimeMillis() - startTime >= 2000)
        {
            startTime = System.currentTimeMillis();
            ServerPing.ping("minegate.fr")
                    .thenAccept(result ->
                    {
                        MOTD = Text.literal(result.getMotd()).append(Text.literal(" (" + result.getPlayers() + "/" + result.getMaxPlayers() + ")").styled(style -> style.withColor(TextColor.fromRgb(2818664))));
                        button.active = true;
                    })
                    .exceptionally(ex ->
                    {
                        MOTD = Text.literal("Serveur indisponible.").styled(style -> style.withColor(TextColor.fromRgb(2818664)));
                        button.active = false;
                        return null;
                    });
        }

    }

    public static class PingResult
    {

        private final int    players;
        private final int    maxPlayers;
        private final String motd;

        public PingResult(int players, int maxPlayers, String motd)
        {
            this.players = players;
            this.maxPlayers = maxPlayers;
            this.motd = motd;
        }

        public int getPlayers()
        {
            return this.players;
        }

        public int getMaxPlayers()
        {
            return this.maxPlayers;
        }

        public String getMotd()
        {
            return this.motd;
        }

        @Override
        public String toString()
        {
            return "PingResult{players=" + this.players + ", maxPlayers=" + this.maxPlayers + '}';
        }
    }

    static
    {
        MOTD = Text.literal("Chargement...").styled(style -> style.withColor(TextColor.fromRgb(2818664)));
    }
}
