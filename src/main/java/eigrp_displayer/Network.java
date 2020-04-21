package eigrp_displayer;

public class Network implements Addable {
    private IPAddress networkAddress;
    private Mask mask;

    public IPAddress getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(IPAddress networkAddress) {
        this.networkAddress = networkAddress;
    }

    public Mask getMask() {
        return mask;
    }

    public void setMask(Mask mask) {
        this.mask = mask;
    }
}
