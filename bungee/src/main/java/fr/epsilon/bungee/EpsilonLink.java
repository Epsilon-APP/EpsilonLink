package fr.epsilon.bungee;

import fr.epsilon.bungee.commands.HubCommand;
import fr.epsilon.bungee.listeners.ConnectionListener;
import fr.epsilon.common.Client;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

public class EpsilonLink extends Plugin {
    private Client client;

    @Override
    public void onEnable() {
        client = new Client(new PacketManager(this), () -> {
            ProxyServer.getInstance().getServers().clear();
        });

        getProxy().getPluginManager().registerListener(this, new ConnectionListener(this));
        getProxy().getPluginManager().registerCommand(this, new HubCommand(this));
    }

    public Client getClient() {
        return client;
    }

    public String getTypeFromServerInfo(ServerInfo serverInfo) {
        if (serverInfo != null) {
            String[] split = serverInfo.getName().split("-");

            if (split.length == 2) {
                return split[0];
            }
        }

        return null;
    }

    public ServerInfo getServers(String identifier) {
        return ProxyServer.getInstance().getServersCopy().values().stream().filter(serverInfo -> {
            String[] split = serverInfo.getName().split("-");

            if (split.length == 2) {
                return split[1].equals(identifier);
            }

            return false;
        }).findAny().orElse(null);
    }

    public List<ServerInfo> getHubOrdained() {
        return ProxyServer.getInstance().getServersCopy().values().stream()
                .filter(serverInfo -> {
                    String[] split = serverInfo.getName().split("-");

                    if (split.length == 2) {
                        return split[0].equals("HUB");
                    }

                    return false;
                })
                .sorted((o1, o2) -> {
                    if (o1.getPlayers().size() < o2.getPlayers().size()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }).collect(Collectors.toList());
    }
}
