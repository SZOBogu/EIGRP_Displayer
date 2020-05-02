package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NeighbourTableEntryTest {
    IPAddress ip0 = new IPAddress(192,168,100,1);
    IPAddress ip1 = Mockito.mock(IPAddress.class);
    DeviceInterface deviceInterface = new DeviceInterface("Interface");
    NeighbourTableEntry entry = new NeighbourTableEntry(deviceInterface, ip0);
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
        Random rand = new Random();

        for(int i = 0; i < 1000; i++) {
            int firstOctet = rand.nextInt(256);
            int secondOctet = rand.nextInt(256);
            int thirdOctet = rand.nextInt(256);
            int fourthOctet = rand.nextInt(256);

            IPAddress ip = new IPAddress(firstOctet, secondOctet,
                    thirdOctet, fourthOctet);
            entry.setNeighbourAddress(ip);
            String[] splittedEntry = entry.toString().split("\t");
            assertEquals(firstOctet + "." +
                            secondOctet + "." +
                            thirdOctet + "." +
                            fourthOctet,
                            splittedEntry[0]);

            assertEquals(deviceInterface.getName(), splittedEntry[1]);
            assertEquals("15", splittedEntry[2]);

            assertTrue(splittedEntry[3].matches("^00:0[0-1]:[0-5][0-9]$|00:00"), " " + splittedEntry[3]);
            assertTrue(splittedEntry[4].matches("([0-9]|[1-9][0-9]|[1-2][0-9][0-9])|00:00"), " " + splittedEntry[4]);
            assertTrue(splittedEntry[5].matches("\\d"), " " + splittedEntry[5]);
            assertEquals("0", splittedEntry[6]);
            assertTrue(splittedEntry[7].matches("^([0-9]|[1-9][0-9])$"), " " + splittedEntry[7]);
        }
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