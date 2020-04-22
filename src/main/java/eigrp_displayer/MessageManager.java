package eigrp_displayer;

import eigrp_displayer.messages.*;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    List<RTPMessage> messagesSentWaitingForReply;
    //Router field?

    public MessageManager(){
        this.messagesSentWaitingForReply = new ArrayList<>();
    }

    //unicast
    public void sendMessage(RTPMessage message){
        //check if connected
        //schedule message
    }

    //broadcast
    public void sendMessageToAllNeighbours(RTPMessage message){
        //check if connected
        //schedule message
    }

    public void sendCyclicMessage(int delay){
        //mainly Hellos
    }

    public void receiveMessage(){}

    public void respond(RTPMessage message, Router router){
        if(message instanceof QueryMessage){
            QueryMessage query = (QueryMessage)message;
            RoutingTableEntry queriedEntry = null;

            this.sendMessage(new ACKMessage(router.getIp_address(), message.getSenderAddress()));

            for(RoutingTableEntry entry : router.getRoutingTable().getEntries()){
                if(entry.getIp_address() == query.getQueriedDeviceAddress())
                    queriedEntry = entry;
            }

            ReplyMessage replyMessage = new ReplyMessage(message.getReceiverAddress(),
                    message.getSenderAddress(),
                    queriedEntry);
            this.getMessagesSentWaitingForReply().add(replyMessage);
            this.sendMessage(replyMessage);
        }
        else if(message instanceof HelloMessage){
            for(RoutingTableEntry entry : router.getRoutingTable().getEntries()){
                if(entry.getIp_address() == message.getSenderAddress())
                    entry.resetTicks();
            }

        }

        else if(message instanceof ACKMessage){
            this.getMessagesSentWaitingForReply().removeIf(waitingMessage ->
                    (waitingMessage instanceof QueryMessage || waitingMessage instanceof UpdateMessage)
                    && message.getSenderAddress() == waitingMessage.getReceiverAddress());
        }
        else if(message instanceof UpdateMessage){
            this.sendMessage(new ACKMessage(router.getIp_address(), message.getSenderAddress()));
            router.getRoutingTable().update(((UpdateMessage) message).getRoutingTable(), message.getSenderAddress());
        }
        else if(message instanceof ReplyMessage){
            this.getMessagesSentWaitingForReply().removeIf(waitingMessage ->
                    (waitingMessage instanceof ReplyMessage) &&
                            message.getSenderAddress() == waitingMessage.getReceiverAddress());
        }
    }

    public List<RTPMessage> getMessagesSentWaitingForReply() {
        return messagesSentWaitingForReply;
    }
}
