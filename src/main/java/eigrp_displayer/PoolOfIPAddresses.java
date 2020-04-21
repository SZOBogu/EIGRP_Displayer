package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class PoolOfIPAddresses {
    private List<IPAddress> freeAddresses;
    private List<IPAddress> takenAddresses;

    public PoolOfIPAddresses(){
        this.freeAddresses = new ArrayList<>();
        this.takenAddresses = new ArrayList<>();
    }

    public void init(IPAddress networkAddress, IPAddress broadcastAddress){
        CalculatorIP calc = new CalculatorIP();

        for(int i = 1; i < calc.difference(broadcastAddress, networkAddress) - 1; i++){
            IPAddress ipAddress = calc.incrementAddress(networkAddress, i);
            this.freeAddresses.add(ipAddress);
        }
    }

    public void init(IPAddress networkAddress, Mask mask){
        CalculatorIP calc = new CalculatorIP();

        for(int i = 1; i < mask.calculateAvailableAddresses() - 1; i++){
            IPAddress ipAddress = calc.incrementAddress(networkAddress, i);
            this.freeAddresses.add(ipAddress);
        }
    }

    public List<IPAddress> getFreeAddresses() {
        return freeAddresses;
    }

    public List<IPAddress> getTakenAddresses() {
        return takenAddresses;
    }

    public IPAddress getIPAddress(){
        IPAddress ipAddress = this.freeAddresses.get(0);
        this.freeAddresses.remove(ipAddress);
        this.takenAddresses.add(ipAddress);

        return ipAddress;
    }

    public void releaseIPAddress(IPAddress ipAddress){
        try {
            for (IPAddress ipa : this.takenAddresses) {
                if (ipa.equals(ipAddress)) {
                    this.freeAddresses.add(ipAddress);
                    this.takenAddresses.remove(ipAddress);
                    sort(this.freeAddresses);
                }
            }
        }
        catch (Exception e){
            System.out.println("Exception " + e);
        }
    }

    public boolean contains(IPAddress ipAddress){
        return this.takenAddresses.contains(ipAddress) || this.freeAddresses.contains(ipAddress);
    }
}
