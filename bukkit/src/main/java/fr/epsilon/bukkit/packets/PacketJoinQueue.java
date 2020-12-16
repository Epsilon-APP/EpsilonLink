package fr.epsilon.bukkit.packets;

import fr.epsilon.common.Packet;
import org.bukkit.entity.Player;

public class PacketJoinQueue extends Packet {
    private String type;
    private String playerName;

    public PacketJoinQueue(String type, Player player) {
        this.type = type;
        this.playerName = player.getName();
    }
}
