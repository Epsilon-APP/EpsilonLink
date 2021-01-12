package fr.epsilon.bukkit;

import fr.epsilon.bukkit.commands.JoinQueueCommand;
import fr.epsilon.bukkit.commands.LeaveQueueCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class EpsilonLink extends JavaPlugin implements Listener {
    private static EpsilonImplementation api;

    @Override
    public void onEnable() {
        api = new EpsilonImplementation(this);

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginCommand("joinqueue").setExecutor(new JoinQueueCommand());
        getServer().getPluginCommand("leavequeue").setExecutor(new LeaveQueueCommand());
    }

    public static EpsilonImplementation getAPI() {
        return api;
    }
}
