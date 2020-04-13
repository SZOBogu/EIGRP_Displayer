package eigrp_displayer;

import eigrp_displayer.messages.*;

import java.util.List;

public class MessageManager {
    MessageScheduler scheduler;
    List<RTPMessage> messagesSentWaitingForReply;


    public void sendMessage(RTPMessage message){

    }

    public void receiveMessage(){}

    public void respond(RTPMessage message, Router router){
        if(message instanceof Query){
            Query query = (Query)message;
            RoutingTableEntry queriedEntry = null;

            this.scheduler.scheduleMessage(new ACK(router.getIp_address(), message.getSenderAddress()));

            for(RoutingTableEntry entry : router.getRoutingTable().getEntries()){
                if(entry.getIp_address() == query.getQueriedDeviceAddress())
                    queriedEntry = entry;
            }

            Reply replyMessage = new Reply(message.getReceiverAddress(),
                    message.getSenderAddress(),
                    queriedEntry);
            this.messagesSentWaitingForReply.add(replyMessage);
            this.scheduler.scheduleMessage(replyMessage);
        }
        else if(message instanceof Hello){
            for(RoutingTableEntry entry : router.getRoutingTable().getEntries()){
                if(entry.getIp_address() == message.getSenderAddress())
                    entry.resetTicks();
            }
        }

        else if(message instanceof ACK){
            this.messagesSentWaitingForReply.removeIf(waitingMessage ->
                    (waitingMessage instanceof Query || waitingMessage instanceof Update)
                    && message.getSenderAddress() == waitingMessage.getReceiverAddress());
        }
        else if(message instanceof Update){
            this.scheduler.scheduleMessage(new ACK(router.getIp_address(), message.getSenderAddress()));
            //TODO: updating of routing table
        }
        else if(message instanceof Reply){
            this.messagesSentWaitingForReply.removeIf(waitingMessage ->
                    (waitingMessage instanceof Reply) &&
                            message.getSenderAddress() == waitingMessage.getReceiverAddress());
        }
    }
}
