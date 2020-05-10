package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public abstract class Message implements Sendable {
    private IPAddress senderAddress;
    private IPAddress receiverAddress;
    private IPAddress targetAddress;

    public Message(IPAddress senderAddress, IPAddress receiverAddress){
        this(senderAddress, receiverAddress, receiverAddress);
    }

    public Message(IPAddress senderAddress, IPAddress receiverAddress, IPAddress targetAddress){
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.targetAddress = targetAddress;
    }

    public IPAddress getSenderAddress() {
        return senderAddress;
    }

    public IPAddress getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(IPAddress receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public IPAddress getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(IPAddress targetAddress) {
        this.targetAddress = targetAddress;
    }
}