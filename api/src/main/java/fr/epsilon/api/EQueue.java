package fr.epsilon.api;

import org.bukkit.entity.Player;

public abstract class EQueue {
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
}
