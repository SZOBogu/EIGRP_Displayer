package eigrp_displayer;

public class Network implements Addable {
    private IPAddress networkAddress;
    private SubnetMask mask;

    public IPAddress getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(IPAddress networkAddress) {
        this.networkAddress = networkAddress;
    }

    public SubnetMask getMask() {
        return mask;
    }

    public void setMask(SubnetMask mask) {
        this.mask = mask;
    }
}
