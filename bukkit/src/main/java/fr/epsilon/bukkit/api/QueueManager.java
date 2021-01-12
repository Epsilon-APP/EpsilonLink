package fr.epsilon.bukkit.api;

import fr.epsilon.api.EQueueManager;
import fr.epsilon.bukkit.EpsilonImplementation;
import fr.epsilon.bukkit.EpsilonPacket;
import fr.epsilon.bukkit.managers.PacketManager;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class QueueManager extends EQueueManager {
    private final PacketManager packetManager;

    public QueueManager(EpsilonImplementation api) {
        this.packetManager = api.getPacketManager();
    }

    @Override
    public void join(Player player, String queueType) {
        packetManager.sendSimplePacket(EpsilonPacket.JOIN_QUEUE, queueType, player.getName());
    }

    @Override
    public void leave(Player player) {
        packetManager.sendSimplePacket(EpsilonPacket.LEAVE_QUEUE, player.getName());
    }

    @Override
    public void joinGroup(List<Player> playerList, String queueType) {
        List<String> playerNameList = playerList.stream().map(Player::getName).collect(Collectors.toList());
        packetManager.sendSimplePacket(EpsilonPacket.JOIN_QUEUE, queueType, playerNameList);
    }
}
