package eigrp_displayer;

import eigrp_displayer.messages.*;

import java.util.List;

public class MessageManager {
    ShowcaseNetwork network;
    MessageScheduler scheduler;
    List<Message> messagesSentWaitingForReply;
    List<Message> messagesToReplyTo;

    public void sendMessage(RTPMessage message){

    }

    public void receiveMessage(){} //?

    public Message getResponse(RTPMessage message){
        if(message instanceof Query){
            Query query = (Query)message;
            Router router = (Router)network.getDevice(message.getReceiverAddress());
            RoutingTableEntry queriedEntry = null;

            for(RoutingTableEntry entry : router.getRoutingTable().getEntries()){
                if(entry.getIp_address() == query.getQueriedDeviceAddress())
                    queriedEntry = entry;
            }

            Reply replyMessage = new Reply(message.getReceiverAddress(),
                    message.getSenderAddress(),
                    queriedEntry);
            return replyMessage;
        }
        else if(message instanceof Hello){
            ACK helloReply = new ACK(message.getReceiverAddress(), message.getSenderAddress());
            return helloReply;
        }
        else if(message instanceof )

    }

}
