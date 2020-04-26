package eigrp_displayer;

import eigrp_displayer.messages.*;

import java.util.ArrayList;
import java.util.List;

public class DeviceController {
    private Device device;
    private List<RTPMessage> messageSchedule;

    public DeviceController() {
        this.messageSchedule = new ArrayList<>();
        for(int i = 0; i <10000; i++){
            messageSchedule.add(new NullMessage());
        }
        MessageScheduler.getInstance().getSchedule().add(this.messageSchedule);
    }

    public DeviceController(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<RTPMessage> getMessageSchedule() {
        return messageSchedule;
    }

    public void setMessageSchedule(List<RTPMessage> messageSchedule) {
        this.messageSchedule = messageSchedule;
    }

    public void sendMessage(RTPMessage message, int offset) {
        for (DeviceInterface deviceInterface : this.getDevice().getDeviceInterfaces()) {
            Device device = deviceInterface.getConnection().getOtherDevice(this).getDevice();
            IPAddress ip = device.getIp_address();
            if (ip.equals(message.getReceiverAddress()) &&
                    Clock.getTime() + offset < this.getMessageSchedule().size()) {

                this.getMessageSchedule().remove(Clock.getTime() + offset);
                this.getMessageSchedule().add(Clock.getTime() + offset, message);
            }
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
                for(int i = Clock.getTime() ; i < this.getMessageSchedule().size(); i++){
                    if(i + offset % message.getInterval() == 0) {
                        this.getMessageSchedule().remove(Clock.getTime());
                        this.getMessageSchedule().add(Clock.getTime(), message.getMessage());
                    }
                }
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
        System.out.println(""); //do not reply
    }


    public List<DeviceController> getAllConnectedDeviceControllers(){
        List<DeviceController> deviceControllers = new ArrayList<>();
        try {
            for (DeviceInterface deviceInterface : this.device.getDeviceInterfaces()) {
                DeviceController controller = deviceInterface.getConnection().getOtherDevice(this);
                if (controller != null) {
                    deviceControllers.add(controller);
                }
            }
            return deviceControllers;
        }
        catch (Exception e) {
            return deviceControllers;
        }
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

    //TODO: test
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
