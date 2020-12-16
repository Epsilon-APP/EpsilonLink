package fr.epsilon.bungee.packets;

import fr.epsilon.common.Packet;

public class PacketRegisterServer extends Packet {
    private String serverName;
    private int serverPort;

    public String getServerName() {
        return serverName;
    }

    public int getServerPort() {
        return serverPort;
    }
}
