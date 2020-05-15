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

        Connection con0 = new Cable();
        Connection con1 = new Cable();
        Connection con2 = new Cable();
        Connection con3 = new Cable();
        Connection con4 = new Cable();
        Connection con5 = new Cable();

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

//        PacketTargetModel dev0ToExtNetModel = new PacketTargetModel(externalNetwork.getIp_address(), router1.getIp_address());
//        PacketTargetModel dev1ToExtNetModel = new PacketTargetModel(externalNetwork.getIp_address(), router2.getIp_address());
//
//        endDeviceController0.setPacketTargetModel(dev0ToExtNetModel);
//        endDeviceController1.setPacketTargetModel(dev1ToExtNetModel);

        return network;
    }
}