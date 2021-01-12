package fr.epsilon.bukkit.packets;

import fr.epsilon.common.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketJoinQueue extends Packet {
    private String type;
    private List<String> playerList;

    public PacketJoinQueue(String type, String playerName) {
        this.type = type;

        this.playerList = new ArrayList<>();
        playerList.add(playerName);
    }

    public PacketJoinQueue(String type, List<String> playerList) {
        this.type = type;
        this.playerList = playerList;
    }
}
