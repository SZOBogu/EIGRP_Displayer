package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class HelloMessage extends Message implements Sendable {
    public HelloMessage(IPAddress senderAddress, IPAddress receiverAddress){
        super(senderAddress, receiverAddress);
    }
    public HelloMessage(IPAddress sender, IPAddress receiver, IPAddress target){
        super(sender, receiver, target);
    }
}
