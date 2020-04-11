package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class RTPMessage {
    private IPAddress senderAddress;
    private IPAddress receiverAddress;

    public RTPMessage (IPAddress senderAddress, IPAddress receiverAddress){
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
    }

    public IPAddress getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(IPAddress senderAddress) {
        this.senderAddress = senderAddress;
    }

    public IPAddress getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(IPAddress receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
}
