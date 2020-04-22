package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NeighbourTableEntryTest {
    IPAddress ip0 = new IPAddress(192,168,100,1);
    IPAddress ip1 = Mockito.mock(IPAddress.class);
    NeighbourTableEntry entry = new NeighbourTableEntry(ip0);
    @Test
    void getNeighbourAddress() {
        assertEquals(ip0, entry.getNeighbourAddress());
    }

    @Test
    void setNeighbourAddress() {
        entry.setNeighbourAddress(ip1);
        assertEquals(ip1, entry.getNeighbourAddress());
    }

    @Test
    void testToString() {
        //192.168.100.1	Interface 2\1	15	00:00:04	164	6	0	2
        String pattern = "\\d\t{2}\\d";
        String[] splittedEntry = entry.toString().split("\t");
        assertEquals("192.168.100.1", splittedEntry[0]);
        assertTrue(splittedEntry[1].matches("Interface [0-3]\\\\[0-3]"), " " + splittedEntry[1]);
        assertEquals("15", splittedEntry[2]);
        //TODO: look into, sometimes fails: 00:00 ==> expected: <true> but was: <false>
        assertTrue(splittedEntry[3].matches("^00:0[0-1]:[0-5][0-9]$"), " " + splittedEntry[3]);
        assertTrue(splittedEntry[4].matches("([0-9]|[1-9][0-9]|[1-2][0-9][0-9])|00:00"), " " + splittedEntry[4]);
        assertTrue(splittedEntry[5].matches("\\d"), " " + splittedEntry[5]);
        assertEquals("0", splittedEntry[6]);
        assertTrue(splittedEntry[7].matches("^([0-9]|[1-9][0-9])$"), " " + splittedEntry[7]);
    }

    @Test
    void getTicksSinceLastHello() {
        assertEquals(0, entry.getTicksSinceLastHello());
    }

    @Test
    void setTicksSinceLastHello() {
        entry.setTicksSinceLastHello(2);
        assertEquals(2, entry.getTicksSinceLastHello());
    }

    @Test
    void getHold() {
        assertEquals(15, entry.getHold());
    }

    @Test
    void setHold() {
        entry.setHold(100);
        assertEquals(100, entry.getHold());
    }
}