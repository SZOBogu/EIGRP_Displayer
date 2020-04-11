package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class Hello extends RTPMessage implements Message {
    public Hello(IPAddress sender, IPAddress receiver){
        super(sender, receiver);
    }
}
