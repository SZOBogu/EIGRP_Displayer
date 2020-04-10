package eigrp_displayer;

public abstract class Device implements Addable {
    private String name;
    private IPAddress ip_address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IPAddress getIp_address() {
        return ip_address;
    }

    public void setIp_address(IPAddress ip_address) {
        this.ip_address = ip_address;
    }
}
