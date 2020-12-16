package fr.epsilon.bukkit.packets;

import fr.epsilon.common.Packet;

public class PacketOnlinePlayers extends Packet {
    private int count;

    public void setCount(int count) {
        this.count = count;
    }
}
