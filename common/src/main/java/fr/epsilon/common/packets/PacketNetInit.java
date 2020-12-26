package fr.epsilon.common.packets;

import fr.epsilon.common.EpsilonUtils;

public class PacketNetInit extends Packet {
    private String identifier;

    public PacketNetInit() {
        this.identifier = EpsilonUtils.getServerIdentifier();
    }

    public String getIdentifier() {
        return identifier;
    }
}
