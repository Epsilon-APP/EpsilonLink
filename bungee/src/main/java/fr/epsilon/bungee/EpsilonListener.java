package fr.epsilon.bungee;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class EpsilonListener implements Listener {
    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        System.out.println("JOIN");
        if (event.getReason() == ServerConnectEvent.Reason.JOIN_PROXY || event.getReason() == ServerConnectEvent.Reason.SERVER_DOWN_REDIRECT || event.getReason() == ServerConnectEvent.Reason.LOBBY_FALLBACK || event.getReason() == ServerConnectEvent.Reason.UNKNOWN) {
            ServerInfo serverInfo = EpsilonLink.getAvailableHub();
            if (serverInfo != null) {
                event.setTarget(serverInfo);
            }else {
                event.getPlayer().disconnect(new TextComponent("§cVeuillez patientez et réesayez !"));
                event.setCancelled(true);
            }
        }
    }
}
