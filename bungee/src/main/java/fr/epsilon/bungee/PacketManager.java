package fr.epsilon.bungee;

import com.google.gson.Gson;
import fr.epsilon.bungee.packets.*;
import fr.epsilon.common.Client;
import fr.epsilon.common.packets.Packet;
import fr.epsilon.common.packets.PacketHandle;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public class PacketManager implements PacketHandle {
    private EpsilonLink epsilonLink;
    private Client client;
    private Gson gson;

    public PacketManager(EpsilonLink epsilonLink) {
        this.epsilonLink = epsilonLink;
        this.client = epsilonLink.getClient();
        this.gson = new Gson();
    }

    public void sendSimplePacket(EpsilonPacket packetType, Object... parameters) {
        client.sendSimplePacket(packetType.invoke(parameters));
    }

    public CompletableFuture<String> sendPacket(EpsilonPacket packetType, Object... parameters) {
        return client.sendPacket(packetType.invoke(parameters));
    }

    @Override
    public void received(String json) {
        Packet data = gson.fromJson(json, Packet.class);
        EpsilonPacket packetType = EpsilonPacket.getPacket(data.getName());

        if (packetType == null) return;

        switch(packetType) {
            case REGISTER_SERVER: {
                PacketRegisterServer packet = gson.fromJson(json, PacketRegisterServer.class);
                ServerInfo server = epsilonLink.getProxy().constructServerInfo(packet.getServerName(), new InetSocketAddress(packet.getServerName(), packet.getServerPort()), "", false);
                epsilonLink.getProxy().getServers().put(server.getName(), server);
                break;
            }

            case UNREGISTER_SERVER: {
                PacketUnregisterServer packet = gson.fromJson(json, PacketUnregisterServer.class);
                epsilonLink.getProxy().getServers().remove(packet.getServerName());
                break;
            }

            case SEND_PLAYER_TO_SERVER: {
                PacketSendPlayerToServer packet = gson.fromJson(json, PacketSendPlayerToServer.class);
                for (String playerName : packet.getPlayerList()) {
                    ServerInfo server = epsilonLink.getServers(packet.getServerIdentifier());
                    ProxiedPlayer player = epsilonLink.getProxy().getPlayer(playerName);

                    if (player != null)
                        player.connect(server);
                }
                break;
            }
        }
    }
}
