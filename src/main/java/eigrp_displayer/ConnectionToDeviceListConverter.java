package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class ConnectionToDeviceListConverter {
    public static List<DeviceController> convert(List<Connection> connections){
        List<DeviceController> deviceControllers = new ArrayList<>();

        if(PathChecker.checkPath(connections)){
            deviceControllers.add(connections.get(0).getDevice1());
            for(Connection connection : connections){
                deviceControllers.add(connection.getOtherDevice(
                        deviceControllers.get(deviceControllers.size() - 1)));
            }
        }
        return deviceControllers;
    }
}
