package eigrp_displayer;

import eigrp_displayer.messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageManager implements ClockDependent {
    private HashMap<RTPMessage, Integer> messagesSentWaitingForReply;
    private HashMap<RTPMessage, Integer> messagesToTryToSendAgain;
    private Router router;

    public MessageManager() {
        this.messagesSentWaitingForReply = new HashMap<>();
        this.messagesToTryToSendAgain = new HashMap<>();
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    //unicast
    public void sendMessage(RTPMessage message) {
        //TODO: fix for devices
        for (DeviceInterface deviceInterface : this.router.getDeviceInterfaces()) {
            Router router = (Router) deviceInterface.getConnection().getOtherDevice(this.router);
            IPAddress ip = router.getIp_address();
            if (ip.equals(message.getReceiverAddress()))
                MessageScheduler.getInstance().scheduleMessage(message);
        }
    }

    //multicast?
    public void sendMessageToAllNeighboursBut(RTPMessage message, IPAddress ipAddress) {
        List<Device> allButOneNeigbours = this.router.getAllNeighbours();
        for (Device device : allButOneNeigbours) {
            if (allButOneNeigbours.get(allButOneNeigbours.indexOf(device)).
                    getIp_address().equals(ipAddress)) {
                allButOneNeigbours.remove(device);
                break;
            }
        }
        //TODO: deep copy of message with replaced receiver address according to list
        //no way this is good solution
        if (message instanceof QueryMessage) {
            List<QueryMessage> queryMessages = new ArrayList<>();
            for (Device device : allButOneNeigbours) {
                QueryMessage queryMessage = new QueryMessage((QueryMessage) message);
                queryMessage.setReceiverAddress(device.getIp_address());
                queryMessages.add(new QueryMessage(queryMessage));
            }
            for (QueryMessage queryMessage : queryMessages) {
                this.sendMessage(queryMessage);
            }
        } else if (message instanceof HelloMessage) {
            List<HelloMessage> helloMessages = new ArrayList<>();
            for (Device device : allButOneNeigbours) {
                HelloMessage helloMessage = new HelloMessage((HelloMessage) message);
                helloMessage.setReceiverAddress(device.getIp_address());
                helloMessages.add(new HelloMessage(helloMessage));
            }
            for (HelloMessage helloMessage : helloMessages) {
                this.sendMessage(helloMessage);
            }
        } else if (message instanceof ACKMessage) {
            List<ACKMessage> ackMessages = new ArrayList<>();
            for (Device device : allButOneNeigbours) {
                ACKMessage ackMessage = new ACKMessage((ACKMessage) message);
                ackMessage.setReceiverAddress(device.getIp_address());
                ackMessages.add(new ACKMessage(ackMessage));
            }
            for (ACKMessage queryMessage : ackMessages) {
                this.sendMessage(queryMessage);
            }
        } else if (message instanceof UpdateMessage) {
            List<UpdateMessage> updateMessages = new ArrayList<>();
            for (Device device : allButOneNeigbours) {
                UpdateMessage updateMessage = new UpdateMessage((UpdateMessage) message);
                updateMessage.setReceiverAddress(device.getIp_address());
                updateMessages.add(new UpdateMessage(updateMessage));
            }
            for (UpdateMessage updateMessage : updateMessages) {
                this.sendMessage(updateMessage);
            }
        } else if (message instanceof ReplyMessage) {
            List<ReplyMessage> replyMessages = new ArrayList<>();
            for (Device device : allButOneNeigbours) {
                ReplyMessage replyMessage = new ReplyMessage((ReplyMessage) message);
                replyMessage.setReceiverAddress(device.getIp_address());
                replyMessages.add(new ReplyMessage(replyMessage));
            }
            for (ReplyMessage replyMessage : replyMessages) {
                this.sendMessage(replyMessage);
            }
        }
        this.sendMessage(message);
    }

    //broadcast
    public void sendMessageToAllNeighbours(RTPMessage message){
        //check if connected
        //schedule message
    }

    //broadcast
    public void sendMessageOnAllInterfaces(RTPMessage message){
        //check if connected
        //schedule message
    }

    public void sendCyclicMessage(CyclicMessage message){
        //TODO: fix for devices
        for(DeviceInterface deviceInterface : this.router.getDeviceInterfaces()){
            Router router = (Router)deviceInterface.getConnection().getOtherDevice(this.router);
            IPAddress ip = router.getIp_address();
            if(ip.equals(message.getMessage().getReceiverAddress())){
                MessageScheduler.getInstance().scheduleCyclicMessage(message);
            }
        }
    }

    public void respond(RTPMessage message){
        if(message instanceof QueryMessage){
            this.respondQuery((QueryMessage)message);
        }
        else if(message instanceof HelloMessage){
            this.respondHello((HelloMessage)message);
        }
        else if(message instanceof ACKMessage){
            this.respondACK((ACKMessage)message);
        }
        else if(message instanceof UpdateMessage){
            this.respondUpdate((UpdateMessage)message);
        }
        else if(message instanceof ReplyMessage) {
            this.respondReply((ReplyMessage)message);
        }
    }

    public void respondACK(ACKMessage ack){
        for(RTPMessage message : this.messagesToTryToSendAgain.keySet()){
            if(message instanceof QueryMessage || message instanceof UpdateMessage){
                if(message.getReceiverAddress().equals(ack.getSenderAddress()))
                    this.messagesToTryToSendAgain.remove(message);
            }
        }
    }

    public void respondHello(HelloMessage helloMessage){
        if(!this.router.getNeighbourTable().checkIfPresent(helloMessage.getSenderAddress())){
            RComparator comparator = new RComparator();
            //TODO: cases other than routers on both ends
            if(comparator.compare((Router)router.getConnectedDevice(helloMessage.getSenderAddress()) ,
                    this.router)){
                this.router.getNeighbourTable().formNeighbourship(helloMessage.getSenderAddress());
            }
        }
    }

    public void respondQuery(QueryMessage queryMessage){
        //Wysłanie ACK, odpowiedz z danymi o ip jesli mamy, jesli nie mamy pytamy sasiadow (co jesli dojedzie do loopa?)
        //przy loopie jesli dostanie sie z innego zrodla ten sam request to odeslij ze ni ma takiego
        this.sendMessage(new ACKMessage(this.router.getIp_address(), queryMessage.getSenderAddress()));
    }

    public void respondUpdate(UpdateMessage updateMessage){
        //porownaj entry z tablica routingu, te ktore nie pasuja do wczesniejszej wersji popraw
        this.sendMessage(new ACKMessage(this.router.getIp_address(), updateMessage.getSenderAddress()));
        this.router.getRoutingTable().update(updateMessage.getRoutingTable(),
                updateMessage.getSenderAddress());
    }

    public void respondReply(ReplyMessage replyMessage){
        //ack (?) dostosuj wpisy w tablicach routingu
        this.sendMessage(new ACKMessage(this.router.getIp_address(), replyMessage.getSenderAddress()));
        this.router.getRoutingTable().update(replyMessage.getRoutingTableEntry(),
                replyMessage.getSenderAddress());
    }

    public HashMap<RTPMessage, Integer> getMessagesSentWaitingForReply() {
        return messagesSentWaitingForReply;
    }

    @Override
    public void updateTime() {
        this.messagesSentWaitingForReply.replaceAll((k, v) -> v + 1);
        while(this.messagesSentWaitingForReply.containsValue(17)){
            this.messagesSentWaitingForReply.values().removeIf(val -> 17 == val);
        }
    }
}
