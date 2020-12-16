package fr.epsilon.api.game;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;

/**
 * All functions required a Game registered
 */

public abstract class EGameManager  {
    /**
     * Register a Game
     *
     * @param game EGame
     */
    public abstract void registerGame(EGame<? extends EGamePlayer> game);

    /**
     * Get a Game if exist
     *
     * @return EGame or null
     */
    public abstract EGame<? extends EGamePlayer> getGame();

    /**
     * Manually start game
     */
    public abstract void start();

    /**
     * End game, close server and redirect players
     */
    public abstract void end();

    /**
     * Set default GameMode
     *
     * @param gamemode Bukkit GameMode
     */
    public abstract void setGameMode(GameMode gamemode);

    /**
     * Set difficulty
     *
     * @param difficulty Bukkit Difficulty
     */
    public abstract void setDifficulty(Difficulty difficulty);

    public abstract void setEndTimeout(int timeout);
    public abstract void setTimer(int time);
    public abstract void setRequiredPlayer(int number);
}
