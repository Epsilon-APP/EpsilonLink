package fr.epsilon.bukkit.api;

import fr.epsilon.api.EState;
import fr.epsilon.api.EpsilonAPI;
import fr.epsilon.api.game.EGame;
import fr.epsilon.api.game.EGameManager;
import fr.epsilon.api.game.EGamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class GameManager extends EGameManager implements Listener {
    private final Plugin plugin;

    private EGame<? extends EGamePlayer> game;
    private int endTimeout;
    private int time;
    private int requiredPlayer;

    private BukkitTask timer;

    public GameManager(Plugin plugin) {
        this.plugin = plugin;

        this.endTimeout = 10;
        this.time = 10;
        this.requiredPlayer = 2;
    }

    private void requireGame() {
        if (game == null) throw new IllegalAccessError("No GAME registered on GameManager");
    }

    @Override
    public void registerGame(EGame<? extends EGamePlayer> game) {
        this.game = game;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public EGame<? extends EGamePlayer> getGame() {
        return game;
    }

    @Override
    public void start() {
        requireGame();
        game.handleStart();
        stopTimer();
        EpsilonAPI.get().getServer().setServerState(EState.GAME);
    }

    @Override
    public void end() {
        requireGame();
        game.handleEnd();
        EpsilonAPI.get().getServer().setServerState(EState.FINISHED);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> EpsilonAPI.get().getServer().close(), 20*endTimeout);
    }

    @Override
    public void setGameMode(GameMode gamemode) {
        requireGame();
        plugin.getServer().setDefaultGameMode(gamemode);
    }

    @Override
    public void setDifficulty(Difficulty difficulty) {
        requireGame();
        for (World world : plugin.getServer().getWorlds()) {
            world.setDifficulty(difficulty);
        }
    }

    @Override
    public void setEndTimeout(int timeout) {
        this.endTimeout = timeout;
    }

    @Override
    public void setTimer(int time) {
        this.time = time;
    }

    @Override
    public void setRequiredPlayer(int number) {
        this.requiredPlayer = number;
    }

    private void startTimer() {
        this.timer = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            int currentTime = time;

            @Override
            public void run() {
                if (currentTime != 0)
                    game.handleTimer(currentTime);

                if (currentTime == 0)
                    start();

                currentTime--;
            }
        }, 0L,20L);
    }

    private void stopTimer() {
        if (timer != null)
            timer.cancel();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int onlinePlayers = plugin.getServer().getOnlinePlayers().size();
        if (onlinePlayers == requiredPlayer)
            startTimer();

        game.initGamePlayer(player);

        if (game.isStarted())
            game.getGamePlayer(player.getUniqueId()).setSpectator(true);

        game.handleJoin(player);
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        int onlinePlayers = plugin.getServer().getOnlinePlayers().size();
        if (onlinePlayers <= requiredPlayer) {
            EState serverState = EpsilonAPI.get().getServer().getServerState();
            if (serverState == EState.LOBBY) {
                stopTimer();
                Bukkit.broadcastMessage("§cLancement annulé, pas assez de joueur !");
            }
        }

        game.destroyGamePlayer(player);

        game.handleQuit(player);
        event.setQuitMessage(null);
    }
}
