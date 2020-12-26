package fr.epsilon.bungee.commands;

import fr.epsilon.bungee.EpsilonLink;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class HubCommand extends Command {
    private EpsilonLink epsilonLink;

    public HubCommand(EpsilonLink epsilonLink) {
        super("hub");

        this.epsilonLink = epsilonLink;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            List<ServerInfo> hubList = epsilonLink.getHubOrdained();
            hubList.remove(player.getServer().getInfo());

            if (!hubList.isEmpty())
                player.connect(hubList.get(0));
        }
    }
}
