package eigrp_displayer;

import eigrp_displayer.messages.*;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    List<RTPMessage> messagesSentWaitingForReply;

    public MessageManager(){
        this.messagesSentWaitingForReply = new ArrayList<>();
    }

    public void sendMessage(RTPMessage message){

    }

    public void receiveMessage(){}

    public void respond(RTPMessage message, Router router){
        MessageScheduler scheduler = MessageScheduler.getInstance();


        if(message instanceof Query){
            Query query = (Query)message;
            RoutingTableEntry queriedEntry = null;

            scheduler.scheduleMessage(new ACK(router.getIp_address(), message.getSenderAddress()));

            for(RoutingTableEntry entry : router.getRoutingTable().getEntries()){
                if(entry.getIp_address() == query.getQueriedDeviceAddress())
                    queriedEntry = entry;
            }

            Reply replyMessage = new Reply(message.getReceiverAddress(),
                    message.getSenderAddress(),
                    queriedEntry);
            this.getMessagesSentWaitingForReply().add(replyMessage);
            scheduler.scheduleMessage(replyMessage);
        }
        else if(message instanceof Hello){
            for(RoutingTableEntry entry : router.getRoutingTable().getEntries()){
                if(entry.getIp_address() == message.getSenderAddress())
                    entry.resetTicks();
            }
        }

        else if(message instanceof ACK){
            this.getMessagesSentWaitingForReply().removeIf(waitingMessage ->
                    (waitingMessage instanceof Query || waitingMessage instanceof Update)
                    && message.getSenderAddress() == waitingMessage.getReceiverAddress());
        }
        else if(message instanceof Update){
            scheduler.scheduleMessage(new ACK(router.getIp_address(), message.getSenderAddress()));
            //TODO: updating of routing table
        }
        else if(message instanceof Reply){
            this.getMessagesSentWaitingForReply().removeIf(waitingMessage ->
                    (waitingMessage instanceof Reply) &&
                            message.getSenderAddress() == waitingMessage.getReceiverAddress());
        }
        else{
            for(RoutingTableEntry entry : router.getRoutingTable().getEntries()){
                entry.incrementTicks(1);
            }
        }
    }

    public List<RTPMessage> getMessagesSentWaitingForReply() {
        return messagesSentWaitingForReply;
    }
}
