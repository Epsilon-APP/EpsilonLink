package fr.epsilon.bungee;

import fr.epsilon.common.Client;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

public class EpsilonLink extends Plugin {
    private static Client connection;

    @Override
    public void onEnable() {
        connection = new Client(new PacketListener(), () -> {
            ProxyServer.getInstance().getServers().clear();
        });

        getProxy().getPluginManager().registerListener(this, new EpsilonListener());
        getProxy().getPluginManager().registerCommand(this, new HubCommand());

        getProxy().setReconnectHandler(new ReconnectHandler() {
            @Override
            public ServerInfo getServer(ProxiedPlayer player) {
                return getAvailableHub();
            }

            @Override
            public void setServer(ProxiedPlayer player) {

            }

            @Override
            public void save() {

            }

            @Override
            public void close() {

            }
        });
    }

    public static Client getConnection() {
        return connection;
    }

    public static String getIdentifierFromServerInfo(ServerInfo serverInfo) {
        if (serverInfo != null) {
            String[] split = serverInfo.getName().split("-");

            if (split.length == 2) {
                return split[1];
            }
        }

        return null;
    }

    public static String getTypeFromServerInfo(ServerInfo serverInfo) {
        if (serverInfo != null) {
            String[] split = serverInfo.getName().split("-");

            if (split.length == 2) {
                return split[0];
            }
        }

        return null;
    }

    public static List<ServerInfo> getServersByType(String type) {
        return ProxyServer.getInstance().getServers().values().stream().filter(serverInfo -> {
            String[] split = serverInfo.getName().split("-");

            if (split.length == 2) {
                return split[0].equals(type.toUpperCase());
            }

            return false;
        }).collect(Collectors.toList());
    }

    public static ServerInfo getServers(String identifier) {
        return ProxyServer.getInstance().getServers().values().stream().filter(serverInfo -> {
            String[] split = serverInfo.getName().split("-");

            if (split.length == 2) {
                return split[1].equals(identifier);
            }

            return false;
        }).findAny().orElse(null);
    }

    public static ServerInfo getAvailableHub() {
        return ProxyServer.getInstance().getServers().values().stream()
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
                }).findFirst().orElse(null);
    }
}
