package eigrp_displayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventLogTest {
    @AfterEach
    void tearDown() {
        EventLog.clear();;
    }

    @Test
    void getInstance() {
        EventLog eventLog1 = EventLog.getInstance();
        EventLog eventLog2 = EventLog.getInstance();

        assertEquals(eventLog1, eventLog2);
    }

    @Test
    void appendLog() {
        EventLog.appendLog("test");
        assertEquals("test\n", EventLog.getEventLog());
    }

    @Test
    void getEventLog() {
        assertEquals("", EventLog.getEventLog());
    }

    @Test
    void clear() {
        EventLog.appendLog("na");
        EventLog.appendLog("jeziorze");
        EventLog.appendLog("wielka");
        EventLog.appendLog("burza");
        EventLog.appendLog("jezus");
        EventLog.appendLog("ze");
        EventLog.appendLog("mna");
        EventLog.appendLog("w lodzi");
        EventLog.appendLog("jest");

        assertNotEquals("", EventLog.getEventLog());
        EventLog.clear();
        assertEquals("", EventLog.getEventLog());
    }
}