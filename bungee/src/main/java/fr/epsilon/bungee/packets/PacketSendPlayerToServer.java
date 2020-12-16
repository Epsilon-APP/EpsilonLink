package fr.epsilon.bungee.packets;

import fr.epsilon.common.Packet;

import java.util.List;

public class PacketSendPlayerToServer extends Packet {
    private String serverIdentifier;
    private List<String> playerList;

    public String getServerIdentifier() {
        return serverIdentifier;
    }

    public List<String> getPlayerList() {
        return playerList;
    }
}
