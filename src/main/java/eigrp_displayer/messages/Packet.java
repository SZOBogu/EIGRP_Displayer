package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class Packet extends Message implements Sendable{
    private Integer packetNumber;

    public Packet(IPAddress senderAddress, IPAddress receiverAddress, int packetNumber){
        super(senderAddress, receiverAddress);
        this.packetNumber = packetNumber;
    }

    public Packet(IPAddress source, IPAddress senderAddress, IPAddress receiverAddress, IPAddress targetAddress, int packetNumber) {
        super(source, senderAddress, receiverAddress, targetAddress);
        this.packetNumber = packetNumber;
    }

    public Integer getPacketNumber() {
        return packetNumber;
    }
}
