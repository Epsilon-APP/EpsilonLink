package fr.epsilon.bukkit.api;

import fr.epsilon.api.EQueue;
import fr.epsilon.bukkit.EpsilonPacket;
import fr.epsilon.bukkit.managers.PacketManager;
import org.bukkit.entity.Player;

public class Queue extends EQueue {
    private final PacketManager packetManager;

    public Queue(PacketManager packetManager) {
        this.packetManager = packetManager;
    }

    @Override
    public void join(Player player, String queueType) {
        packetManager.sendSimplePacket(EpsilonPacket.JOIN_QUEUE, queueType, player.getName());
    }

    @Override
    public void leave(Player player) {
        packetManager.sendSimplePacket(EpsilonPacket.LEAVE_QUEUE, player.getName());
    }
}
