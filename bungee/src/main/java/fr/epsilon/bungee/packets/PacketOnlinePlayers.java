package fr.epsilon.bungee.packets;

import fr.epsilon.common.Packet;

public class PacketOnlinePlayers extends Packet {
    private String type;
    private int count;

    public String getType() {
        return type;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
