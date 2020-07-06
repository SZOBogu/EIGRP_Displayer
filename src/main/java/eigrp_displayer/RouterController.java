package eigrp_displayer;

import eigrp_displayer.messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouterController extends DeviceController implements ClockDependent {
    private HashMap<Message, Integer> messagesSentWaitingForReply;
    private HashMap<Message, Integer> messagesToTryToSendAgain;

    public RouterController() {
        super();
        this.messagesSentWaitingForReply = new HashMap<>();
        this.messagesToTryToSendAgain = new HashMap<>();
        Clock.addClockDependant(this);
    }

    public RouterController(Router router) {
        this();
        this.setDevice(router);
    }

    @Override
    public void scheduleCyclicMessages(){
        List<IPAddress> connectedDevicesAddresses = new ArrayList<>();

        for(DeviceController controller : this.getAllConnectedDeviceControllers()){
            connectedDevicesAddresses.add(controller.getDevice().getIp_address());
        }
        for(IPAddress ip : connectedDevicesAddresses){
            CyclicMessage message = new CyclicMessage(
                    new HelloMessage(this.getDevice().getIp_address(), ip), 60);
            this.sendCyclicMessage(message, this.getDevice().getMessageSendingTimeOffset());
        }
    }

    @Override
    public void respond(Message message){
        EventLog.messageReceived(this, message);

        if(message.getTargetAddress().equals(this.getDevice().getIp_address())) {
            if (message instanceof QueryMessage) {
                this.respondQuery((QueryMessage) message);
            } else if (message instanceof HelloMessage) {
                this.respondHello((HelloMessage) message);
            } else if (message instanceof ACKMessage) {
                this.respondACK((ACKMessage) message);
            } else if (message instanceof UpdateMessage) {
                this.respondUpdate((UpdateMessage) message);
            } else if (message instanceof ReplyMessage) {
                this.respondReply((ReplyMessage) message);
            }
        }
        else{
            this.passMessageFurther(message);
        }
    }

    public void passMessageFurther(Message message){
        RoutingTableEntry entry = this.getDevice().getRoutingTable().getEntry(message.getTargetAddress());
        if(entry != null) {
            List<IPAddress> ips = entry.getIPAddressPath(this);
            if (ips.size() > 1) {
                message.setSenderAddress(this.getDevice().getIp_address());
                message.setReceiverAddress(ips.get(1));
                this.sendMessage(message, 1);
            }
        }
    }

    public void respondACK(ACKMessage ack){
        Message messageToRemove = null;
        for(Message message : this.messagesToTryToSendAgain.keySet()){
            if(message instanceof ReplyMessage || message instanceof UpdateMessage){
                if(message.getReceiverAddress().equals(ack.getSenderAddress())) {
                    messageToRemove = message;
                }
            }
        }
        this.messagesToTryToSendAgain.remove(messageToRemove);
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

            assert otherDeviceController != null;
            if(otherDeviceController.getDevice() instanceof Router) {
                if (RComparator.compare(this.getDevice(), (Router)otherDeviceController.getDevice())) {

                    this.getDevice().getNeighbourTable().formNeighbourship(
                            this.getInterface(helloMessage.getSenderAddress()), helloMessage.getSenderAddress());

                    EventLog.neighbourshipFormed(this, otherDeviceController);

                    UpdateMessage updateMessage = new UpdateMessage(this.getDevice().getIp_address(),
                            helloMessage.getSenderAddress(), this.getDevice().getTopologyTable());

                    CyclicMessage cyclicMessage = new CyclicMessage(updateMessage, 60);
                    this.sendMessage(updateMessage, 1);
                    //TODO: cleanup updatow po zerwaniu sasiedztwa
                    this.sendCyclicMessage(cyclicMessage, 100);
                }
            }
            if(otherDeviceController.getDevice() instanceof EndDevice ||
                    otherDeviceController.getDevice() instanceof ExternalNetwork) {

                this.getDevice().getNeighbourTable().formNeighbourship(
                        this.getInterface(helloMessage.getSenderAddress()), helloMessage.getSenderAddress());

                EventLog.neighbourshipFormed(this, otherDeviceController);

                //AND make new record in routing and topology tables
                RoutingTableEntry entry = new RoutingTableEntry(
                        otherDeviceController.getDevice().getIp_address());
                entry.setCode("C");

               Connection connection = this.getConnectionWithDeviceController(otherDeviceController);
                long metric = MetricCalculator.calculateMetric(this.getDevice(), connection);
                entry.setFeasibleDistance(metric);
                entry.setReportedDistance(0);

                List<Connection> connections = new ArrayList<>();
                connections.add(connection);
                entry.setPath(connections);

                this.getDevice().getTopologyTable().getEntries().add(entry);
                RoutingTableEntry bestEntry = this.getDevice().getTopologyTable().getBestEntryForIP(entry.getIp_address());
                this.getDevice().getRoutingTable().getEntries().add(bestEntry);
            }
        }
        else{
            this.getDevice().getNeighbourTable().getEntry(helloMessage.getSenderAddress()).setTicksSinceLastHello(0);
        }
    }

    public void respondQuery(QueryMessage queryMessage){
        this.sendMessage(new ACKMessage(this.getDevice().getIp_address(), queryMessage.getSenderAddress()));
        boolean isLoopedBack = false;
        Message messageToRemove = null;
        //if query for the same ip address that was queried from this.router then delete, and send back empty reply
        for(Message message : messagesSentWaitingForReply.keySet()){
            if(message instanceof QueryMessage){
                if(((QueryMessage) message).getQueriedDeviceAddress()
                        .equals(queryMessage.getQueriedDeviceAddress())){
                    messageToRemove = message;
                    isLoopedBack = true;
                    ReplyMessage emptyReply = new ReplyMessage(this.getDevice().getIp_address(),
                            queryMessage.getSenderAddress(), null);
                    this.sendMessage(emptyReply, 1);
                }
            }
        }
        this.messagesSentWaitingForReply.remove(messageToRemove);
        if(!isLoopedBack) {
            for (RoutingTableEntry entry : this.getDevice().getRoutingTable().getEntries()) {
                if (entry.getIp_address().equals(
                        queryMessage.getQueriedDeviceAddress())) {
                    this.sendMessage(new ReplyMessage(this.getDevice().getIp_address(),
                            queryMessage.getSenderAddress(), entry), 1);
                }
            }
        }
    }

    public void respondUpdate(UpdateMessage updateMessage){
        this.sendMessage(new ACKMessage(this.getDevice().getIp_address(), updateMessage.getSenderAddress()), 1);
        this.update(updateMessage);
    }

    public void respondReply(ReplyMessage replyMessage){
        //ack, delete query from messages waiting for reply, update routing tables
        this.sendMessage(new ACKMessage(this.getDevice().getIp_address(), replyMessage.getSenderAddress()), 1);

        for(Message message : this.messagesSentWaitingForReply.keySet()){
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

    public void sendQueryMessages(List<QueryMessage> messages){
        for (QueryMessage message : messages) {
            super.sendMessage(message, 1);
        }
    }

    public void update(UpdateMessage updateMessage){
        for(RoutingTableEntry entry : updateMessage.getTopologyTable().getEntries()){
            this.update(entry, updateMessage.getSenderAddress());
        }
    }

    public void update(RoutingTableEntry entry, IPAddress senderAddress){
        this.getDevice().getTopologyTable().update(this, entry, senderAddress);
        RoutingTableEntry bestEntry = this.getDevice().getTopologyTable().getBestEntryForIP(entry.getIp_address());
        this.getDevice().getRoutingTable().update(bestEntry);
    }


    public void severNeighbourship(IPAddress ipOfFormerNeighbour){
        Router router = this.getDevice();
        router.getNeighbourTable().removeNeighbourship(ipOfFormerNeighbour);
        router.getTopologyTable().deleteNeighbourEntries(this, ipOfFormerNeighbour);
        List<RoutingTableEntry> routingTableEntries = router.getRoutingTable().getEntries();
        routingTableEntries.remove(router.getRoutingTable().getEntry(ipOfFormerNeighbour));
        EventLog.neighbourshipBroken(this, ipOfFormerNeighbour);
        List<QueryMessage> queryMessageList = new ArrayList<>();
        for(DeviceController controller : this.getAllNeighbourControllers()){
            QueryMessage queryMessage = new QueryMessage(router.getIp_address(),
                    controller.getDevice().getIp_address(), ipOfFormerNeighbour);
            queryMessageList.add(queryMessage);
        }

        this.sendQueryMessages(queryMessageList);
    }

    public List<DeviceController> getAllNeighbourControllers(){
        List<DeviceController> deviceControllers = this.getAllConnectedDeviceControllers();
        List<IPAddress> ips = this.getDevice().getNeighbourTable().getAllNeighboursAddresses();
        List<DeviceController> neighbourControllers = new ArrayList<>();

        for(DeviceController controller : deviceControllers){
            if(ips.contains(controller.getDevice().getIp_address())){
                neighbourControllers.add(controller);
            }
        }
        return neighbourControllers;
    }

    public List<DeviceController> getAllNeighbourControllersButOne(IPAddress ipAddress){
        List<DeviceController> deviceControllers = this.getAllNeighbourControllers();
        deviceControllers.removeIf(controller -> controller.getDevice().getIp_address().equals(ipAddress));
        return deviceControllers;
    }


    public IPAddress getAddressOfNextDeviceOnPath(IPAddress targetIP){
        Router device = this.getDevice();
        RoutingTable routingTable = device.getRoutingTable();
        RoutingTableEntry entry = routingTable.getEntry(targetIP);
        List<Connection> connections = entry.getPath();
        Connection connection = connections.get(0);
        DeviceController controller = connection.getOtherDevice(this);
        Device device0 = controller.getDevice();

        return device0.getIp_address();
    }

    public DeviceInterface getInterface(RoutingTableEntry entry){
        return this.getInterface(entry.getIp_address());
    }

    public DeviceInterface getInterface(IPAddress ipAddress){
        for(DeviceInterface deviceInterface : this.getDevice().getDeviceInterfaces()){
            if(deviceInterface.checkIfOtherDeviceControllerConnected(this)){
                Device device = deviceInterface.getOtherDeviceController(this).getDevice();
                if(device!=null && device.getIp_address().equals(ipAddress)){
                    return deviceInterface;
                }
            }
        }
        return null;
    }

    public String printTopologyTable() {
        List<IPAddress> alreadyPrintedIPs = new ArrayList<>();
        StringBuilder string = new StringBuilder(this.getDevice().getTopologyTable().getDescription() + "\n"
                + this.getDevice().getTopologyTable().getCodes() + "\n");

        for(RoutingTableEntry entry : this.getDevice().getTopologyTable().getEntries()){
            if(!alreadyPrintedIPs.contains(entry.getIp_address()) ) {
                string.append(entry.getCode()).append(" ").append(entry.getIp_address()).append("/")
                        .append(MessageScheduler.getInstance().getNetwork().getMask().getMask()).append(", ")
                        .append(this.getDevice().getTopologyTable().getSuccessorCount(entry.getIp_address()))
                        .append(" successors, FD is ").append(entry.getFeasibleDistance()).append("\n");

                List<RoutingTableEntry> entriesToShow = new ArrayList<>();
                RoutingTableEntry bestEntry = this.getDevice().getTopologyTable().getBestEntryForIP(entry.getIp_address());
                RoutingTableEntry feasibleSuccessorEntry = this.getDevice().getTopologyTable().getFeasibleSuccessorEntry(entry.getIp_address());
                if(bestEntry != null)
                    entriesToShow.add(bestEntry);
                if(feasibleSuccessorEntry != null)
                    entriesToShow.add(feasibleSuccessorEntry);

                for (RoutingTableEntry rtEntry : entriesToShow) {
                    for (DeviceInterface deviceInterface : this.getDevice().getDeviceInterfaces()) {
                        if (deviceInterface.getConnection() != null &&
                                rtEntry.getIp_address().equals(
                                        deviceInterface.getConnection().getOtherDevice(this).getDevice().getIp_address()))

                            string.append("\tvia ").append(rtEntry.getIp_address()).append(" ").append("(")
                                    .append(rtEntry.getFeasibleDistance()).append("\\")
                                    .append(rtEntry.getReportedDistance()).append(") ")
                                    .append(deviceInterface.getName()).append("\n");
                    }
                }
                alreadyPrintedIPs.add(entry.getIp_address());
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
            if(controller.getDevice() instanceof ExternalNetwork){
                isVariablySubnetted = true;
                masks.add(((ExternalNetwork) controller.getDevice()).getMask());
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
                    .append("placeholder ip")
//                    .append(entry.getPath().get(0).getOtherDevice(this).getDevice().getIp_address())
                    .append(", ").append("Interface").append("\n");
            //this.getInterface(entry).getName()
        }
        return string.toString();
    }

    @Override
    public void updateTime() {
        this.messagesSentWaitingForReply.replaceAll((k, v) -> v + 1);
        this.messagesToTryToSendAgain.replaceAll((k, v) -> v + 1);
        while(this.messagesSentWaitingForReply.containsValue(17)){
            this.messagesSentWaitingForReply.values().removeIf(val -> 17 == val);
        }
        for(Map.Entry<Message, Integer> record : this.messagesToTryToSendAgain.entrySet()) {
            if(record.getValue() >= 15){
                record.setValue(0);
                this.sendMessage(record.getKey());
            }
        }
    }

    public Router getDevice(){
        return (Router)super.getDevice();
    }

    public void setDevice(Router router){
        super.setDevice(router);
    }

    public HashMap<Message, Integer> getMessagesToTryToSendAgain() {
        return messagesToTryToSendAgain;
    }

    public void setMessagesToTryToSendAgain(HashMap<Message, Integer> messagesToTryToSendAgain) {
        this.messagesToTryToSendAgain = messagesToTryToSendAgain;
    }

    public HashMap<Message, Integer> getMessagesSentWaitingForReply() {
        return messagesSentWaitingForReply;
    }

    public void setMessagesSentWaitingForReply(HashMap<Message, Integer> messagesSentWaitingForReply) {
        this.messagesSentWaitingForReply = messagesSentWaitingForReply;
    }
}