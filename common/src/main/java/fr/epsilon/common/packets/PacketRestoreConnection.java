package fr.epsilon.common.packets;

import fr.epsilon.common.EpsilonUtils;

public class PacketRestoreConnection extends Packet {
    private String identifier;
    private String type;
    private int port;

    public PacketRestoreConnection() {
        this.identifier = EpsilonUtils.getServerIdentifier();
        this.type = EpsilonUtils.getServerType();
        this.port = EpsilonUtils.getServerPort();
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getType() {
        return type;
    }

    public int getPort() {
        return port;
    }
}
