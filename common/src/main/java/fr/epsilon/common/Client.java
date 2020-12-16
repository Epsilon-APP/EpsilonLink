package fr.epsilon.common;

import com.google.gson.Gson;

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

    public Client(PacketHandle handle, Runnable connectOrClose) {
//        "host.docker.internal"
        this.address = new InetSocketAddress("host.docker.internal", 8250);
        this.futureMap = new HashMap<>();

        connect(address);

        if (connectOrClose != null)
            connectOrClose.run();

        sendSimplePacket(new PacketNetInit());

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            try {
                while (true) {
                    String line = bufferedReader.readLine();

                    if (line != null) {
                        Packet packet = gson.fromJson(line, Packet.class);
                        String packetUniqueId = packet.getUniqueId();

                        if (futureMap.containsKey(packetUniqueId)) {
                            futureMap.get(packetUniqueId).complete(line);
                        } else {
                            handle.received(line);
                        }
                    }else {
                        if (connectOrClose != null)
                            connectOrClose.run();

                        if (socket.isConnected())
                            socket.close();

                        connect(address);

                        if (socket.isConnected())
                            sendSimplePacket(new PacketRestoreConnection());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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
            socket.connect(address);

            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ignored) {}
    }

    public synchronized CompletableFuture<String> sendPacket(Packet packet) {
        CompletableFuture<String> future = new CompletableFuture<>();

        futureMap.put(packet.getUniqueId(), future);
        writer.println(gson.toJson(packet));

        return future;
    }

    public synchronized void sendSimplePacket(Packet packet) {
        writer.println(gson.toJson(packet));
    }

    public void sendPacketResponse(Packet packet, String uuid) {
        packet.setUniqueId(uuid);
        sendSimplePacket(packet);
    }
}
