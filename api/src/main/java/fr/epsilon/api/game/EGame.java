package fr.epsilon.api.game;

import fr.epsilon.api.EState;
import fr.epsilon.api.EpsilonAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class EGame<GPLAYER extends EGamePlayer> implements Listener {
    private final Class<GPLAYER> gamePlayerClass;
    private final HashMap<UUID, GPLAYER> gamePlayers;

    /**
     * @param plugin Main class
     * @param gamePlayerClass
     */
    public EGame(Plugin plugin, Class<GPLAYER> gamePlayerClass) {
        this.gamePlayerClass = gamePlayerClass;
        this.gamePlayers = new HashMap<>();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Use this for manually start game
     */
    public void startGame() {
        EpsilonAPI.get().getGameManager().start();
    }

    /**
     * End game, close server and redirect players on HUB
     */
    public void endGame() {
        EpsilonAPI.get().getGameManager().end();
    }

    /**
     * Get if game is started
     *
     * @return Game is started
     */
    public boolean isStarted() {
        return EpsilonAPI.get().getServer().getServerState() != EState.LOBBY;
    }

    /**
     * Get spectators list
     *
     * @return Spectators list
     */
    public List<GPLAYER> getSpectators() {
        List<GPLAYER> gameSpectatorsClone = new ArrayList<>();
        for (GPLAYER gameSpectator : gamePlayers.values()) {
            if (gameSpectator.isSpectator())
                gameSpectatorsClone.add(gameSpectator);
        }

        return gameSpectatorsClone;
    }

    /**
     * Get in game players
     *
     * @return All players not spectators is returned
     */
    public List<GPLAYER> getInGamePlayer() {
        List<GPLAYER> gamePlayersClone = new ArrayList<>();
        for (GPLAYER gamePlayer : gamePlayers.values()) {
            if (!gamePlayer.isSpectator())
                gamePlayersClone.add(gamePlayer);
        }

        return gamePlayersClone;
    }

    /**
     * Get GamePlayer with UUID
     *
     * @param uuid Player Unique ID
     * @return GamePlayer
     */
    public GPLAYER getGamePlayer(UUID uuid) {
        if (gamePlayers.containsKey(uuid))
            return gamePlayers.get(uuid);

        return null;
    }

    /**
     * Check if player is connected on server
     *
     * @param player Bukkit Player
     * @return If player exist
     */
    public boolean hasPlayer(Player player) {
        return gamePlayers.containsKey(player.getUniqueId());
    }

    /**
     * It's called when game started
     */
    public abstract void handleStart();

    /**
     * It's called when game ended
     */
    public abstract void handleEnd();

    /**
     * It's called every seconds elapsed by timer
     *
     * @param time Current time / Cannot be 0
     */
    public abstract void handleTimer(int time);

    /**
     * It's called when player join
     *
     * @param player Bukkit Player
     */
    public abstract void handleJoin(Player player);

    /**
     * It's called when player quit
     *
     * @param player Bukkit Player
     */
    public abstract void handleQuit(Player player);

    /***
     * Don't use this
     *
     * @param player
     */
    public void initGamePlayer(Player player) {
        try {
            GPLAYER gamePlayer = gamePlayerClass.getConstructor(Player.class).newInstance(player);
            gamePlayers.put(player.getUniqueId(), gamePlayer);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Don't use this
     *
     * @param player
     */
    public void destroyGamePlayer(Player player) {
        gamePlayers.remove(player.getUniqueId());
    }
}
