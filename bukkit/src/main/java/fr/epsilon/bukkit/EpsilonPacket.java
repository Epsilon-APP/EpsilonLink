package fr.epsilon.bukkit;

import fr.epsilon.bukkit.packets.*;
import fr.epsilon.common.packets.Packet;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum EpsilonPacket {
    OPEN_SERVER(PacketOpenServer.class),
    CLOSE_SERVER(PacketCloseServer.class),

    JOIN_QUEUE(PacketJoinQueue.class),
    LEAVE_QUEUE(PacketLeaveQueue.class),

    UPDATE_STATE(PacketUpdateState.class),
    ONLINE_PLAYERS(PacketOnlinePlayers.class),
    GET_SERVERS_REGISTERED(PacketGetServersRegistered.class),
    SYNC_PERMISSIONS(PacketSyncPermissions.class);

    private Class<?> packetClass;

    EpsilonPacket(Class<?> packetClass) {
        this.packetClass = packetClass;
    }

    public String getName() {
        return packetClass.getSimpleName();
    }

    public Packet invoke(Object... parameters) {
        Class<?>[] types = Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new);

        try {
            return (Packet) packetClass.getDeclaredConstructor(types).newInstance(parameters);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static EpsilonPacket getPacket(String name) {
        for (EpsilonPacket packet : values()) {
            if (packet.getName().equals(name))
                return packet;
        }
        return null;
    }
}
