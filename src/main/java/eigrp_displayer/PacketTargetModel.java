package eigrp_displayer;

import java.util.HashSet;
import java.util.Set;

public class PacketTargetModel {
    private IPAddress targetAddress;
    private IPAddress nextHopAddress;
    private Set<Integer> ackedMessageNumberSet;

    public PacketTargetModel(IPAddress targetAddress, IPAddress nextHopAddress) {
        this.targetAddress = targetAddress;
        this.nextHopAddress = nextHopAddress;
        this.ackedMessageNumberSet = new HashSet<>();
    }

    public IPAddress getTargetAddress() {
        return targetAddress;
    }

    public IPAddress getNextHopAddress() {
        return nextHopAddress;
    }

    public Set<Integer> getAckedMessageNumberSet() {
        return ackedMessageNumberSet;
    }
}
