package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.HelloMessage;
import eigrp_displayer.messages.QueryMessage;
import eigrp_displayer.messages.RTPMessage;

import java.util.ArrayList;
import java.util.List;

public class DeviceController {
    private Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    //unicast
    public void sendMessage(RTPMessage message, int offset) {
        for (DeviceInterface deviceInterface : this.device.getDeviceInterfaces()) {
            Device device = deviceInterface.getConnection().getOtherDevice(this.device);
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
            Device device = deviceInterface.getConnection().getOtherDevice(this.device);
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

        for(Device device : this.device.getAllConnectedDevices()){
            connectedDevicesAddresses.add(device.getIp_address());
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
}
