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
    }
}