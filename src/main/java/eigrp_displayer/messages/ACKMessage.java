package eigrp_displayer.messages;

import eigrp_displayer.IPAddress;

public class ACKMessage extends Message implements Sendable {

    public ACKMessage(IPAddress senderAddress, IPAddress receiverAddress){
        super(senderAddress, receiverAddress);
    }

    public ACKMessage(IPAddress sender, IPAddress receiver, IPAddress target){
        super(sender, receiver, target);
    }
}
