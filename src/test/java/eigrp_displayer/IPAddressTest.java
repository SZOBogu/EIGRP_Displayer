package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IPAddressTest {
    IPAddress address = new IPAddress(127,0,0,1);

    @Test
    void getFirstOctet() {
        assertEquals(127, address.getFirstOctet());
    }

    @Test
    void setFirstOctet() {
        address.setFirstOctet(0);
        assertEquals(0, address.getFirstOctet());
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

    @Test
    void setAddress(){
        address.setAddress(4,20,6,9);
        assertEquals(new IPAddress(4,20,6,9), address);
    }

    @Test
    void equals(){
        IPAddress ip0 = new IPAddress(23,234,5,57);
        IPAddress ip1 = new IPAddress(5,45,223,4);
        IPAddress ip2 = new IPAddress(5,12,5,56);
        IPAddress ip3 = new IPAddress(23,234,5,57);

        assertEquals(ip0, ip3);
        assertNotEquals(ip0, ip1);
        assertNotEquals(ip0, ip2);
        assertNotEquals(ip2, ip1);
    }
}