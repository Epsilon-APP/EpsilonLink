package fr.epsilon.bukkit.api;

import fr.epsilon.api.EQueue;
import fr.epsilon.bukkit.EpsilonLink;
import fr.epsilon.bukkit.packets.PacketJoinQueue;
import fr.epsilon.bukkit.packets.PacketLeaveQueue;
import org.bukkit.entity.Player;

public class Queue extends EQueue {
    @Override
    public void join(Player player, String queueType) {
        EpsilonLink.getAPI().getClient().sendSimplePacket(new PacketJoinQueue(queueType, player));
    }

    @Override
    public void leave(Player player) {
        EpsilonLink.getAPI().getClient().sendSimplePacket(new PacketLeaveQueue(player));
    }
}
