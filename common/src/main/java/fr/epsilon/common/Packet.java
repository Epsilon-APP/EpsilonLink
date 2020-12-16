package fr.epsilon.common;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Packet {
    @SerializedName("_")
    private String id;
    private String packet;

    public Packet() {
        this.id = UUID.randomUUID().toString();
        this.packet = getClass().getSimpleName();
    }

    public String getUniqueId() {
        return id;
    }

    public void setUniqueId(String id) {
        this.id = id;
    }

    public String getName() {
        return packet;
    }
}
