package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class HelloMessage extends RTPMessage implements Message {
    public HelloMessage(IPAddress sender, IPAddress receiver){
        super(sender, receiver);
    }
}
