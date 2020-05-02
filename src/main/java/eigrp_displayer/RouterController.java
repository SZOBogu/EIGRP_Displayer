package eigrp_displayer;

import eigrp_displayer.messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouterController extends DeviceController implements ClockDependent {
    private HashMap<RTPMessage, Integer> messagesSentWaitingForReply;
    private HashMap<RTPMessage, Integer> messagesToTryToSendAgain;

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
    public void respond(RTPMessage message){
        if(message.getReceiverAddress().equals(this.getDevice().getIp_address())) {
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

    public void passMessageFurther(RTPMessage message){
        /**sprawdz wpisy w tablicy routingu
         * jak jest wpis dotyczacy ip to sendnij message dalej w nastepnym kroku schedula
         *
        */
    }

    public void respondACK(ACKMessage ack){
        RTPMessage messageToRemove = null;
        for(RTPMessage message : this.messagesToTryToSendAgain.keySet()){
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
                RComparator comparator = new RComparator();
                if (comparator.compare(this.getDevice(), (Router)otherDeviceController.getDevice())) {

                    this.getDevice().getNeighbourTable().formNeighbourship(
                            this.getInterface(helloMessage.getSenderAddress()), helloMessage.getSenderAddress());

                    UpdateMessage updateMessage = new UpdateMessage(this.getDevice().getIp_address(),
                            helloMessage.getSenderAddress(), this.getDevice().getTopologyTable());
                    this.sendMessage(updateMessage);
                }
            }
            if(otherDeviceController.getDevice() instanceof EndDevice ||
                    otherDeviceController.getDevice() instanceof ExternalNetwork) {
                //TODO: look for unexpected cases of not replying/looping/whatever
                this.getDevice().getNeighbourTable().formNeighbourship(
                        this.getInterface(helloMessage.getSenderAddress()), helloMessage.getSenderAddress());

                //AND make new record in routing and topology tables
                RoutingTableEntry entry = new RoutingTableEntry(
                        otherDeviceController.getDevice().getIp_address());
                entry.setCode("C");

                MetricCalculator calculator = new MetricCalculator();
                Connection connection = this.getConnectionWithDeviceController(otherDeviceController);
                long metric = calculator.calculateMetric(this.getDevice(), connection);
                entry.setFeasibleDistance(metric);
                entry.setReportedDistance(0);
                entry.getPath().add(connection);

                this.update(entry, helloMessage.getSenderAddress());
            }
        }
        else{
            this.getDevice().getNeighbourTable().getEntry(helloMessage.getSenderAddress()).setTicksSinceLastHello(0);
        }
    }

    public void respondQuery(QueryMessage queryMessage){
        this.sendMessage(new ACKMessage(this.getDevice().getIp_address(), queryMessage.getSenderAddress()));
        boolean isLoopedBack = false;
        RTPMessage messageToRemove = null;
        //if query for the same ip address that was queried from this.router then delete, and send back empty reply
        for(RTPMessage message : messagesSentWaitingForReply.keySet()){
            if(message instanceof QueryMessage){
                if(((QueryMessage) message).getQueriedDeviceAddress()
                        .equals(queryMessage.getQueriedDeviceAddress())){
                    messageToRemove = message;
                    isLoopedBack =true;
                    ReplyMessage emptyReply = new ReplyMessage(this.getDevice().getIp_address(),
                            queryMessage.getSenderAddress(), null);
                    this.sendMessage(emptyReply);
                }
            }
        }
        this.messagesSentWaitingForReply.remove(messageToRemove);
        if(!isLoopedBack) {
            for (RoutingTableEntry entry : this.getDevice().getRoutingTable().getEntries()) {
                if (entry.getIp_address().equals(
                        queryMessage.getQueriedDeviceAddress())) {
                    this.sendMessage(new ReplyMessage(this.getDevice().getIp_address(),
                            queryMessage.getSenderAddress(), entry));
                }
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

    public void sendQueryMessages(List<QueryMessage> messages){
        for(int i = 0; i < messages.size(); i++) {
            this.sendMessage(messages.get(i), i);
        }
    }

    public void update(TopologyTable table, IPAddress senderAddress){
        for(RoutingTableEntry entry : table.getEntries()){
            this.update(entry, senderAddress);
        }
    }

    public void update(RoutingTableEntry entry, IPAddress senderAddress){
        this.getDevice().getTopologyTable().update(this, entry, senderAddress);
        RoutingTableEntry bestEntry = this.getDevice().getTopologyTable().getBestEntryForIP(entry.getIp_address());
        this.getDevice().getRoutingTable().update(bestEntry);
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
        StringBuilder string = new StringBuilder(this.getDevice().getTopologyTable().getDescription() + "\n"
                + this.getDevice().getTopologyTable().getCodes() + "\n");

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
                    .append(entry.getPath().get(0).getOtherDevice(this).getDevice().getIp_address())
                    .append(", ").append(this.getInterface(entry).getName()).append("\n");
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
        for(Map.Entry<RTPMessage, Integer> record : this.messagesToTryToSendAgain.entrySet()) {
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

    public HashMap<RTPMessage, Integer> getMessagesToTryToSendAgain() {
        return messagesToTryToSendAgain;
    }

    public void setMessagesToTryToSendAgain(HashMap<RTPMessage, Integer> messagesToTryToSendAgain) {
        this.messagesToTryToSendAgain = messagesToTryToSendAgain;
    }

    public HashMap<RTPMessage, Integer> getMessagesSentWaitingForReply() {
        return messagesSentWaitingForReply;
    }

    public void setMessagesSentWaitingForReply(HashMap<RTPMessage, Integer> messagesSentWaitingForReply) {
        this.messagesSentWaitingForReply = messagesSentWaitingForReply;
    }
}
