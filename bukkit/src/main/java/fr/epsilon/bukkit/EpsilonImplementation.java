package fr.epsilon.bukkit;

import fr.epsilon.api.EServer;
import fr.epsilon.api.EpsilonAPI;
import fr.epsilon.api.game.EGameManager;
import fr.epsilon.bukkit.api.GameManager;
import fr.epsilon.bukkit.api.QueueManager;
import fr.epsilon.bukkit.api.Server;
import fr.epsilon.bukkit.managers.PacketManager;
import fr.epsilon.bukkit.managers.PermissionsManager;
import fr.epsilon.bukkit.packets.PacketGetServersRegistered;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class EpsilonImplementation extends EpsilonAPI {
    private final Plugin plugin;

    private final PacketManager packetManager;
    private final PermissionsManager permissionsManager;

    private final QueueManager queueManager;
    private final Server server;

    private final GameManager gameManager;

    public EpsilonImplementation(Plugin plugin) {
        this.plugin = plugin;

        this.permissionsManager = new PermissionsManager(this);
        this.packetManager = new PacketManager(this);;

        this.queueManager = new QueueManager(this);
        this.server = new Server(this);
        this.gameManager = new GameManager(this);

        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public PermissionsManager getPermissionsManager() {
        return permissionsManager;
    }

    @Override
    public EServer getServer() {
        return server;
    }

    @Override
    public CompletableFuture<List<EServer>> getServers() {
        CompletableFuture<List<EServer>> future = new CompletableFuture<>();

        packetManager.sendPacket(EpsilonPacket.GET_SERVERS_REGISTERED, PacketGetServersRegistered.class).whenComplete((packet, error) -> {
            List<EServer> serverList = new ArrayList<>();

            for (PacketGetServersRegistered.Server server : packet.getServerList()) {
                serverList.add(new Server(this, server.name, server.slots, server.onlineCount, server.state));
            }

            future.complete(serverList);
        });

        return future;
    }

    @Override
    public CompletableFuture<List<EServer>> getServers(String type) {
        CompletableFuture<List<EServer>> future = new CompletableFuture<>();

        packetManager.sendPacket(EpsilonPacket.GET_SERVERS_REGISTERED, PacketGetServersRegistered.class, type).whenComplete((packet, error) -> {
            List<EServer> serverList = new ArrayList<>();

            for (PacketGetServersRegistered.Server server : packet.getServerList()) {
                serverList.add(new Server(this, server.name, server.slots, server.onlineCount, server.state));
            }

            future.complete(serverList);
        });

        return future;
    }

    @Override
    public void openServer(String type) {
        packetManager.sendSimplePacket(EpsilonPacket.OPEN_SERVER, type);
    }

    @Override
    public QueueManager getQueueSystem() {
        return queueManager;
    }

    @Override
    public EGameManager getGameManager() {
        return gameManager;
    }
}
