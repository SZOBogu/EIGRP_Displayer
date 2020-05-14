package eigrp_displayer;

import java.util.HashSet;
import java.util.Set;

public class PacketTargetModel {
    private IPAddress targetAddress;
    private IPAddress nextHopAddress;
    private Set<Integer> ackedMessageNumberSet;
    private int sentPacketCount;

    public PacketTargetModel(IPAddress targetAddress, IPAddress nextHopAddress) {
        this.targetAddress = targetAddress;
        this.nextHopAddress = nextHopAddress;
        this.ackedMessageNumberSet = new HashSet<>();
        this.sentPacketCount = 0;
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

    public int getSentPacketCount() {
        return sentPacketCount;
    }

    public void incrementSentPacketCount() {
        this.sentPacketCount++;
    }
}
