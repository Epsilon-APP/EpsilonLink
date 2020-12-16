package fr.epsilon.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class EServer {
    /**
     * Get server name
     *
     * @return Server name
     */
    public abstract String getName();

    /**
     * Get server identifier
     *
     * @return Server identifier
     */
    public abstract String getIdentifier();

    /**
     * Get server template type
     *
     * @return Server template type
     */
    public abstract String getType();

    /**
     * Get server slots
     *
     * @return Server slots
     */
    public abstract int getSlots();

    /**
     * Get server online count
     *
     * @return Server online count
     */
    public abstract int getOnlineCount();

    /**
     * Set server state
     *
     * @param state Server state
     */
    public abstract void setServerState(EState state);

    /**
     * Get server state
     *
     * @return Server state
     */
    public abstract EState getServerState();

    /**
     * Connect player in the server
     *
     * @param player Bukkit Player
     */
    public abstract void connect(Player player);

    /**
     * Close server
     */
    public abstract void close();

    /**
     * Get wool color
     *
     * @return Color wool
     */
    public abstract ItemStack getWoolColor();
}