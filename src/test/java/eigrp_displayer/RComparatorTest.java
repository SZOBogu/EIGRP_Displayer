package eigrp_displayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RComparatorTest {
    Router router1 = new Router("R1");
    Router router2 = new Router("R2");
    RComparator comparator = new RComparator();

    @Test
    void compare(){
        router1.setK1(true);
        router1.setK2(false);
        router1.setK3(true);
        router1.setK4(false);
        router1.setK5(true);

        router2.setK1(false);
        router2.setK2(true);
        router2.setK3(false);
        router2.setK4(true);
        router2.setK5(false);

        assertFalse(comparator.compare(router1, router2));

        router2.setK1(true);
        router2.setK2(false);
        router2.setK3(true);
        router2.setK4(false);
        router2.setK5(true);

        assertTrue(comparator.compare(router1, router2));
    }
}