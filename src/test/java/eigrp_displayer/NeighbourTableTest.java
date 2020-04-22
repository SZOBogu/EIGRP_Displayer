package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NeighbourTableTest {
    NeighbourTable neighbourTable = new NeighbourTable();
    IPAddress ip0 = Mockito.mock(IPAddress.class);
    IPAddress ip1 = Mockito.mock(IPAddress.class);
    IPAddress ip2 = Mockito.mock(IPAddress.class);

    @Test
    void getDescription() {
        assertEquals("IP-EIGRP neighbours table for process 1", neighbourTable.getDescription());
    }

    @Test
    void getEntries() {
        assertEquals(new ArrayList<>(), neighbourTable.getEntries());
    }

    @Test
    void formNeighbourship() {
        assertEquals(0 , neighbourTable.getEntries().size());
        neighbourTable.formNeighbourship(ip0);
        assertEquals(1 , neighbourTable.getEntries().size());
        assertEquals(ip0, neighbourTable.getEntries().get(0).getNeighbourAddress());
        neighbourTable.formNeighbourship(ip1);
        neighbourTable.formNeighbourship(ip2);
        assertEquals(3 , neighbourTable.getEntries().size());
    }

    @Test
    void testToString() {
        StringBuilder string = new StringBuilder(neighbourTable.getDescription() + '\n' +
                "H\tAddress\tInterface\tHold\tUptime\tSRTT\tRTO\tQ Cnt\tSeq Num\n");
        neighbourTable.formNeighbourship(ip0);
        neighbourTable.formNeighbourship(ip1);
        neighbourTable.formNeighbourship(ip2);

        for(int i = 0 ; i < neighbourTable.getEntries().size(); i++){
            string.append(i).append("\t").append(neighbourTable.getEntries().get(i).toString()).append("\n");
        }
        assertEquals(string.toString(), neighbourTable.toString());
    }

    @Test
    void updateTime() {
        Clock.getClockDependents().clear();
        neighbourTable = new NeighbourTable();
        neighbourTable.formNeighbourship(ip0);
        neighbourTable.formNeighbourship(ip1);
        neighbourTable.getEntries().get(1).setHold(20);
        assertEquals(1, Clock.getClockDependents().size());
        assertEquals(neighbourTable, Clock.getClockDependents().get(0));
        assertEquals(0, neighbourTable.getEntries().get(0).getTicksSinceLastHello());
        assertEquals(0, neighbourTable.getEntries().get(1).getTicksSinceLastHello());
        Clock.incrementClock();
        assertEquals(1, neighbourTable.getEntries().get(0).getTicksSinceLastHello());
        assertEquals(1, neighbourTable.getEntries().get(1).getTicksSinceLastHello());
        Clock.incrementClock(14);
        assertEquals(15, neighbourTable.getEntries().get(0).getTicksSinceLastHello());
        assertEquals(15, neighbourTable.getEntries().get(1).getTicksSinceLastHello());
        Clock.incrementClock();
        assertEquals(1, neighbourTable.getEntries().size());
        Clock.getClockDependents().clear();
    }

    @Test
    void checkIfPresent() {
        neighbourTable.formNeighbourship(ip0);
        neighbourTable.formNeighbourship(ip1);

        assertTrue(neighbourTable.checkIfPresent(ip0));
        assertTrue(neighbourTable.checkIfPresent(ip1));
        assertFalse(neighbourTable.checkIfPresent(ip2));
    }
}