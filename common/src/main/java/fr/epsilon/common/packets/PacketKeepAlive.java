package fr.epsilon.common.packets;

import java.util.Random;

public class PacketKeepAlive extends Packet {
    private int id;

    public PacketKeepAlive() {
        this.id = new Random().nextInt();
    }
}
