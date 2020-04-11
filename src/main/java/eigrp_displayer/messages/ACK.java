package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class ACK extends RTPMessage implements Message {
    public ACK(IPAddress sender, IPAddress receiver){
        super(sender, receiver);
    }
}
