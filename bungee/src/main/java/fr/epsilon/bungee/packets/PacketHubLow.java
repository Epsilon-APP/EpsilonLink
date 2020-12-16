package fr.epsilon.bungee.packets;

import fr.epsilon.common.Packet;

public class PacketHubLow extends Packet {
    private String identifier;

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
