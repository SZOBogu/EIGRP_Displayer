package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public abstract class RTPMessage implements Message {
    private IPAddress senderAddress;
    private IPAddress receiverAddress;

    public RTPMessage (IPAddress senderAddress, IPAddress receiverAddress){
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
    }

    public IPAddress getSenderAddress() {
        return senderAddress;
    }

    public IPAddress getReceiverAddress() {
        return receiverAddress;
    }
}
