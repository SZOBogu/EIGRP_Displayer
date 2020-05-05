package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class Packet extends Message{
    private Integer packetNumber;
    private static int packetCount = 0;

    public Packet(IPAddress senderAddress, IPAddress receiverAddress){
        super(senderAddress, receiverAddress);
        this.packetNumber = packetCount;
        packetCount++;
    }

    public Packet(IPAddress senderAddress, IPAddress receiverAddress, IPAddress targetAddress) {
        super(senderAddress, receiverAddress, targetAddress);
        this.packetNumber = packetCount;
        packetCount++;
    }

    public Integer getPacketNumber() {
        return packetNumber;
    }
}
