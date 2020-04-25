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

    public RouterController(Router router) {
        this();
        this.setDevice(router);
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
            Device device = deviceInterface.getConnection().getOtherDevice(this).getDevice();
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
            Device device = deviceInterface.getConnection().getOtherDevice(this).getDevice();
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

        for(DeviceController controller : this.getAllConnectedDeviceControllers()){
            connectedDevicesAddresses.add(controller.getDevice().getIp_address());
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
            DeviceController otherDeviceController = null;
            for(DeviceController controller : this.getAllConnectedDeviceControllers()){
                if(controller.getDevice().getIp_address().equals(helloMessage.getSenderAddress())){
                    otherDeviceController = controller;
                    break;
                }
            }

            if(otherDeviceController.getDevice() instanceof Router) {
                RComparator comparator = new RComparator();
                if (comparator.compare(this.getDevice(), (Router)otherDeviceController.getDevice())) {
                    this.getDevice().getNeighbourTable().formNeighbourship(helloMessage.getSenderAddress());
                }
            }
            if(otherDeviceController.getDevice() instanceof EndDevice ||
                    otherDeviceController.getDevice() instanceof Network) {
                //TODO: look for unexpected cases of not replying/looping/whatever
                this.getDevice().getNeighbourTable().formNeighbourship(helloMessage.getSenderAddress());
                //AND make new record in routing and topology tables
                RoutingTableEntry entry = new RoutingTableEntry(
                        otherDeviceController.getDevice().getIp_address());
                entry.setCode("C");

                MetricCalculator calculator = new MetricCalculator();
                Connection connection = this.getConnectionWithDevice(otherDeviceController);
                long metric = calculator.calculateMetric(this.getDevice(), connection);
                entry.setFeasibleDistance(metric);
                entry.setReportedDistance(0);
                entry.getPath().add(connection);

                this.getDevice().getRoutingTable().getEntries().add(entry);
                this.getDevice().getTopologyTable().getEntries().add(entry);

                UpdateMessage updateMessage = new UpdateMessage(this.getDevice().getIp_address(),
                        helloMessage.getSenderAddress(), this.getDevice().getTopologyTable());
                this.sendMessage(updateMessage);
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
                List<DeviceController> allNeighboursButSenderOfQuery =
                        this.getAllNeighboursButOne(queryMessage.getSenderAddress());
                for (DeviceController controller : allNeighboursButSenderOfQuery) {
                    QueryMessage qmsg = new QueryMessage(
                            this.getDevice().getIp_address(),
                            controller.getDevice().getIp_address(),
                            queryMessage.getQueriedDeviceAddress());
                    queryMessages.add(qmsg);
                }
                this.sendMessages(queryMessages);
            }
        }
    }

    public void respondUpdate(UpdateMessage updateMessage){
        this.sendMessage(new ACKMessage(this.getDevice().getIp_address(), updateMessage.getSenderAddress()));
        this.update(updateMessage.getTopologyTable(),
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
        this.update(replyMessage.getRoutingTableEntry(),
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

    public List<DeviceController> getAllNeighbours(){
        List<DeviceController> deviceControllers = new ArrayList<>();
        List<IPAddress> ips = this.getDevice().getNeighbourTable().getAllNeighboursAddresses();
        try {
            for (DeviceInterface deviceInterface : this.getDevice().getDeviceInterfaces()) {
                DeviceController controller = deviceInterface.getConnection().getOtherDevice(this);
                if (controller != null && ips.contains(controller.getDevice().getIp_address())) {
                    deviceControllers.add(controller);
                }
            }
            return deviceControllers;
        }
        catch (Exception e){
            return deviceControllers;
        }
    }

    public List<DeviceController> getAllNeighboursButOne(IPAddress ipAddress){
        List<DeviceController> deviceControllers = this.getAllNeighbours();
        deviceControllers.removeIf(controller -> controller.getDevice().getIp_address().equals(ipAddress));
        return deviceControllers;
    }

    //TODO: tests
    public void update(RoutingTableEntry entry, IPAddress senderAddress){
        this.getDevice().getTopologyTable().update(this, entry, senderAddress);
        RoutingTableEntry bestEntry = this.getDevice().getTopologyTable().getBestEntryForIP(senderAddress);
        this.getDevice().getRoutingTable().update(bestEntry);
    }

    public void update(TopologyTable table, IPAddress senderAddress){
        for(RoutingTableEntry entry : table.getEntries()){
            this.update(entry, senderAddress);
        }
    }

    //TODO:test
    public DeviceInterface getInterface(RoutingTableEntry entry){
        for(DeviceInterface deviceInterface : this.getDevice().getDeviceInterfaces()){
            if(deviceInterface.getConnection().getOtherDevice(this).getDevice().getIp_address()
                    == entry.getIp_address()){
                return deviceInterface;
            }
        }
        return null;
    }

    //TODO: test (have fun dawg)
    public String printTopologyTable() {
        StringBuilder string = new StringBuilder(this.getDevice().getTopologyTable().getDescription() + "\n\n"
                + this.getDevice().getTopologyTable().getCodes() + "\n\n");

        for(RoutingTableEntry entry : this.getDevice().getTopologyTable().getEntries()){

            string.append(entry.getCode()).append(" ").append(entry.getIp_address()).append("/")
                    .append(MessageScheduler.getInstance().getNetwork().getMask()).append(", ")
                    .append(this.getDevice().getTopologyTable().getSuccessorCount(entry.getIp_address()))
                    .append(" successors, FD is").append(entry.getFeasibleDistance()).append("\n");

            List<RoutingTableEntry> successorEntries = this.getDevice().getTopologyTable().getSuccessorEntriesForIP(entry.getIp_address());
            for(RoutingTableEntry rtEntry : successorEntries){
                for(DeviceInterface deviceInterface : this.getDevice().getDeviceInterfaces()) {
                    if(rtEntry.getIp_address().equals(
                            deviceInterface.getConnection().getOtherDevice(this).getDevice().getIp_address()))
                        string.append("\tvia ").append(rtEntry.getIp_address()).append(" ").append("(")
                                .append(entry.getFeasibleDistance()).append("\\")
                                .append(entry.getReportedDistance()).append(" ")
                                .append(deviceInterface.getName());
                }
            }
        }
        return string.toString();
    }

    public String printRoutingTable(){
        boolean isVariablySubnetted = false;
        List<Mask> masks = new ArrayList<>();
        Mask thisNetworkMask = MessageScheduler.getInstance().getNetwork().getMask();
        masks.add(thisNetworkMask);
        int subnetCount = 0;

        for(DeviceController controller : this.getAllConnectedDeviceControllers()){
            if(controller.getDevice() instanceof Network){
                isVariablySubnetted = true;
                masks.add(((Network) controller.getDevice()).getMask());
                subnetCount++;
            }
        }
        StringBuilder string = new StringBuilder(this.getDevice().getIp_address() + "\\");
        string.append(thisNetworkMask.getMask()).append(" ");

        if(isVariablySubnetted)
            string.append("is ");
        else
            string.append("isn't ");
        string.append("variably subnetted, ").append(subnetCount)
                .append(" subnets, ").append(masks.size()).append(" masks\n");

        for(RoutingTableEntry entry : this.getDevice().getRoutingTable().getEntries()){
            string.append(entry.getCode()).append("\t")
                    .append(entry.getIp_address()).append("[").append(entry.getReportedDistance())
                    .append("\\").append(entry.getFeasibleDistance()).append("] via ")
                    .append(entry.getPath().get(0).getOtherDevice(this).getDevice().getIp_address())
                    .append(", ").append(this.getInterface(entry).getName()).append("\n");
        }
        return string.toString();
    }
}
