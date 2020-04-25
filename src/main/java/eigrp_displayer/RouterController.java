package eigrp_displayer;

import eigrp_displayer.messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouterController extends DeviceController implements ClockDependent {
    private HashMap<RTPMessage, Integer> messagesSentWaitingForReply;
    private HashMap<RTPMessage, Integer> messagesToTryToSendAgain;

    public RouterController() {
        this.messagesSentWaitingForReply = new HashMap<>();
        this.messagesToTryToSendAgain = new HashMap<>();
        Clock.addClockDependant(this);
    }

    public Router getDevice(){
        return (Router)super.getDevice();
    }

    public void setDevice(Router router){
        super.setDevice(router);
    }

    //unicast
    public void sendMessage(RTPMessage message, int offset) {
        for (DeviceInterface deviceInterface : this.getDevice().getDeviceInterfaces()) {
            Device device = deviceInterface.getConnection().getOtherDevice(this.getDevice());
            IPAddress ip = device.getIp_address();
            if (ip.equals(message.getReceiverAddress()))
                MessageScheduler.getInstance().scheduleMessage(message, offset);
        }
    }

    public void sendMessages(List<QueryMessage> messages, int offset){
        for(QueryMessage query : messages){
            this.sendMessage(query, offset);
        }
    }


    public void sendCyclicMessage(CyclicMessage message, int offset){
        for(DeviceInterface deviceInterface : this.getDevice().getDeviceInterfaces()){
            Device device = deviceInterface.getConnection().getOtherDevice(this.getDevice());
            IPAddress ip = device.getIp_address();
            if(ip.equals(message.getMessage().getReceiverAddress())){
                MessageScheduler.getInstance().scheduleCyclicMessage(message, offset);
            }
        }
    }

    public void sendMessage(RTPMessage message) {
        this.sendMessage(message, 0);
    }

    public void sendMessages(List<QueryMessage> messages){
        this.sendMessages(messages, 0);
    }


    public void sendCyclicMessage(CyclicMessage message){
        this.sendCyclicMessage(message, 0);
    }

    public void scheduleHellos(){
        List<IPAddress> connectedDevicesAddresses = new ArrayList<>();

        for(Device device : this.getDevice().getAllConnectedDevices()){
            connectedDevicesAddresses.add(device.getIp_address());
        }
        for(IPAddress ip : connectedDevicesAddresses){
            CyclicMessage message = new CyclicMessage(
                    new HelloMessage(this.getDevice().getIp_address(), ip), 15);
            this.sendCyclicMessage(message);
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
            if(message instanceof ReplyMessage || message instanceof UpdateMessage){
                if(message.getReceiverAddress().equals(ack.getSenderAddress()))
                    this.messagesToTryToSendAgain.remove(message);
            }
        }
    }

    public void respondHello(HelloMessage helloMessage){
        if(!this.getDevice().getNeighbourTable().checkIfPresent(helloMessage.getSenderAddress())){
            Device otherDevice = null;
            for(Device device : this.getDevice().getAllConnectedDevices()){
                if(device.getIp_address().equals(helloMessage.getSenderAddress())){
                    otherDevice = device;
                    break;
                }
            }

            if(otherDevice instanceof Router) {
                RComparator comparator = new RComparator();
                if (comparator.compare((Router) getDevice().getConnectedDevice(helloMessage.getSenderAddress()),
                        this.getDevice())) {
                    this.getDevice().getNeighbourTable().formNeighbourship(helloMessage.getSenderAddress());
                }
            }
            if(otherDevice instanceof EndDevice || otherDevice instanceof Network) {
                //TODO: look for unexpected cases of not replying/looping/whatever
                this.getDevice().getNeighbourTable().formNeighbourship(helloMessage.getSenderAddress());
                //AND make new record in routing and topology tables
                RoutingTableEntry entry = new RoutingTableEntry(otherDevice.getIp_address());
                entry.setCode("C");

                MetricCalculator calculator = new MetricCalculator();
                Connection connection = this.getDevice().getConnectionWithDevice(otherDevice);
                long metric = calculator.calculateMetric(this.getDevice(), connection);
                entry.setFeasibleDistance(metric);
                entry.setReportedDistance(0);
                entry.getPath().add(connection);

                this.getDevice().getRoutingTable().getEntries().add(entry);
                this.getDevice().getTopologyTable().getEntries().add(entry);

                UpdateMessage updateMessage = new UpdateMessage(this.getDevice().getIp_address(),
                        helloMessage.getSenderAddress(), this.getDevice().getTopologyTable());
            }
        }
    }

    public void respondQuery(QueryMessage queryMessage){
        this.sendMessage(new ACKMessage(this.getDevice().getIp_address(), queryMessage.getSenderAddress()));
        boolean isReplySent = false;
        boolean isLoopedBack = false;

        List<RoutingTableEntry> entries = this.getDevice().getRoutingTable().getEntries();

        //if query for the same ip address that was queried from this.router then delete, and send back empty reply
        for(RTPMessage message : messagesSentWaitingForReply.keySet()){
            if(message instanceof QueryMessage){
                if(((QueryMessage) message).getQueriedDeviceAddress().equals(queryMessage.getQueriedDeviceAddress())){
                    messagesSentWaitingForReply.remove(message);
                    isLoopedBack =true;
                    ReplyMessage emptyReply = new ReplyMessage(this.getDevice().getIp_address(),
                            queryMessage.getSenderAddress(), null);
                    this.sendMessage(emptyReply);
                }
            }
        }
        if(!isLoopedBack) {
            for (RoutingTableEntry entry : entries) {
                if (entry.getIp_address().equals(
                        queryMessage.getQueriedDeviceAddress())) {
                    this.sendMessage(new ReplyMessage(this.getDevice().getIp_address(),
                            queryMessage.getSenderAddress(), entry));
                    isReplySent = true;
                }
            }
            //ask neighbours for info ?
            if(!isReplySent) {
                List<QueryMessage> queryMessages = new ArrayList<>();
                List<Device> allNeighboursButSenderOfQuery =
                        this.getDevice().getAllNeighboursButOne(queryMessage.getSenderAddress());
                for (Device device : allNeighboursButSenderOfQuery) {
                    QueryMessage qmsg = new QueryMessage(
                            this.getDevice().getIp_address(),
                            device.getIp_address(),
                            queryMessage.getQueriedDeviceAddress());
                    queryMessages.add(qmsg);
                }
                this.sendMessages(queryMessages);
            }
        }
    }

    public void respondUpdate(UpdateMessage updateMessage){
        this.sendMessage(new ACKMessage(this.getDevice().getIp_address(), updateMessage.getSenderAddress()));
        this.getDevice().update(updateMessage.getTopologyTable(),
                updateMessage.getSenderAddress());
    }

    public void respondReply(ReplyMessage replyMessage){
        //ack, delete query from messages waiting for reply, update routing tables
        this.sendMessage(new ACKMessage(this.getDevice().getIp_address(), replyMessage.getSenderAddress()));

        for(RTPMessage message : this.messagesSentWaitingForReply.keySet()){
            if(message instanceof QueryMessage){
                if(((QueryMessage) message).getQueriedDeviceAddress().equals(
                        replyMessage.getRoutingTableEntry().getIp_address())){
                    this.messagesSentWaitingForReply.remove(message);
                }
            }
        }
        this.getDevice().update(replyMessage.getRoutingTableEntry(),
                replyMessage.getSenderAddress());
    }

    public HashMap<RTPMessage, Integer> getMessagesSentWaitingForReply() {
        return messagesSentWaitingForReply;
    }

    @Override
    public void updateTime() {
        //TODO: fix for various cases
        this.messagesSentWaitingForReply.replaceAll((k, v) -> v + 1);
        while(this.messagesSentWaitingForReply.containsValue(17)){
            this.messagesSentWaitingForReply.values().removeIf(val -> 17 == val);
        }
    }
}
