package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class ACKMessage extends RTPMessage implements Message {
    public ACKMessage(IPAddress sender, IPAddress receiver){
        super(sender, receiver);
    }
}
