package fr.epsilon.api.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class EGamePlayer {
    protected Player player;
    protected boolean spectator;

    public EGamePlayer(Player player) {
        this.player = player;
        this.spectator = false;
    }

    /**
     * Get Bukkit Player
     *
     * @return Bukkit Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set spectator mode
     *
     * @param value Spectator or not
     */
    public void setSpectator(boolean value) {
        this.spectator = value;

        if (value) {
            spectatorMode();
        } else {
            player.setGameMode(Bukkit.getDefaultGameMode());
        }
    }

    /**
     * Check if spectator
     *
     * @return If spectator
     */
    public boolean isSpectator() {
        return spectator;
    }

    /**
     * It's called when spectator mode enabled
     */
    public abstract void spectatorMode();
}
