package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.HelloMessage;
import eigrp_displayer.messages.QueryMessage;
import eigrp_displayer.messages.RTPMessage;

import java.util.ArrayList;
import java.util.List;

public class DeviceController {
    private Device device;

    public DeviceController() {}
    public DeviceController(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    //unicast
    public void sendMessage(RTPMessage message, int offset) {
        for (DeviceInterface deviceInterface : this.device.getDeviceInterfaces()) {
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
        for(DeviceInterface deviceInterface : this.device.getDeviceInterfaces()){
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
                    new HelloMessage(this.device.getIp_address(), ip), 15);
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
    public Connection getConnectionWithDevice(DeviceController controller){
        for(DeviceInterface deviceInterface : this.device.getDeviceInterfaces()){
            Connection connection = deviceInterface.getConnection();
            if(connection == this.getConnectionWithDevice(controller)){
                return connection;
            }
        }
        return null;
    }
    //TODO: implement, rename, test
    public void updateMetric(Connection connection){
        //przelicz trasy
    }

    //TODO: rewrite
    public void setConnection(Connection connection){
        for(int i = 0; i < this.device.getDeviceInterfaces().length; i++){
            if(this.device.getDeviceInterfaces()[i].getConnection() == null) {
                this.device.getDeviceInterfaces()[i].setConnection(connection);
                break;
            }
        }
    }
}
