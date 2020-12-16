package fr.epsilon.bukkit.commands;

import fr.epsilon.api.EpsilonAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinQueueCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.isOp())
                return true;

            if (args.length == 1) {
                String type = args[0];

                EpsilonAPI.get().getQueueSystem().join(player, type);
                player.sendMessage("§cVous avez été ajouté de la queue " + type);
            }
        }

        return true;
    }
}
