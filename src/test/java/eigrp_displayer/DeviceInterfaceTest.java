package eigrp_displayer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeviceInterfaceTest {
    DeviceInterface deviceInterface0 = new DeviceInterface();
    DeviceInterface deviceInterface1 = new DeviceInterface("Test");
    Connection connection = Mockito.mock(Connection.class);

    @Test
    void getName() {
        assertEquals("Any", deviceInterface0.getName());
        assertEquals("Test", deviceInterface1.getName());
    }

    @Test
    void getConnection() {
        assertNull(deviceInterface0.getConnection());
        assertNull(deviceInterface1.getConnection());
    }

    @Test
    void setConnection() {
        deviceInterface0.setConnection(connection);
        assertEquals(connection, deviceInterface0.getConnection());
    }
}