package fr.epsilon.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HubCommand extends Command {
    public HubCommand() {
        super("hub");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (!EpsilonLink.getTypeFromServerInfo(player.getServer().getInfo()).equalsIgnoreCase("hub")) {
                player.connect(EpsilonLink.getAvailableHub());
            }else {
                player.sendMessage(new TextComponent("§cVous êtes déjà sûr un HUB"));
            }
        }
    }
}
