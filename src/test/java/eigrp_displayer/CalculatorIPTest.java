package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorIPTest {
    IPAddress ip_address0 = new IPAddress(127,0,0,1);
    IPAddress ip_address1 = new IPAddress(255,255,255,255);
    IPAddress ip_address2 = new IPAddress(254,255,255,255);
    IPAddress ip_address3 = new IPAddress(100,255,254,255);
    CalculatorIP calculator = new CalculatorIP();

    @Test
    void incrementAddress() {
        IPAddress ip_address00 = calculator.incrementAddress(ip_address0);
        IPAddress ip_address01 = calculator.incrementAddress(ip_address1);
        IPAddress ip_address02 = calculator.incrementAddress(ip_address2);
        IPAddress ip_address03 = calculator.incrementAddress(ip_address3);

        IPAddress ip_address10 = new IPAddress(127,0,0,2);
        IPAddress ip_address11 = new IPAddress(0,0,0,0);
        IPAddress ip_address12 = new IPAddress(255,0,0,0);
        IPAddress ip_address13 = new IPAddress(100,255,255,0);

        assertEquals(ip_address10, ip_address00);
        assertEquals(ip_address11, ip_address01);
        assertEquals(ip_address12, ip_address02);
        assertEquals(ip_address13, ip_address03);
    }

    @Test
    void difference() {
        int difference = calculator.difference(ip_address1, ip_address2);
        assertEquals(256*256*256, difference);
    }

    @Test
    void isBigger() {
        assertTrue(calculator.isBigger(ip_address0, ip_address3));
        assertFalse(calculator.isBigger(ip_address2, ip_address1));
    }
}