package eigrp_displayer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PoolOfIPAddressesTest {
    PoolOfIPAddresses pool = new PoolOfIPAddresses();
    IPAddress ipAddressNetwork = new IPAddress(192,168,100, 0);
    IPAddress ipAddressBroadcast = new IPAddress(192,168,100, 16);
    SubnetMask mask = new SubnetMask(28);

    IPAddress address1 = new IPAddress(192,168,100, 1);
    IPAddress address2 = new IPAddress(192,168,100, 2);
    IPAddress address3 = new IPAddress(192,168,100, 3);
    IPAddress address4 = new IPAddress(192,168,100, 4);
    IPAddress address5 = new IPAddress(192,168,100, 5);
    IPAddress address6 = new IPAddress(192,168,100, 6);
    IPAddress address7 = new IPAddress(192,168,100, 7);
    IPAddress address8 = new IPAddress(192,168,100, 8);
    IPAddress address9 = new IPAddress(192,168,100, 9);
    IPAddress address10 = new IPAddress(192,168,100, 10);
    IPAddress address11 = new IPAddress(192,168,100, 11);
    IPAddress address12 = new IPAddress(192,168,100, 12);
    IPAddress address13 = new IPAddress(192,168,100, 13);
    IPAddress address14 = new IPAddress(192,168,100, 14);
    ArrayList<IPAddress> controlGroup = new ArrayList<>(Arrays.asList(
            address1, address2, address3, address4, address5, address6, address7,
            address8, address9, address10, address11, address12, address13, address14
    ));

    @Test
    void initAddresses() {
        pool.init(ipAddressNetwork, ipAddressBroadcast);
        assertEquals(14, pool.getFreeAddresses().size());

        for (int i = 0; i < pool.getFreeAddresses().size(); i++) {
            assertEquals(controlGroup.get(i), pool.getFreeAddresses().get(i));
        }
    }

    @Test
    void initMask() {
        pool.init(ipAddressNetwork, mask);
        assertEquals(14, pool.getFreeAddresses().size());

        for (int i = 0; i < pool.getFreeAddresses().size(); i++) {
            assertEquals(controlGroup.get(i), pool.getFreeAddresses().get(i));
        }
    }

    @Test
    void getFreeAddresses() {
        assertEquals(0, pool.getFreeAddresses().size());
        pool.init(ipAddressNetwork, mask);
        assertEquals(14, pool.getFreeAddresses().size());
    }

    @Test
    void getTakenAddresses() {
        assertEquals(0, pool.getTakenAddresses().size());
        pool.init(ipAddressNetwork, mask);
        assertEquals(0, pool.getTakenAddresses().size());
        IPAddress ipa = pool.getIPAddress();
        assertEquals(1, pool.getTakenAddresses().size());
    }

    @Test
    void getIPAddress() {
        assertEquals(0, pool.getTakenAddresses().size());
        pool.init(ipAddressNetwork, mask);

        IPAddress ipa = pool.getIPAddress();
        IPAddress ipa2 = pool.getIPAddress();
        IPAddress ipa3 = pool.getIPAddress();

        assertEquals(3, pool.getTakenAddresses().size());
        pool.releaseIPAddress(ipa2);
        assertEquals(2, pool.getTakenAddresses().size());
        IPAddress ipa4 = pool.getIPAddress();
        assertEquals(3, pool.getTakenAddresses().size());
        assertEquals(ipa2, ipa4);
    }

    @Test
    void releaseIPAddress() {
        pool.init(ipAddressNetwork, mask);
        assertEquals(14, pool.getFreeAddresses().size());
        assertEquals(0, pool.getTakenAddresses().size());
        IPAddress ipa = pool.getIPAddress();
        assertEquals(13, pool.getFreeAddresses().size());
        assertEquals(1, pool.getTakenAddresses().size());
        pool.releaseIPAddress(ipa);
        assertEquals(14, pool.getFreeAddresses().size());
        assertEquals(0, pool.getTakenAddresses().size());
    }
}