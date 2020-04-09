package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IP_CalculatorTest {
    IP_Address ip_address0 = new IP_Address(127,0,0,1);
    IP_Address ip_address1 = new IP_Address(255,255,255,255);
    IP_Address ip_address2 = new IP_Address(254,255,255,255);
    IP_Address ip_address3 = new IP_Address(100,255,254,255);
    IP_Calculator calculator = new IP_Calculator();

    @Test
    void incrementAddress() {
        calculator.incrementAddress(ip_address0);
        calculator.incrementAddress(ip_address1);
        calculator.incrementAddress(ip_address2);
        calculator.incrementAddress(ip_address3);

        IP_Address ip_address00 = new IP_Address(127,0,0,2);
        IP_Address ip_address01 = new IP_Address(0,0,0,0);
        IP_Address ip_address02 = new IP_Address(0,0,0,0);
        IP_Address ip_address03 = new IP_Address(100,255,255,0);

        assertEquals(ip_address0, ip_address00);
        assertEquals(ip_address1, ip_address01);
        assertEquals(ip_address2, ip_address02);
        assertEquals(ip_address3, ip_address03);
    }

    @Test
    void difference() {
        int difference = calculator.difference(ip_address1, ip_address2);
        assertEquals(256*256*256, difference);
    }

    @Test
    void isBigger() {
        assertTrue(calculator.isBigger(ip_address3, ip_address0));
        assertFalse(calculator.isBigger(ip_address1, ip_address2));
    }
}