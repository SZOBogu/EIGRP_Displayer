package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public abstract class Message implements Sendable {
    private IPAddress sourceAddress;
    private IPAddress senderAddress;
    private IPAddress receiverAddress;
    private IPAddress targetAddress;

    public Message(IPAddress senderAddress, IPAddress receiverAddress){
        this(senderAddress, senderAddress, receiverAddress, receiverAddress);
    }

    public Message(IPAddress sourceAddress, IPAddress senderAddress, IPAddress receiverAddress, IPAddress targetAddress){
        this.sourceAddress = sourceAddress;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.targetAddress = targetAddress;
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

    public IPAddress getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(IPAddress targetAddress) {
        this.targetAddress = targetAddress;
    }

    public IPAddress getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(IPAddress sourceAddress) {
        this.sourceAddress = sourceAddress;
    }
}