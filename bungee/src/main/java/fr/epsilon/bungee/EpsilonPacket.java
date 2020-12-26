package fr.epsilon.bungee;

import fr.epsilon.bungee.packets.*;
import fr.epsilon.common.packets.Packet;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum EpsilonPacket {
    REGISTER_SERVER(PacketRegisterServer.class),
    UNREGISTER_SERVER(PacketUnregisterServer.class),
    SEND_PLAYER_TO_SERVER(PacketSendPlayerToServer.class);

    private final Class<?> packetClass;

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
