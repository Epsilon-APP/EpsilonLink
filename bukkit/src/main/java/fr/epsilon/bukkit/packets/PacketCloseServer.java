package fr.epsilon.bukkit.packets;

import fr.epsilon.common.Packet;

public class PacketCloseServer extends Packet {
    private String identifier;

    public PacketCloseServer(String identifier) {
        this.identifier = identifier;
    }
}
