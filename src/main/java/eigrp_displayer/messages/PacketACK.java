package eigrp_displayer.messages;

public class PacketACK extends Message {
    private int packetNumber;

    //TODO: test
    public PacketACK(Packet packet) {
        super(packet.getTargetAddress(), packet.getReceiverAddress(), packet.getSenderAddress());
        this.packetNumber = packet.getPacketNumber();
    }
    public int getPacketNumber() {
        return packetNumber;
    }
}
