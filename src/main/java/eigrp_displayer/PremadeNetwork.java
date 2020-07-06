package eigrp_displayer;

public class PremadeNetwork {
    public static Network getNetwork(){
        Network network = new Network();

        ExternalNetwork externalNetwork = new ExternalNetwork();
        externalNetwork.setMask(new Mask(8));
        externalNetwork.setIp_address(new IPAddress(10,0,0,1));
        ExternalNetworkController externalNetworkController = new ExternalNetworkController(externalNetwork);
        network.getDeviceControllers().add(externalNetworkController);

        EndDevice endDevice0 = new EndDevice();
        EndDevice endDevice1 = new EndDevice();
        DeviceController endDeviceController0 = new DeviceController(endDevice0);
        DeviceController endDeviceController1 = new DeviceController(endDevice1);

        network.addDeviceController(endDeviceController0);
        network.addDeviceController(endDeviceController1);

        Router router0 = new Router("R0");
        Router router1 = new Router("R1");
        Router router2 = new Router("R2");

        RouterController routerController0 = new RouterController(router0);
        RouterController routerController1 = new RouterController(router1);
        RouterController routerController2 = new RouterController(router2);

        network.addDeviceController(routerController0);
        network.addDeviceController(routerController1);
        network.addDeviceController(routerController2);

        Connection con0 = new Cable("Cable 0", 100000, 200, 5, 70);
        Connection con1 = new Cable("Cable 1", 100000, 100, 10, 30);
        Connection con2 = new Cable("Cable 2", 200000, 200, 10, 100);
        Connection con3 = new Cable("Cable 3", 100000, 50, 20, 10);
        Connection con4 = new Cable("Cable 4", 300000, 200, 10, 50);
        Connection con5 = new Cable("Cable 5", 100000, 200, 40, 10);

        System.out.println();

        con0.linkDevices(externalNetworkController, routerController0);
        con1.linkDevices(routerController0, routerController1);
        con2.linkDevices(routerController0, routerController2);
        con3.linkDevices(routerController1, endDeviceController0);
        con4.linkDevices(routerController1, routerController2);
        con5.linkDevices(routerController2, endDeviceController1);

        network.getConnections().add(con0);
        network.getConnections().add(con1);
        network.getConnections().add(con2);
        network.getConnections().add(con3);
        network.getConnections().add(con4);
        network.getConnections().add(con5);

        PacketTargetModel extNetToDev0Model = new PacketTargetModel(endDevice0.getIp_address(), router0.getIp_address());
        PacketTargetModel extNetToDev1Model = new PacketTargetModel(endDevice1.getIp_address(), router0.getIp_address());

        externalNetworkController.addPacketTargetModel(extNetToDev0Model);
        externalNetworkController.addPacketTargetModel(extNetToDev1Model);

        RoutingTableEntry R0ExtNetEntry = new RoutingTableEntry(externalNetwork.getIp_address());
        RoutingTableEntry R1EndDev0Entry = new RoutingTableEntry(endDevice0.getIp_address());
        RoutingTableEntry R2EndDev1Entry = new RoutingTableEntry(endDevice1.getIp_address());

        routerController0.update(R0ExtNetEntry, externalNetwork.getIp_address());
        routerController1.update(R1EndDev0Entry, endDevice0.getIp_address());
        routerController2.update(R2EndDev1Entry, endDevice1.getIp_address());

        return network;
    }
}