package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class ShowcaseNetwork {
    private IP_Address networkAddress;
    private IP_Address broadcastAddress;
    private SubnetMask mask;

    private List<IP_Address> takenIPAddresses;
    private List<Link> connections;     //separate class?
    private List<Device> devices;

    //TODO: constructor calculating broadcast using mask and networkAddress

    public ShowcaseNetwork(IP_Address networkAddress, IP_Address broadcastAddress, SubnetMask mask){
        this.networkAddress = networkAddress;
        this.broadcastAddress = broadcastAddress;
        this.mask = mask;
        this.takenIPAddresses = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.devices = new ArrayList<>();
    }

//    public void addDevice(Device device){
//
//    }
//
//    public void linkDevices(Device device1, Device device2){
//
//    }
//
//    public boolean checkIfIPAddressIsFree(IP_Address ip_address){
//        if(ip_address.equals(networkAddress) || ip_address.equals(broadcastAddress)){
//            return false;
//        }
//    }
}
