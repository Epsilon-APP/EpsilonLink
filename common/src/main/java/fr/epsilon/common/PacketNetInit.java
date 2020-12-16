package fr.epsilon.common;

public class PacketNetInit extends Packet {
    private String identifier;

    public PacketNetInit() {
        this.identifier = EpsilonUtils.getServerIdentifier();
    }

    public String getIdentifier() {
        return identifier;
    }
}
