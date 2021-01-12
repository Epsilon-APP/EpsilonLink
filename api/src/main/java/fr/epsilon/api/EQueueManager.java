package fr.epsilon.api;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class EQueueManager {
    /**
     * Player join queue with template type
     *
     * @param player Bukkit Player
     * @param queueType Template type
     */
    public abstract void join(Player player, String queueType);

    /**
     * Player leave queue
     *
     * @param player Bukkit Player
     */
    public abstract void leave(Player player);

    /**
     * Player join queue with template type
     *
     * @param players Bukkit List of Player
     * @param queueType Template type
     */
    public abstract void joinGroup(List<Player> players, String queueType);
}
