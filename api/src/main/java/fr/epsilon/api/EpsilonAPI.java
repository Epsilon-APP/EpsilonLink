package fr.epsilon.api;

import fr.epsilon.api.game.EGame;
import fr.epsilon.api.game.EGameManager;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public abstract class EpsilonAPI {
    private static EpsilonAPI instance;

    public EpsilonAPI() {
        instance = this;
    }

    /**
     * Get a instance of EpsilonAPI
     *
     * @return Instance of EpsilonAPI
     */
    public static EpsilonAPI get() {
        return instance;
    }

    /**
     * Redirect list of players to HUB
     *
     * @param players Bukkit Player
     */
    public abstract void redirectHub(Player... players);

    /**
     * Get current server
     *
     * @return Current server
     */
    public abstract EServer getServer();

    /**
     * Get list of server registered
     *
     * @param callback List of server
     */
    public abstract void getServers(Consumer<List<EServer>> callback);

    /**
     * Open a instance of server with template type
     * Template need to be registered in templates.json
     *
     * @param type Template type
     */
    public abstract void openServer(String type);

    /**
     * Get queue system to join or leave queue
     *
     * @return Queue system
     */
    public abstract EQueue getQueueSystem();

    public abstract EGameManager getGameManager();
}
