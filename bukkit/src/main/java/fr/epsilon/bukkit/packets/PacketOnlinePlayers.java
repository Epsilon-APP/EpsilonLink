package fr.epsilon.bukkit.packets;

import fr.epsilon.common.packets.Packet;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PacketOnlinePlayers extends Packet {
    private List<EPlayer> playerList;

    public void response(Collection<? extends Player> players) {
        this.playerList = new ArrayList<>();

        for (Player player : players) {
            playerList.add(new EPlayer(player.getUniqueId(), player.getName(), player.isOp(), player.getWorld().getName(), player.getGameMode()));
        }
    }

    private static class EPlayer {
        private UUID uuid;
        private String name;
        private boolean op;
        private String world;
        private GameMode gameMode;

        public EPlayer(UUID uuid, String name, boolean op, String world, GameMode gameMode) {
            this.uuid = uuid;
            this.name = name;
            this.op = op;
            this.world = world;
            this.gameMode = gameMode;
        }
    }
}
