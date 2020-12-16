package fr.epsilon.bukkit.packets;

import fr.epsilon.api.EState;
import fr.epsilon.common.Packet;

public class PacketUpdateState extends Packet {
    private EState state;

    public PacketUpdateState(EState state) {
        this.state = state;
    }
}
