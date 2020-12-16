package fr.epsilon.hub;

import fr.epsilon.api.EServer;
import fr.epsilon.api.EpsilonAPI;
import fr.epsilon.hub.utils.FastInv;
import fr.epsilon.hub.utils.FastInvManager;
import fr.epsilon.hub.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EpsilonHub extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        FastInvManager.register(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void interactItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null)
            return;

        if (item.getType() == Material.COMPASS) {
            FastInv inventory = new FastInv(27, "List de Hub");

            EpsilonAPI.get().getServers(servers -> {
                for (EServer server : servers) {
                    inventory.addItem(new ItemBuilder(server.getWoolColor()).name(server.getName()).lore("Nombres joueurs : " + server.getOnlineCount() + "/" + server.getSlots(), server.getServerState().name()).build(), (e) -> {
                        server.connect(player);
                    });
                }
            });

            inventory.open(player);
        }
    }
}
