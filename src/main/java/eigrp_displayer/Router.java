package eigrp_displayer;

import java.util.Objects;

public class Router extends Device{
    private RoutingTable routingTable;
    private NeighbourTable neighbourTable;
    private TopologyTable topologyTable;
    private boolean k1;
    private boolean k2;
    private boolean k3;
    private boolean k4;
    private boolean k5;

    public Router() {this("Router", 4);}

    public Router(String name){
        this(name, 4);
    }

    public Router(String name, int numberOfInterfaces){
        super(numberOfInterfaces);
        this.setName(name);
        this.setIp_address(null);
        this.k1 = true;
        this.k2 = false;
        this.k3 = true;
        this.k4 = false;
        this.k5 = false;
        this.routingTable = new RoutingTable();
        this.neighbourTable = new NeighbourTable();
        this.topologyTable = new TopologyTable();
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

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public NeighbourTable getNeighbourTable() {
        return neighbourTable;
    }

    public TopologyTable getTopologyTable() {
        return topologyTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Router router = (Router) o;
        return isK1() == router.isK1() &&
                isK2() == router.isK2() &&
                isK3() == router.isK3() &&
                isK4() == router.isK4() &&
                isK5() == router.isK5();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isK1(), isK2(), isK3(), isK4(), isK5());
    }
}