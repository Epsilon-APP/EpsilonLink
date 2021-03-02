package fr.epsilon.bungee.packets;

import fr.epsilon.common.packets.Packet;

import java.util.List;

public class PacketSendPlayerToServer extends Packet {
    private String serverIdentifier;
    private List<String> playerList;

    public void response() {
        this.serverIdentifier = null;
        this.playerList = null;
    }

    public String getServerIdentifier() {
        return serverIdentifier;
    }

    public List<String> getPlayerList() {
        return playerList;
    }
}
