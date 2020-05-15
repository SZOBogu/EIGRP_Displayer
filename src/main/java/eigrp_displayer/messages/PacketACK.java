package eigrp_displayer.messages;

public class PacketACK extends Message implements Sendable {
    private int packetNumber;

    public PacketACK(Packet packet) {
        super(packet.getTargetAddress(), packet.getTargetAddress(), packet.getSenderAddress(), packet.getSourceAddress());
        this.packetNumber = packet.getPacketNumber();
    }
    public int getPacketNumber() {
        return packetNumber;
    }
}
