package eigrp_displayer;

import java.util.Random;

public class Router extends Device{
    private RoutingTable routingTable;
    private IPAddress ip_address;
    private boolean k1;
    private boolean k2;
    private boolean k3;
    private boolean k4;
    private boolean k5;
    private final int messageSendingTimeOffset;

    public Router(String name){
        super();
        this.setName(name);
        this.ip_address = null;
        this.k1 = true;
        this.k2 = false;
        this.k3 = true;
        this.k4 = false;
        this.k5 = false;
        this.messageSendingTimeOffset = new Random().nextInt(60);
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    public boolean isK1() {
        return k1;
    }

    public void setK1(boolean k1) {
        this.k1 = k1;
    }

    public boolean isK2() {
        return k2;
    }

    public void setK2(boolean k2) {
        this.k2 = k2;
    }

    public boolean isK3() {
        return k3;
    }

    public void setK3(boolean k3) {
        this.k3 = k3;
    }

    public boolean isK4() {
        return k4;
    }

    public void setK4(boolean k4) {
        this.k4 = k4;
    }

    public boolean isK5() {
        return k5;
    }

    public void setK5(boolean k5) {
        this.k5 = k5;
    }

    public int getMessageSendingTimeOffset() {
        return messageSendingTimeOffset;
    }

}