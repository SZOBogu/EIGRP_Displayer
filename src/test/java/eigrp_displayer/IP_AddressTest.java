package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IP_AddressTest {
    SubnetMask mask = new SubnetMask(24);
    IP_Address address = new IP_Address("127.0.0.1", mask);

    @Test
    void getAddress() {
        assertEquals("127.0.0.1", address.getAddress());
    }

    @Test
    void setAddress() {
        address.setAddress("8.8.8.8");
        assertEquals("8.8.8.8", address.getAddress());
    }

    @Test
    void getMask() {
        assertEquals(mask, address.getMask());
    }

    @Test
    void setMask() {
        SubnetMask mask0 = new SubnetMask(8);
        address.setMask(mask0);
        assertEquals(mask0, address.getMask());
    }
}