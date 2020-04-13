package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class Placeholder extends RTPMessage implements Message{
    public Placeholder(){
        this(null, null);
    }

    private Placeholder(IPAddress senderAddress, IPAddress receiverAddress) {
        super(senderAddress, receiverAddress);
    }
}
