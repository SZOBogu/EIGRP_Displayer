package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CableTest {
    Cable cable = new Cable();
    Cable cable0 = new Cable("Dummy Cable", 10, 10);
    Device device = Mockito.mock(Device.class);
    Device device0 = Mockito.mock(Device.class);

    @BeforeEach
    void init(){
        cable.setDevice1(device);
        cable.setDevice2(device0);
    }

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

    @Test
    void getLoad() {
        assertEquals(10, cable.getLoad());
    }

    @Test
    void setLoad() {
        cable.setLoad(4);
        assertEquals(4, cable.getLoad());
    }

    @Test
    void getDevice1() {
        assertEquals(device ,cable.getDevice1());
    }

    @Test
    void setDevice1() {
        cable.setDevice1(device0);
        assertEquals(device0 ,cable.getDevice1());
    }

    @Test
    void getDevice2() {
        assertEquals(device0 ,cable.getDevice2());
    }

    @Test
    void setDevice2() {
        cable.setDevice2(device);
        assertEquals(device ,cable.getDevice2());
    }

    @Test
    void linkDevice(){
        cable0.linkDevice(device);
        assertEquals(device, cable0.getDevice1());
        assertNull(cable0.getDevice2());
        cable0.linkDevice(device0);
        assertEquals(device, cable0.getDevice1());
        assertEquals(device0, cable0.getDevice2());
    }
}