package fr.epsilon.bukkit.managers;

import fr.epsilon.bukkit.EpsilonImplementation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class PermissionsManager implements Listener {
    private EpsilonImplementation epsilonImplementation;
    private List<String> operatorsList;

    public PermissionsManager(EpsilonImplementation epsilonImplementation) {
        this.epsilonImplementation = epsilonImplementation;
        this.operatorsList = new ArrayList<>();

        epsilonImplementation.getPlugin().getServer().getPluginManager().registerEvents(this, epsilonImplementation.getPlugin());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        updateOperator(player);
    }

    private void updateOperator(Player player) {
        player.setOp(operatorsList.contains(player.getName()));
    }

    public void syncOperators(List<String> operators) {
        operatorsList.clear();
        operatorsList.addAll(operators);

        for (Player player : epsilonImplementation.getPlugin().getServer().getOnlinePlayers()) {
            updateOperator(player);
        }
    }

    public void addOperator(String operator) {
        if (!operatorsList.contains(operator))
            operatorsList.add(operator);

        Player player = epsilonImplementation.getPlugin().getServer().getPlayerExact(operator);

        if (player != null) {
            player.sendMessage("§aVous avez été promu opérateur sur le serveur !");
            updateOperator(player);
        }
    }

    public void removeOperator(String operator) {
        operatorsList.remove(operator);

        Player player = epsilonImplementation.getPlugin().getServer().getPlayerExact(operator);

        if (player != null) {
            player.sendMessage("§cVous avez été retiré de la liste des opérateurs sur le serveur !");
            updateOperator(player);
        }
    }
}
