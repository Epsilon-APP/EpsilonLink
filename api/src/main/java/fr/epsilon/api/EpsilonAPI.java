package fr.epsilon.api;

import fr.epsilon.api.game.EGame;
import fr.epsilon.api.game.EGameManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
     * Get current server
     *
     * @return Current server
     */
    public abstract EServer getServer();

    /**
     * Get list of server registered
     *
     * @return callback List of server
     */
    public abstract CompletableFuture<List<EServer>> getServers();

    /**
     * Get list of server registered by filter TYPE
     *
     * @return callback List of server
     */
    public abstract CompletableFuture<List<EServer>> getServers(String type);

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
