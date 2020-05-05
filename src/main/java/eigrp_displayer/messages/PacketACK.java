package eigrp_displayer.messages;

public class PacketACK extends Message {
    int packetNumber;

    public PacketACK(Packet packet) {
        super(packet.getTargetAddress(), packet.getReceiverAddress(), packet.getSenderAddress());
        this.packetNumber = packet.getPacketNumber();
    }
}
