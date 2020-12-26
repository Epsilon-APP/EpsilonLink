package fr.epsilon.bukkit.managers;

import com.google.gson.Gson;
import fr.epsilon.bukkit.EpsilonImplementation;
import fr.epsilon.bukkit.EpsilonLink;
import fr.epsilon.bukkit.EpsilonPacket;
import fr.epsilon.bukkit.packets.PacketOnlinePlayers;
import fr.epsilon.bukkit.packets.PacketSyncPermissions;
import fr.epsilon.common.Client;
import fr.epsilon.common.packets.Packet;
import fr.epsilon.common.packets.PacketHandle;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PacketManager implements PacketHandle {
    private Client client;
    private Gson gson;

    private EpsilonImplementation epsilonImplementation;

    public PacketManager(EpsilonImplementation epsilonImplementation) {
        this.client = new Client(this, null);
        this.gson = new Gson();

        this.epsilonImplementation = epsilonImplementation;
    }

    public void sendSimplePacket(EpsilonPacket packetType, Object... parameters) {
        client.sendSimplePacket(packetType.invoke(parameters));
    }

    public <T> CompletableFuture<T> sendPacket(EpsilonPacket packetType, Class<T> pClass, Object... parameters) {
        CompletableFuture<T> future = new CompletableFuture<>();

        client.sendPacket(packetType.invoke(parameters)).whenComplete((json, error) -> {
            future.complete(gson.fromJson(json, pClass));
        });

        return future;
    }

    public void sendResponse(Packet packet) {
        client.sendSimplePacket(packet);
    }

    @Override
    public void received(String json) {
        Packet data = gson.fromJson(json, Packet.class);
        EpsilonPacket packetType = EpsilonPacket.getPacket(data.getName());

        if (packetType == null) return;

        switch (packetType) {
            case ONLINE_PLAYERS: {
                PacketOnlinePlayers packet = gson.fromJson(json, PacketOnlinePlayers.class);

                Server server = Bukkit.getServer();
                Collection<? extends Player> players = server.getOnlinePlayers();

                packet.response(players);

                sendResponse(packet);
                break;
            }

            case SYNC_PERMISSIONS: {
                PacketSyncPermissions packet = gson.fromJson(json, PacketSyncPermissions.class);
                PermissionsManager permissionsManager = epsilonImplementation.getPermissionsManager();

                if (packet.getState() == PacketSyncPermissions.State.UPDATE) {
                    permissionsManager.syncOperators(packet.getOperatorsList());
                }else if (packet.getState() == PacketSyncPermissions.State.ADD) {
                    permissionsManager.addOperator(packet.getOperatorsList().get(0));
                }else if (packet.getState() == PacketSyncPermissions.State.REMOVE) {
                    permissionsManager.removeOperator(packet.getOperatorsList().get(0));
                }
            }
        }
    }
}
