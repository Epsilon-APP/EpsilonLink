package fr.epsilon.bukkit.packets;

import fr.epsilon.common.packets.Packet;
import org.bukkit.entity.Player;

public class PacketLeaveQueue extends Packet {
    private String playerName;

    public PacketLeaveQueue(String playerName) {
        this.playerName = playerName;
    }
}
