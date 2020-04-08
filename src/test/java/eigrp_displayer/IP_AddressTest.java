package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IP_AddressTest {
    SubnetMask mask = new SubnetMask(24);
    IP_Address address = new IP_Address(127,0,0,1, mask);


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

    @Test
    void getFirstOctet() {
        assertEquals(127, address.getFirstOctet());
    }

    @Test
    void setFirstOctet() {
        assertEquals(127, address.getFirstOctet());
    }

    @Test
    void getSecondOctet() {
        address.setFirstOctet(1);
        assertEquals(1, address.getSecondOctet());
    }

    @Test
    void setSecondOctet() {
        address.setSecondOctet(2);
        assertEquals(2, address.getSecondOctet());
    }

    @Test
    void getThirdOctet() {
        assertEquals(0, address.getThirdOctet());
    }

    @Test
    void setThirdOctet() {
        address.setThirdOctet(3);
        assertEquals(3, address.getThirdOctet());
    }

    @Test
    void getFourthOctet() {
        assertEquals(1, address.getFourthOctet());
    }

    @Test
    void setFourthOctet() {
        address.setFourthOctet(4);
        assertEquals(4, address.getFourthOctet());
    }
}