package fr.epsilon.bungee;

import com.google.gson.Gson;
import fr.epsilon.bungee.packets.*;
import fr.epsilon.common.Packet;
import fr.epsilon.common.PacketHandle;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;

public class PacketListener implements PacketHandle {
    private static Gson gson = new Gson();

    @Override
    public void received(String json) {
        Packet p = gson.fromJson(json, Packet.class);
        String name = p.getName();
        String uuid = p.getUniqueId();

        if (name.equals("PacketRegisterServer")) {
            PacketRegisterServer packet = gson.fromJson(json, PacketRegisterServer.class);
            ProxyServer.getInstance().getServers().put(packet.getServerName(), ProxyServer.getInstance().constructServerInfo(
                    packet.getServerName(),
                    new InetSocketAddress(packet.getServerName(), packet.getServerPort()),
                    "",
                    false));
        }else if (name.equals("PacketUnregisterServer")) {
            PacketUnregisterServer packet = gson.fromJson(json, PacketUnregisterServer.class);
            ProxyServer.getInstance().getServers().remove(packet.getServerName());
        }else if (name.equals("PacketOnlinePlayers")) {
            PacketOnlinePlayers packet = gson.fromJson(json, PacketOnlinePlayers.class);

            int count = 0;
            for (ServerInfo hub : EpsilonLink.getServersByType(packet.getType())) {
                count += hub.getPlayers().size();
            }

            packet.setCount(count);
            EpsilonLink.getConnection().sendPacketResponse(packet, uuid);
        }else if (name.equals("PacketHubLow")) {
            PacketHubLow packet = gson.fromJson(json, PacketHubLow.class);

            packet.setIdentifier(EpsilonLink.getIdentifierFromServerInfo(EpsilonLink.getAvailableHub()));
            EpsilonLink.getConnection().sendPacketResponse(packet, uuid);
        }else if (name.equals("PacketSendPlayerToServer")) {
            PacketSendPlayerToServer packet = gson.fromJson(json, PacketSendPlayerToServer.class);
            for (String playerName : packet.getPlayerList()) {
                ServerInfo server = EpsilonLink.getServers(packet.getServerIdentifier());
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);

                if (player != null)
                    player.connect(server);
            }
        }
    }
}
