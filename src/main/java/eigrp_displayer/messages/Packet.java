package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class Packet extends Message{
    private Integer packetNumber;

    //TODO:tests
    public Packet(IPAddress senderAddress, IPAddress receiverAddress, int packetNumber){
        super(senderAddress, receiverAddress);
        this.packetNumber = packetNumber;
    }

    public Packet(IPAddress senderAddress, IPAddress receiverAddress, IPAddress targetAddress, int packetNumber) {
        super(senderAddress, receiverAddress, targetAddress);
        this.packetNumber = packetNumber;
    }

    public Integer getPacketNumber() {
        return packetNumber;
    }
}
