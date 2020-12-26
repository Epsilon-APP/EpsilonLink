package fr.epsilon.bukkit.packets;

import fr.epsilon.common.packets.Packet;

import java.util.List;

public class PacketSyncPermissions extends Packet {
    private List<String> operatorsList;
    private State state;

    public List<String> getOperatorsList() {
        return operatorsList;
    }

    public State getState() {
        return state;
    }

    public enum State {
        UPDATE, ADD, REMOVE
    }
}
