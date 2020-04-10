package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IPAddressTest {
    IPAddress address = new IPAddress(127,0,0,1);

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
        address.setSecondOctet(1);
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