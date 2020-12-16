package fr.epsilon.bukkit.packets;

import fr.epsilon.common.Packet;

public class PacketOpenServer extends Packet {
    private String type;

    public PacketOpenServer(String type) {
        this.type = type;
    }
}
