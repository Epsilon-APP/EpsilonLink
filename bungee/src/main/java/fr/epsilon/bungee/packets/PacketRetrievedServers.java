package fr.epsilon.bungee.packets;

import fr.epsilon.api.EState;
import fr.epsilon.common.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketRetrievedServers extends Packet {
    private final List<Server> serverList;

    public PacketRetrievedServers() {
        this.serverList = new ArrayList<>();
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
