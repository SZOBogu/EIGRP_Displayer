package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.HelloMessage;
import eigrp_displayer.messages.NullMessage;
import eigrp_displayer.messages.RTPMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DeviceController {
    private Device device;
    private List<RTPMessage> messageSchedule;

    public DeviceController() {
        this.messageSchedule = new ArrayList<>();
        for(int i = 0; i <10000; i++){
            this.messageSchedule.add(new NullMessage());
        }
        MessageScheduler.getInstance().getSchedule().add(this.messageSchedule);
    }

    public DeviceController(Device device) {
        this();
        this.device = device;
        //schedule hellos
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
        //schedule hellos
    }

    public List<RTPMessage> getMessageSchedule() {
        return messageSchedule;
    }

    public void sendMessage(RTPMessage message, int offset) {
        for (DeviceInterface deviceInterface : new ArrayList<>(Arrays.asList(this.getDevice().getDeviceInterfaces()))) {
            DeviceController deviceController = deviceInterface.getOtherDeviceController(this);

            if (deviceController != null && deviceController.getDevice().getIp_address().equals(
                    message.getReceiverAddress())) {
                for (int i = Clock.getTime() + offset; i < this.messageSchedule.size(); i++) {
                    if (this.messageSchedule.get(i) instanceof NullMessage) {
                        this.getMessageSchedule().set(i, message);
                        break;
                    }
                }
            }
            this.getMessageSchedule().set(Clock.getTime() + offset, message);
        }
    }

    public void sendMessage(RTPMessage message) {
        this.sendMessage(message, 0);
    }

    public void sendMessages(List<RTPMessage> messages, int offset){
        for(int i = 0; i < messages.size(); i++){
            this.sendMessage(messages.get(i), offset + i);
        }
    }

    public void sendMessages(List<RTPMessage> messages){
        this.sendMessages(messages, 0);
    }

    public void sendCyclicMessage(CyclicMessage message, int offset){
        int trimmedOffset = message.getInterval() % offset;
        for(int i = Clock.getTime(); i < this.messageSchedule.size(); i++){
            if((i % message.getInterval()) == trimmedOffset) {
                sendMessage(message.getMessage(), i + trimmedOffset);
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
                    new HelloMessage(this.getDevice().getIp_address(), ip), 15);
            this.sendCyclicMessage(message);
        }
    }


    public void respond(RTPMessage message){
        System.out.println(""); //do not reply, only routers are supposed to do so
    }

    public List<DeviceController> getAllConnectedDeviceControllers(){
        ArrayList<DeviceInterface> deviceInterfaces = new ArrayList(
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
    //TODO: implement, rename, test
    public void updateMetric(Connection connection){
        //przelicz trasy
    }

    public void setConnection(Connection connection){
        for(int i = 0; i < this.device.getDeviceInterfaces().length; i++){
            if(this.device.getDeviceInterfaces()[i].getConnection() == null) {
                this.device.getDeviceInterfaces()[i].setConnection(connection);
                break;
            }
        }
    }
}
