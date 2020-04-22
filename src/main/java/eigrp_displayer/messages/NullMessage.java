package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class NullMessage extends RTPMessage implements Message{
    public NullMessage(){
        this(null, null);
    }

    private NullMessage(IPAddress senderAddress, IPAddress receiverAddress) {
        super(senderAddress, receiverAddress);
    }
}
