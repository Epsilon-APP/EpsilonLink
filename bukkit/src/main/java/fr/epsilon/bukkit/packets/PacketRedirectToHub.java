package fr.epsilon.bukkit.packets;

import fr.epsilon.common.Packet;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PacketRedirectToHub extends Packet {
    private List<String> playerList;

    public PacketRedirectToHub(Collection<? extends Player> players) {
        this.playerList = new ArrayList<>();

        for (Player player : players) {
            playerList.add(player.getName());
        }
    }
}
