package fr.epsilon.bukkit;

import com.google.gson.Gson;
import fr.epsilon.api.EServer;
import fr.epsilon.api.EpsilonAPI;
import fr.epsilon.api.game.EGameManager;
import fr.epsilon.bukkit.api.GameManager;
import fr.epsilon.bukkit.api.Queue;
import fr.epsilon.bukkit.api.Server;
import fr.epsilon.bukkit.packets.PacketOpenServer;
import fr.epsilon.bukkit.packets.PacketRedirectToHub;
import fr.epsilon.bukkit.packets.PacketRetrievedServers;
import fr.epsilon.common.Client;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EpsilonImplementation extends EpsilonAPI {
    private final Client client;

    private final Plugin plugin;
    private final Queue queue;
    private final Server server;

    private final GameManager gameManager;

    public EpsilonImplementation(Plugin plugin) {
        this.client = new Client(new PacketListener(), null);

        this.plugin = plugin;
        this.queue = new Queue();
        this.server = new Server(plugin);
        this.gameManager = new GameManager(plugin);

        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void redirectHub(Player... players) {
        EpsilonLink.getAPI().getClient().sendSimplePacket(new PacketRedirectToHub(Arrays.asList(players)));
    }

    @Override
    public EServer getServer() {
        return server;
    }

    @Override
    public void getServers(Consumer<List<EServer>> callback) {
        client.sendPacket(new PacketRetrievedServers()).whenComplete((json, error) -> {
            PacketRetrievedServers packet = new Gson().fromJson(json, PacketRetrievedServers.class);

            List<EServer> serverList = new ArrayList<>();

            for (PacketRetrievedServers.Server server : packet.getServerList()) {
                serverList.add(new Server(plugin, server.name, server.slots, server.onlineCount, server.state));
            }

            callback.accept(serverList);
        });
    }

    @Override
    public void openServer(String type) {
        client.sendSimplePacket(new PacketOpenServer(type));
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
