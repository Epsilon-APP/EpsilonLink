package fr.epsilon.bukkit.packets;

import fr.epsilon.common.Packet;
import org.bukkit.entity.Player;

public class PacketLeaveQueue extends Packet {
    private String playerName;

    public PacketLeaveQueue(Player player) {
        this.playerName = player.getName();
    }
}
