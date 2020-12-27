package fr.epsilon.bukkit;

import fr.epsilon.api.EServer;
import fr.epsilon.api.EpsilonAPI;
import fr.epsilon.api.game.EGameManager;
import fr.epsilon.bukkit.api.GameManager;
import fr.epsilon.bukkit.api.Queue;
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

    private final Queue queue;
    private final Server server;

    private final GameManager gameManager;

    public EpsilonImplementation(Plugin plugin) {
        this.plugin = plugin;

        this.permissionsManager = new PermissionsManager(this);
        this.packetManager = new PacketManager(this);;

        this.queue = new Queue(packetManager);
        this.server = new Server(plugin, packetManager);
        this.gameManager = new GameManager(plugin);

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
                serverList.add(new Server(plugin, packetManager, server.name, server.slots, server.onlineCount, server.state));
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
                serverList.add(new Server(plugin, packetManager, server.name, server.slots, server.onlineCount, server.state));
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
    public Queue getQueueSystem() {
        return queue;
    }

    @Override
    public EGameManager getGameManager() {
        return gameManager;
    }
}
