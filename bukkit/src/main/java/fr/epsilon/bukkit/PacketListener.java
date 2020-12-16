package fr.epsilon.bukkit;

import com.google.gson.Gson;
import fr.epsilon.bukkit.packets.PacketOnlinePlayers;
import fr.epsilon.common.Packet;
import fr.epsilon.common.PacketHandle;
import org.bukkit.Bukkit;

public class PacketListener implements PacketHandle {
    private static Gson gson = new Gson();

    @Override
    public void received(String json) {
        Packet p = gson.fromJson(json, Packet.class);
        String name = p.getName();
        String uuid = p.getUniqueId();

        if (name.equals("PacketOnlinePlayers")) {
            PacketOnlinePlayers packet = gson.fromJson(json, PacketOnlinePlayers.class);
            packet.setCount(Bukkit.getOnlinePlayers().size());

            EpsilonLink.getAPI().getClient().sendPacketResponse(packet, uuid);
        }
    }
}
