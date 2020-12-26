package fr.epsilon.bukkit.packets;

import fr.epsilon.common.packets.Packet;

public class PacketJoinQueue extends Packet {
    private String type;
    private String playerName;

    public PacketJoinQueue(String type, String playerName) {
        this.type = type;
        this.playerName = playerName;
    }
}
