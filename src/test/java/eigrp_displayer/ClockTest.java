package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockTest {
    ClockDependent cd0 = Mockito.mock(ClockDependent.class);
    ClockDependent cd1 = Mockito.mock(ClockDependent.class);

    @BeforeEach
    void setUp() {
        Clock.resetClock();
        Clock.getClockDependents().clear();
    }

    @Test
    void getTime(){
        assertEquals(0, Clock.getTime());
    }

    @Test
    void getClockDependents() {
        assertEquals(new ArrayList<>(), Clock.getClockDependents());
    }

    @Test
    void resetClock() {
        assertEquals(0, Clock.getTime());
        Clock.incrementClock();
        assertEquals(1, Clock.getTime());
        Clock.resetClock();
        assertEquals(0, Clock.getTime());
    }

    @Test
    void getInstance() {
        Clock clock0 = Clock.getInstance();
        Clock clock1 = Clock.getInstance();

        assertEquals(clock0, clock1);
    }

    @Test
    void incrementClock() {
        assertEquals(0, Clock.getTime());
        Clock.incrementClock();
        assertEquals(1, Clock.getTime());
    }

    @Test
    void incrementClockBy() {
        assertEquals(0, Clock.getTime());
        Clock.incrementClock(100);
        assertEquals(100, Clock.getTime());
    }

    @Test
    void addClockDependant() {
        Clock.addClockDependant(cd0);
        assertEquals(1, Clock.getClockDependents().size());
        Clock.addClockDependant(cd1);
        assertEquals(2, Clock.getClockDependents().size());
    }

    @Test
    void removeDependant() {
        Clock.addClockDependant(cd0);
        Clock.addClockDependant(cd1);
        assertEquals(2, Clock.getClockDependents().size());
        Clock.removeDependant(cd0);
        assertEquals(1, Clock.getClockDependents().size());
        Clock.removeDependant(cd1);
        assertEquals(0, Clock.getClockDependents().size());
    }
}