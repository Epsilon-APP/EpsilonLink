package fr.epsilon.bungee.listeners;

import fr.epsilon.bungee.EpsilonLink;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;
import java.util.List;

public class ConnectionListener implements Listener, ReconnectHandler {
    private final EpsilonLink epsilonLink;

    public ConnectionListener(EpsilonLink epsilonLink) {
        this.epsilonLink = epsilonLink;

        epsilonLink.getProxy().setReconnectHandler(this);
    }

    @EventHandler
    public void serverKick(ServerKickEvent event) {
        ProxiedPlayer player = event.getPlayer();
        ServerInfo serverKick = event.getKickedFrom();

        player.sendMessage(new TextComponent("§cVous avez été redirigez dans un HUB"));

        List<ServerInfo> hubList = epsilonLink.getHubOrdained();

        hubList.remove(serverKick);

        if (!hubList.isEmpty()) {
            event.setCancelServer(hubList.get(0));
            event.setCancelled(true);
        }else {
            event.setKickReasonComponent(new TextComponent[]{getKickReason()});
        }
    }

    @EventHandler
    public void serverPostLogin(PreLoginEvent event) {
        List<ServerInfo> hubList = epsilonLink.getHubOrdained();
        if (hubList.isEmpty()) {
            event.setCancelReason(getKickReason());
            event.setCancelled(true);
        }
    }

    @Override
    public ServerInfo getServer(ProxiedPlayer player) {
        List<ServerInfo> hubList = epsilonLink.getHubOrdained();

        if (!hubList.isEmpty())
            return hubList.get(0);

        return null;
    }

    @Override
    public void setServer(ProxiedPlayer player) {}

    @Override
    public void save() {}

    @Override
    public void close() {}

    private TextComponent getKickReason() {
        return new TextComponent("§cUn problème est survenu, veuillez réessayer !");
    }
}
