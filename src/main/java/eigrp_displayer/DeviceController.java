package eigrp_displayer;

import eigrp_displayer.messages.*;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DeviceController {
    private Device device;
    private List<Message> messageSchedule;

    public DeviceController() {
        this.clearSchedule();
    }

    public DeviceController(Device device) {
        this();
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<Message> getMessageSchedule() {
        return messageSchedule;
    }

    public void addSelfToScheduler(){
        MessageScheduler.getInstance().getMessageSchedules().add(this.messageSchedule);
        MessageScheduler.getInstance().getControllers().add(this);
    }

    public void sendMessage(Message message, int offset) {
        for (DeviceInterface deviceInterface : new ArrayList<>(Arrays.asList(this.getDevice().getDeviceInterfaces()))) {
            DeviceController deviceController = deviceInterface.getOtherDeviceController(this);

            if (deviceController != null && deviceController.getDevice().getIp_address().equals(
                    message.getReceiverAddress())) {
                for (int i = Clock.getTime() + offset; i < this.messageSchedule.size(); i++) {
                    if (this.messageSchedule.get(i) == null) {
                        this.getMessageSchedule().set(i, message);
                        break;
                    }
                }
            }
        }
    }

    public void sendMessage(Message message) {
        this.sendMessage(message, 0);
    }

    public void sendMessages(List<Message> messages, int offset){
        for(int i = 0; i < messages.size(); i++){
            this.sendMessage(messages.get(i), offset + i);
        }
    }

    public void sendMessages(List<Message> messages){
        this.sendMessages(messages, 0);
    }

    public void sendCyclicMessage(CyclicMessage message, int offset){
        int trimmedOffset;
        if (offset == 0)
            trimmedOffset = 0;
        else if(offset < message.getInterval())
            trimmedOffset = offset;
        else
            trimmedOffset = offset % message.getInterval();
        for(int i = Clock.getTime() + offset; i < this.messageSchedule.size(); i++){
            if((i % message.getInterval()) == trimmedOffset) {
                sendMessage(message.getMessage(), i);
            }
        }
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
                    new HelloMessage(this.getDevice().getIp_address(), ip), 60);
            this.sendCyclicMessage(message, this.device.getMessageSendingTimeOffset());
        }
    }

    //TODO: test
    public void respond(Message message){
        EventLog.messageReceived(this, message);

        if(message instanceof Packet){
            this.sendMessage(new PacketACK((Packet) message), 1);
        }
    }

    public List<DeviceController> getAllConnectedDeviceControllers(){
        List<DeviceInterface> deviceInterfaces = new ArrayList<>(
                Arrays.asList(this.device.getDeviceInterfaces()));
        Iterator<DeviceInterface> iterator = deviceInterfaces.iterator() ;
        List<DeviceController> controllers = new ArrayList<>();

        while(iterator.hasNext()){
            DeviceInterface deviceInterface = iterator.next();
            DeviceController controller = deviceInterface.getOtherDeviceController(this);
            if(controller != null){
                controllers.add(controller);
            }
        }

        return controllers;
    }

    public DeviceController getConnectedDeviceController(IPAddress ipAddress){
        for(DeviceInterface deviceInterface : this.device.getDeviceInterfaces()){
            DeviceController controller = deviceInterface.getConnection().getOtherDevice(this);
            if(controller.getDevice().getIp_address().equals(ipAddress)){
                return controller;
            }
        }
        return null;
    }

    public Connection getConnectionWithDeviceController(DeviceController controller){
        for(DeviceInterface deviceInterface : this.device.getDeviceInterfaces()){
            Connection connection = deviceInterface.getConnection();
            if(connection.getDevice1() == controller || connection.getDevice2() == controller){
                return connection;
            }
        }
        return null;
    }

    public void setConnection(Connection connection){
        for(int i = 0; i < this.device.getDeviceInterfaces().length; i++){
            if(this.device.getDeviceInterfaces()[i].getConnection() == null) {
                this.device.getDeviceInterfaces()[i].setConnection(connection);
                break;
            }
        }
    }

    public void clearSchedule(){
        this.messageSchedule = new ArrayList<>();
        for(int i = 0; i <10000; i++){
            this.messageSchedule.add(null);
        }
    }
}