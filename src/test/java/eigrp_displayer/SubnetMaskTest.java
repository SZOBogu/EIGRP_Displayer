package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubnetMaskTest {
    SubnetMask mask = new SubnetMask(24);

    @Test
    void getMask() {
        assertEquals(24, mask.getMask());
    }

    @Test
    void setMask() {
        mask.setMask(8);
        assertEquals(8, mask.getMask());
        mask.setMask(-1);
        assertEquals(0, mask.getMask());
        mask.setMask(100);
        assertEquals(32, mask.getMask());
    }

    @Test
    void calculateAvailableAddresses() {
        assertEquals(256, mask.calculateAvailableAddresses());
        mask.setMask(30);
        assertEquals(4, mask.calculateAvailableAddresses());
        mask.setMask(-1);
        assertEquals((long)Math.pow(2, 32), mask.calculateAvailableAddresses());
        mask.setMask(40);
        assertEquals(1, mask.calculateAvailableAddresses());
    }
}