package fr.epsilon.bukkit.api;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.epsilon.api.EServer;
import fr.epsilon.api.EState;
import fr.epsilon.api.EpsilonAPI;
import fr.epsilon.bukkit.EpsilonLink;
import fr.epsilon.bukkit.packets.PacketCloseServer;
import fr.epsilon.bukkit.packets.PacketRedirectToHub;
import fr.epsilon.bukkit.packets.PacketUpdateState;
import fr.epsilon.common.EpsilonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Server extends EServer {
    private Plugin plugin;

    private final String name;
    private final int slots;
    private int onlineCount;
    private EState state;

    private org.bukkit.Server server;

    public Server(Plugin plugin, String name, int slots, int onlineCount, EState state) {
        this.plugin = plugin;

        this.name = name;
        this.slots = slots;
        this.onlineCount = onlineCount;
        this.state = state;
    }

    public Server(Plugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();

        this.name = EpsilonUtils.getServerType() + "-" + EpsilonUtils.getServerIdentifier();
        this.slots = server.getMaxPlayers();
        this.state = EState.LOBBY;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIdentifier() {
        String[] nameSplit = name.split("-");
        if (nameSplit.length == 2)
            return nameSplit[1];

        return null;
    }

    @Override
    public String getType() {
        String[] nameSplit = name.split("-");
        if (nameSplit.length == 2)
            return nameSplit[0].toLowerCase();

        return null;
    }

    @Override
    public int getSlots() {
        return slots;
    }

    @Override
    public int getOnlineCount() {
        return server != null ? server.getOnlinePlayers().size() : onlineCount;
    }

    @Override
    public void setServerState(EState state) {
        this.state = state;
        EpsilonLink.getAPI().getClient().sendSimplePacket(new PacketUpdateState(state));
    }

    @Override
    public EState getServerState() {
        return state;
    }

    @Override
    public void connect(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(name);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    @Override
    public void close() {
        EpsilonLink.getAPI().getClient().sendSimplePacket(new PacketRedirectToHub(Bukkit.getOnlinePlayers()));
        plugin.getServer().getScheduler().runTaskLater(plugin, Bukkit::shutdown,20 * 4);
    }

    @Override
    public ItemStack getWoolColor() {
        int calc = slots / 2;
        // VERT : 5 / ROUGE : 14 / ORANGE : 4
        if (onlineCount <= calc) {
            return new ItemStack(Material.WOOL, 1, (byte)5 );
        }else if (onlineCount <= calc*2) {
            return new ItemStack(Material.WOOL, 1, (byte)14);
        }else {
            return new ItemStack(Material.WOOL, 1, (byte)4);
        }
    }
}
