package fr.epsilon.bungee.packets;

import fr.epsilon.common.Packet;

public class PacketUnregisterServer extends Packet {
    private String serverName;

    public String getServerName() {
        return serverName;
    }
}
