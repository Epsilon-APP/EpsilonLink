package fr.epsilon.bukkit.packets;

import fr.epsilon.api.EState;
import fr.epsilon.common.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketGetServersRegistered extends Packet {
    private String type;
    private List<Server> serverList;

    public PacketGetServersRegistered(String type) {
        this.type = type;
        this.serverList = new ArrayList<>();
    }

    public PacketGetServersRegistered() {
        this.serverList = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public static class Server {
        public String name;
        public int slots;
        public int onlineCount;
        public EState state;
    }
}
