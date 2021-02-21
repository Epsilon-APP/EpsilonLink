package fr.epsilon.common;

import com.google.gson.Gson;
import fr.epsilon.common.packets.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Client {
    private final static Gson gson = new Gson();

    private SocketAddress address;

    private Map<String, CompletableFuture<String>> futureMap;
    private Socket socket;

    private BufferedReader bufferedReader;
    private PrintWriter writer;

    public Client(PacketHandle handle, Runnable connect) {
        this.address = new InetSocketAddress(EpsilonUtils.isLinuxHost() ? "EPSILON": "host.docker.internal", 8250);
        this.futureMap = new HashMap<>();

        connect(address);

        if (connect != null)
            connect.run();

        sendSimplePacket(new PacketNetInit());

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            while (true) {
                try {
                    String line = bufferedReader.readLine();

                    Packet packet = gson.fromJson(line, Packet.class);
                    String packetUniqueId = packet.getUniqueId();

                    System.out.println(packet.getName());

                    if (futureMap.containsKey(packetUniqueId)) {
                        futureMap.get(packetUniqueId).complete(line);
                    } else {
                        handle.received(line);
                    }
                }catch (Exception exception) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    connect(address);

                    if (socket.isConnected()) {
                        if (connect != null)
                            connect.run();

                        sendSimplePacket(new PacketRestoreConnection());
                    }
                }
            }
        });

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            sendSimplePacket(new PacketKeepAlive());
        }, 1, 1, TimeUnit.SECONDS);
    }

    private void connect(SocketAddress address) {
        try {
            this.socket = new Socket();

            socket.setTcpNoDelay(true);
            socket.connect(address);

            this.writer = new PrintWriter(socket.getOutputStream());
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ignored) {}
    }

    public CompletableFuture<String> sendPacket(Packet packet) {
        CompletableFuture<String> future = new CompletableFuture<>();

        futureMap.put(packet.getUniqueId(), future);
        writer.println(gson.toJson(packet));
        writer.flush();

        return future;
    }

    public void sendSimplePacket(Packet packet) {
        writer.println(gson.toJson(packet));
        writer.flush();
    }
}
