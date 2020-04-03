package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CableTest {
    Cable cable = new Cable();
    Cable cable0 = new Cable("Dummy Cable", 10, 10);

    @Test
    void getName() {
        assertEquals("Ethernet Cable", cable.getName());
        assertEquals("Dummy Cable", cable0.getName());
    }

    @Test
    void setName() {
        cable.setName("Test");
        assertEquals("Test", cable.getName());
    }

    @Test
    void getBandwidth() {
        assertEquals(100000, cable.getBandwidth());
        assertEquals(10, cable0.getBandwidth());
    }

    @Test
    void setBandwidth() {
        cable.setBandwidth(2);
        assertEquals(2, cable.getBandwidth());
    }

    @Test
    void getDelay() {
        assertEquals(100000, cable.getBandwidth());
        assertEquals(10, cable0.getBandwidth());
    }

    @Test
    void setDelay() {
        cable.setDelay(3);
        assertEquals(3, cable.getDelay());
    }
}