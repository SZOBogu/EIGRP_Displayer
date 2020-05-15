package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class HelloMessage extends Message implements Sendable {
    public HelloMessage(IPAddress senderAddress, IPAddress receiverAddress){
        super(senderAddress, receiverAddress);
    }
    public HelloMessage(IPAddress source, IPAddress sender, IPAddress receiver, IPAddress target){
        super(source, sender, receiver, target);
    }
}
