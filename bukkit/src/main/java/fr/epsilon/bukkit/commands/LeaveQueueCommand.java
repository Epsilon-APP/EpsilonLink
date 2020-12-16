package fr.epsilon.bukkit.commands;

import fr.epsilon.api.EpsilonAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveQueueCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.isOp())
                return true;

            EpsilonAPI.get().getQueueSystem().leave(player);
            player.sendMessage("§cVous avez été retiré de la queue");
        }

        return true;
    }
}
